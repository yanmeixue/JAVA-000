package pers.ryan.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class JdbcStatement {
    // DROP TABLE IF EXISTS Student;
    // CREATE TABLE Student (id INT NOT NULL, age INT NOT NULL, name VARCHAR(255));
    // INSERT INTO Student VALUES (1, 15, 'ZhangSan');
    // INSERT INTO Student VALUES (2, 16, 'LiSi');
    // INSERT INTO Student VALUES (3, 16, 'WangWu');
    // INSERT INTO Student VALUES (4, 17, 'ZhaoLiu');

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(Config.JDBC_DRIVER);
            conn = DriverManager.getConnection(Config.DB_URL, Config.USER, Config.PASS);
            stmt = conn.createStatement();
            String sql = "SELECT id, age, name FROM Student";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String name = rs.getString("name");
                log.info("id: {}, age: {}, name: {}", id, age, name);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
