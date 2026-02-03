package com.ess.services;

import com.ess.config.DatabaseConfig;
import com.ess.models.AirQualityReport;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    /**
     * 传入报告的信息,在数据库中添加对应的表添加一个报告的数据
     * @param userId
     * @param location
     * @param latitude
     * @param longitude
     * @param description
     * @return
     */

    public boolean createReport(int userId, String location, double latitude, double longitude, String description) {
        String sql = "INSERT INTO air_quality_reports (user_id, location, latitude, longitude, description) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, location);
            stmt.setDouble(3, latitude);
            stmt.setDouble(4, longitude);
            stmt.setString(5, description);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 传入用户ID,拿到数据库中该用户提交的所有报告
     * 并返回一个集合
     * @param userId
     * @return
     */

    public List<AirQualityReport> getReportsByUser(int userId) {
        System.out.println("进入了getReportsByUser");
        List<AirQualityReport> reports = new ArrayList<>();
        String sql = "SELECT * FROM air_quality_reports WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AirQualityReport airQualityReport = new AirQualityReport(
                        rs.getInt("report_id"),
                        rs.getInt("user_id"),
                        rs.getString("location"),
                        rs.getString("description"),
                        "待更改",
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                if(rs.getString("status").equals("PENDING"))airQualityReport.setStatus("待处理");
                else if (rs.getString("status").equals("ASSIGNED"))airQualityReport.setStatus("已处理");
                reports.add(airQualityReport);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(reports.size()==0){
            System.out.println("该用户没有提交过报告");
        }
        return reports;
    }

    public List<Integer> getPendingReportIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT report_id FROM air_quality_reports WHERE status = 'PENDING'";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ids.add(rs.getInt("report_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * 拿到数据库中的所有报告
     * @return
     */

    public List<AirQualityReport> getAllReports() {
        List<AirQualityReport> reports = new ArrayList<>();
        String sql = "SELECT r.*, u.username FROM air_quality_reports r " +
                "JOIN users u ON r.user_id = u.user_id " +
                "ORDER BY r.created_at DESC";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AirQualityReport report = new AirQualityReport(
                        rs.getInt("report_id"),
                        rs.getInt("user_id"),
                        rs.getString("location"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("description"),
                       "待更改",
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                if(rs.getString("status").equals("PENDING"))report.setStatus("待处理");
                else if(rs.getString("status").equals("ASSIGNED"))report.setStatus("已处理");

                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
    public void updateReportStatus(int reportId, String status) throws SQLException {
        String sql = "UPDATE air_quality_reports SET status = ? WHERE report_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);



            stmt.setInt(2, reportId);
            stmt.executeUpdate();
        }
    }

    public  Map<String, Object> getReportByReportId(Integer report_id)  {
        Map<String, Object> rowDataList= new HashMap<>();
        String sql = "SELECT * FROM `" + "air_quality_reports" + "` WHERE `" + "report_id" + "` = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, report_id);
            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String colName = metaData.getColumnName(i);
                    Object colValue = rs.getObject(i);
                    row.put(colName, colValue);
                }
               rowDataList=row;
            }
            return rowDataList;
        }catch (Exception e){
            e.printStackTrace();
        }
         return rowDataList;
    }





}