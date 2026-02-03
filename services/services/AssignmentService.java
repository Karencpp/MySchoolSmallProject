package com.ess.services;

import com.ess.config.DatabaseConfig;
import com.ess.models.Assignment;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentService {
    /**
     * 传入一个任务的信息,根据这个信息在数据库中添加一个任务的数据
     * @param report_id
     * @param inspector_id
     * @param assigned_by
     * @param deadline
     * @return
     */
    public  boolean createAssignment(int report_id, int inspector_id, int assigned_by, LocalDate deadline) {
        String sql = "INSERT INTO assignments (report_id, inspector_id, assigned_by, deadline) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, report_id);
            pstmt.setInt(2, inspector_id);
            pstmt.setInt(3, assigned_by);
            pstmt.setObject(4, deadline); // 支持 LocalDate 类型插入

            new ReportService().updateReportStatus(report_id,"ASSIGNED");


            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 传入一个角色为网格员的用户ID
     * 拿到这个网格员所被指派的所有任务
     * @param inspectorId
     * @return
     */

    public List<Assignment> getAssignmentsByInspector(int inspectorId) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT a.*, r.location FROM assignments a " +
                "JOIN air_quality_reports r ON a.report_id = r.report_id " +
                "WHERE a.inspector_id = ? " +
                "ORDER BY a.deadline";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inspectorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
             Assignment assignment =  new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("report_id"),

                        rs.getInt("inspector_id"),
                        rs.getInt("assigned_by"),
                        rs.getDate("assigned_at").toLocalDate(),
                        rs.getDate("deadline").toLocalDate(),
                        ""
                );
             if(rs.getString("status").equals("PENDING"))assignment.setStatus("待处理");
             if (rs.getString("status").equals("COMPLETED"))assignment.setStatus("已完成");


                assignments.add(assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }

    /**
     * 拿到数据中的所有任务
     * 并返回一个任务集合
     * @return
     */

        public List<Assignment> getAllAssignments() {

               String tableName  ="assignments";
            List<Assignment> assignments = new ArrayList<>();
            List<Map<String, Object>> result = new ArrayList<>();

            String sql = "SELECT * FROM " + tableName;

            try (Connection conn = DatabaseConfig.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // 获取结果集元数据
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // 遍历结果集
                while (rs.next()) {
                    Map<String, Object> rowData = new HashMap<>();

                    // 遍历所有列
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object value = rs.getObject(i);
                        rowData.put(columnName, value);
                    }

                    result.add(rowData);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Map<String, Object> rowData : result) {
                Assignment assignment = new Assignment(
                        (int) rowData.get("assignment_id"),
                        (int) rowData.get("report_id"),

                        (int) rowData.get("inspector_id"),
                        (int) rowData.get("assigned_by"),
                        ((Timestamp) rowData.get("assigned_at")).toLocalDateTime().toLocalDate(),
                        ((Timestamp) rowData.get("deadline")).toLocalDateTime().toLocalDate(),

                        "待定"
                );
                if(((String)rowData.get("status")).equals("PENDING"))assignment.setStatus("待处理");
                if (((String)rowData.get("status")).equals("ACCEPTED"))assignment.setStatus("已接受");
                if(((String)rowData.get("status")).equals("REJECTED"))assignment.setStatus("已拒绝");
                if (((String)rowData.get("status")).equals("COMPLETED"))assignment.setStatus("已完成");
                assignments.add(assignment
                 );
            }
            return assignments;
        }




}