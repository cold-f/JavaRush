package com.javarush.test.level33.lesson15.big01.strategies;

import com.javarush.test.level33.lesson15.big01.ExceptionHandler;

import java.sql.*;

public class MySQLStorageStrategy implements StorageStrategy {

    static Connection connection = getSQLDBConnection("jdbc:mysql://localhost/mydbtest?useSSL=false", "root", "root");

    @Override
    public boolean containsKey(Long key) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mytable where id = ?");
            preparedStatement.setLong(1, key);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            new ExceptionHandler().log(e);
            return false;
        }
    }

    @Override
    public boolean containsValue(String value)  {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mytable where string = ?");
            preparedStatement.setString(1, value);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next())
                return true;
            else
                return false;
        } catch (SQLException e) { new ExceptionHandler().log(e);
            return false;
        }
    }

    @Override
    public void put(Long key, String value)  {
        try {
            if (!containsKey(key)) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mytable(id, string) values(?, ?)");
                preparedStatement.setLong(1, key);
                preparedStatement.setString(2, value);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) { new ExceptionHandler().log(e);}
        //добавь логику, есди содержит ключ!например, обновить
    }

    @Override
    public Long getKey(String value)  {
        try {
            if (containsValue(value)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mytable where string = ?");
                preparedStatement.setString(1, value);
                ResultSet result = preparedStatement.executeQuery();
                result.next();
                return result.getLong("id");
            }
            else
                return null;
        } catch (SQLException e) { new ExceptionHandler().log(e);;
            return null;
        }

    }

    @Override
    public String getValue(Long key)  {
        try {
            if (containsKey(key)) {
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM mytable where id = ?");
                preparedStatement.setLong(1, key);
                ResultSet result = preparedStatement.executeQuery();
                result.next();
                return result.getString("string");
            } else
                return null;
        } catch (SQLException e) { new ExceptionHandler().log(e);
            return null;
        }
    }


    private static Connection getSQLDBConnection(String url, String name, String password ) {
        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            new ExceptionHandler().log(e);
        }
        try {
            dbConnection = DriverManager.getConnection(url, name, password);
            return dbConnection;
        } catch (SQLException e) {
            new ExceptionHandler().log(e);
        }
        return dbConnection;
    }

    //база данных mydbtest, таблица mytable, две колонки:  id (BIGINT) и string (VARCHAR)
    //после каждого заполнения(теста) вручную удалял все данные из таблицы
    //драйверы на БД ставил через IDE, как и другие библиотеки
}
