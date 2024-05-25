package org.example.models;

import org.example.models.basics.Coordinates;
import org.example.models.basics.Dragon;
import org.example.models.basics.DragonHead;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Random;

public class DataBaseHandler {
    private final String url = "jdbc:postgresql://localhost:5433/studs";
    private final String name = "s413030";
    private final String password = System.getenv("helios_db_pass");
    private Connection connection;

    public void connectToDataBase(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,name,password);
            System.out.println("Подключение к базе данных установлено!");
        }catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных. Завершение приложения.");
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int getUserIdByName(String userName){
        int userID = -1;
        try{
            String query = "SELECT id FROM users WHERE name=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,userName);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            userID = resultSet.getInt("id");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userID;
    }

    public ResultSet getDragons(){
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();

            resultSet =  statement.executeQuery("SELECT * FROM dragons " +
                    "JOIN coordinates ON coordinates.id=dragons.coordinates_id " +
                    "JOIN dragon_head ON dragon_head.id=dragons.head_id");


        }catch(SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getDragonHeadById(int id){
        try{
            String query = "SELECT * FROM dragon_head WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);

            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getCoordinatesRowById(int id){
        try{
            String query = "SELECT * FROM coordinates WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);

            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDragonById(int dragonId){
        try{
            String query = "DELETE FROM dragons WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,dragonId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean userOwnerOfDragon(int dragonID, int userID){
        try{
            String query = "SELECT * FROM dragons WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,dragonID);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return resultSet.getInt("user_id") == userID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearDB(){
        try{
            connection.createStatement().execute("TRUNCATE dragons");
            connection.createStatement().execute("TRUNCATE dragon_head CASCADE");
            connection.createStatement().execute("TRUNCATE coordinates CASCADE");

            connection.createStatement().execute("ALTER SEQUENCE dragons_id_seq RESTART");
            connection.createStatement().execute("ALTER SEQUENCE dragon_head_id_seq RESTART");
            connection.createStatement().execute("ALTER SEQUENCE coordinates_id_seq RESTART");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean insertDragon(Dragon dragon,int userID){
        try{
            int id = getLastIdOfDragon();
            dragon.setId(id+1);

            DragonHead head = dragon.getHead();
            Coordinates coordinates = dragon.getCoordinates();

            String insertCoordinates = "INSERT INTO coordinates(x,y) VALUES(?,?)";
            PreparedStatement coordinatesStatement = connection.prepareStatement(insertCoordinates);
            coordinatesStatement.setInt(1,coordinates.getX());
            coordinatesStatement.setFloat(2,coordinates.getY());
            coordinatesStatement.executeUpdate();

            String insertDragonHead = "INSERT INTO dragon_head(size, eyes_count, tooth_count) VALUES(?,?,?)";
            PreparedStatement dragonHeadStatement = connection.prepareStatement(insertDragonHead);
            dragonHeadStatement.setLong(1,head.getSize());
            dragonHeadStatement.setFloat(2,head.getEyesCount());
            dragonHeadStatement.setFloat(3,head.getToothCount());
            dragonHeadStatement.executeUpdate();

            ResultSet coordinatesIDResult = connection.createStatement().executeQuery("SELECT last_value FROM coordinates_id_seq");
            coordinatesIDResult.next();
            int coordinatesID = coordinatesIDResult.getInt("last_value");

            ResultSet dragonHeadIDResult = connection.createStatement().executeQuery("SELECT last_value FROM dragon_head_id_seq");
            dragonHeadIDResult.next();
            int dragonHeadID = dragonHeadIDResult.getInt("last_value");

            String insertDragon = "INSERT INTO dragons(name,creation_date,age,wingspan,speaking,type,head_id,coordinates_id,user_id)" +
                    "VALUES(?,?,?,?,?,CAST(? AS dragon_type),?,?,?)";
            PreparedStatement dragonStatement = connection.prepareStatement(insertDragon);
            dragonStatement.setString(1,dragon.getName());
            dragonStatement.setDate(2, (Date) dragon.getCreationDate());
            dragonStatement.setLong(3,dragon.getAge());
            dragonStatement.setFloat(4,dragon.getWingspan());
            dragonStatement.setBoolean(5,dragon.getSpeaking());
            dragonStatement.setString(6,dragon.getType().name());
            dragonStatement.setInt(7,dragonHeadID);
            dragonStatement.setInt(8,coordinatesID);
            dragonStatement.setInt(9,userID);
            dragonStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authorization(String userName, String password) {
        boolean result = false;
        try {
            if(!availableUsername(userName)){
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

                String saltQuery = "SELECT salt FROM users WHERE name=?";
                PreparedStatement saltStatement = connection.prepareStatement(saltQuery);
                saltStatement.setString(1,userName);

                String passQuery = "SELECT password FROM users WHERE name=?";
                PreparedStatement passwordStatement = connection.prepareStatement(passQuery);
                passwordStatement.setString(1,userName);

                ResultSet resultSaltSet = saltStatement.executeQuery();
                resultSaltSet.next();
                String salt = resultSaltSet.getString("salt");

                ResultSet resultPassSet = passwordStatement.executeQuery();
                resultPassSet.next();
                String dbPassword = resultPassSet.getString("password");

                byte[] bytesOfHashedPassword = messageDigest.digest((password+salt).getBytes());
                BigInteger numRepresentation = new BigInteger(1,bytesOfHashedPassword);

                result = numRepresentation.toString(16).equals(dbPassword);
            }
        }catch (SQLException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean registration(String userName, String password) {
        boolean result = false;

        try {
            if (availableUsername(userName)) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                String salt = generateSalt();

                byte[] hashedPassword = messageDigest.digest((password + salt).getBytes());
                BigInteger numRepresentation = new BigInteger(1, hashedPassword);


                String query = "INSERT INTO users(name,password,salt) VALUES(?,?,?)";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setString(2, numRepresentation.toString(16));
                statement.setString(3, salt);

                statement.executeUpdate();
                result = true;
            }
            System.out.println("Такой пользователь уже есть");

        }catch (SQLException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return result;
    }
    public boolean availableUsername(String userName) throws SQLException{
        String query = "SELECT * FROM users WHERE name=?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,userName);

        ResultSet user = statement.executeQuery();

        return !user.next();
    }

    private int getLastIdOfDragon(){
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT last_value FROM dragons_id_seq");
            resultSet.next();

            return resultSet.getInt("last_value");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateSalt() {
        String specialSymb = "}$%\\@[!#^>?^-$]{:%+_;$^</#£`~*()";
        String basicAlph = "QWRETYUIOPASDFGHJLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";

        Random randomIndex = new Random();

        StringBuilder salt = new StringBuilder();

        for (int i = 0; i<20; i++){
            if(i%3==0)
                salt.append(specialSymb.charAt(randomIndex.nextInt(29)));
            else
                salt.append(basicAlph.charAt(randomIndex.nextInt(61)));
        }

        return salt.toString();
    }
}
