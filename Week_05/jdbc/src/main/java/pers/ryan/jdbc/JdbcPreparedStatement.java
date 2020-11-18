package pers.ryan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcPreparedStatement {

    public static void main(String[] args) {
        try {
            Class.forName(Config.JDBC_DRIVER);
            Connection con = DriverManager.getConnection(Config.DB_URL, Config.USER, Config.PASS);
            String sql = "UPDATE Student SET age = age + 1 WHERE id = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                preparedStatement.setInt(1, 1);
                preparedStatement.executeUpdate();
                preparedStatement.setInt(1, 2);
                preparedStatement.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
