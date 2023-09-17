package repository;

import entities.User2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {

    private static final String url = "jdbc:sqlserver://localhost:1433;database=shopDB;";
    private static final String user = "newlogin2";
    private static final String password = "1234";

    public UserRepository() throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }

    public void saveMessage(User2 user2) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (Id,chatId,userName,email) VALUES (?,?,?,?)")) {
            statement.setInt(1, user2.getId());
            statement.setString(2, user2.getChatId());
            statement.setString(3, user2.getUserName());
            statement.setString(4, user2.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


