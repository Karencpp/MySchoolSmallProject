package com.ess.services;

import com.ess.config.DatabaseConfig;
import com.ess.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    /**
     * 拿到所有身份为网格员的用户
     * @return
     */

    public List<String> getInspectorUsernames() {
        List<String> usernames = new ArrayList<>();
        String sql = "SELECT username FROM users WHERE role = 'INSPECTOR'";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    /**
     * 拿到所有用户
     * @return
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY role, username";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        "待定",
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("location")
                );

                if(rs.getString("role").equals("INSPECTOR"))user.setRole("网格员");
                if(rs.getString("role").equals("ADMIN"))user.setRole("系统管理员");
                if(rs.getString("role").equals("PUBLIC"))user.setRole("公众监督员");
                if(rs.getString("role").equals("DECISION_MAKER"))user.setRole("决策者");

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
/**  传入一个用户对象
 * 并把这个用户的信息存入数据库中
 */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, role = ?, full_name = ?, email = ?, phone = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, user.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除这个用户在数据库中的数据
     * @param userId 传入一个用户的ID
     * @return
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
           return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}