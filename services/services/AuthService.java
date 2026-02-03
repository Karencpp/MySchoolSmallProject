package com.ess.services;

import com.ess.config.DatabaseConfig;
import com.ess.models.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    /**
     * 传入用户名和密码,并判断数据库中有没有这个账户,并且密码是否正确
     * 如果正确,则返回一个用户对象,供全局使用,否则返回null
     * @param username
     * @param password
     * @return
     */


    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storePassword = rs.getString("password");
                if (password.equals(storePassword)) {
                    User user = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            password,
                            rs.getString("role"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("location")
                    );
                         return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查指定的用户名是否已经存在于数据库中。
     * 
     * @param username 要检查的用户名
     * @return 如果用户名存在返回true，否则返回false
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *   传人一个用户对象,根据这个对象的属性在数据库中添加一个用户数据
     * @param user
     * @return
     */

    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, role, full_name, email, phone,location) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());

            stmt.setString(2, user.getPassword());

            if(user.getRole().equals("网格员"))stmt.setString(3, "INSPECTOR");
            if(user.getRole().equals("管理员"))stmt.setString(3, "ADMIN");
            if(user.getRole().equals("公众监督员"))stmt.setString(3, "PUBLIC");
            if(user.getRole().equals("决策者"))stmt.setString(3, "DECISION_MAKER");

            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getPlaceOfinspector());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
