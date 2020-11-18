package pers.ryan.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class JdbcHikariCp {

    public static void main(String[] args) {
        String configFile = JdbcHikariCp.class.getClassLoader().getResource("db.properties").getPath();
        HikariDataSource ds = new HikariDataSource(new HikariConfig(configFile));

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = ds.getConnection();
            pst = con.prepareStatement("SELECT * FROM Student");
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int age = rs.getInt("age");
                String name = rs.getString("name");
                log.info("id: {}, age: {}, name: {}", id, age, name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
                ds.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
