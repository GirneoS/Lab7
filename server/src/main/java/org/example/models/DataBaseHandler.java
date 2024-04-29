package org.example.models;

import java.sql.*;

public class DataBaseHandler {
    private final String url;
    private final String name;
    private final String password;
    private Connection connection;

    public DataBaseHandler(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public void connectToDataBase(){
        try{
            connection = DriverManager.getConnection(url,name,password);
            System.out.println("Подключение к базе данных установлено!");
        }catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных. Завершение приложения.");
            System.exit(-1);
        }
    }

    public ResultSet getDragons(){
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();

            resultSet =  statement.executeQuery("SELECT * FROM dragons");

        }catch(SQLException e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public void authorization(String userName, String password){

    }

    public void registration(String userName, String password){

    }
}
