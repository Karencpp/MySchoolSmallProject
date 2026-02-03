package com.ess.services;

import com.ess.config.DatabaseConfig;
import com.ess.models.AQIMeasurement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MeasurementService {
    /**
     * 传入一个测量报告的数据
     * 并在数据库中对应的表添加一个测量报告的数据
     * @param reportId
     * @param inspectorId
     * @param aqiValue
     * @param pm25
     * @param pm10
     * @param so2
     * @param no2
     * @param co
     * @param o3
     * @param temperature
     * @param humidity
     * @param windSpeed
     * @param notes
     * @return
     */
    public boolean createMeasurement(int reportId, int inspectorId, int aqiValue,
                                     double pm25, double pm10, double so2, double no2,
                                     double co, double o3, double temperature,
                                     double humidity, double windSpeed,  String notes) {
        String sql = "INSERT INTO aqi_measurements (report_id, inspector_id, aqi_value, " +
                "pm25, pm10, so2, no2, co, o3, temperature, humidity, wind_speed,notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reportId);
            stmt.setInt(2, inspectorId);
            stmt.setInt(3, aqiValue);
            stmt.setDouble(4, pm25);
            stmt.setDouble(5, pm10);
            stmt.setDouble(6, so2);
            stmt.setDouble(7, no2);
            stmt.setDouble(8, co);
            stmt.setDouble(9, o3);
            stmt.setDouble(10, temperature);
            stmt.setDouble(11, humidity);
            stmt.setDouble(12, windSpeed);
            stmt.setString(13, notes);
            boolean success = stmt.executeUpdate() > 0;

            if (success) {
                updateAssignmengStatus(reportId, "COMPLETED");
            }

            return success;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
//每次创建完一次测量数据,都要更新对应任务的状态

    /**
     * 传入监督报告ID,和状态字符串
     * 更新这个监督报告所对应的任务的状态
     * @param report_id
     * @param status
     * @throws SQLException
     */
    private void updateAssignmengStatus(int report_id, String status) throws SQLException {
        String sql = "UPDATE assignments SET status = ? WHERE report_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, report_id);
            stmt.executeUpdate();
        }
    }

    /**
     * 拿到数据库中所有的测量报告
     * 返回一个测量报告集合
     */
    public List<AQIMeasurement> getAllMeasurements() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<AQIMeasurement> measurements = new ArrayList<>();
        String sql = "SELECT * FROM " + "aqi_measurements";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Map<String, Object> rowData : result) {
            int report_id= (Integer) rowData.get("report_id");
             String location =  new ReportService().getReportByReportId(report_id).get("location").toString();

            measurements.add(new AQIMeasurement(
                    (Integer) rowData.get("measurement_id"),
                    report_id,
                    (Integer) rowData.get("inspector_id"),
                    (Integer) rowData.get("aqi_value"),
                    ((Number) rowData.get("pm25")).doubleValue(),
                    ((Number) rowData.get("pm10")).doubleValue(),
                    ((Number) rowData.get("so2")).doubleValue(),
                    ((Number) rowData.get("no2")).doubleValue(),
                    ((Number) rowData.get("co")).doubleValue(),
                    ((Number) rowData.get("o3")).doubleValue(),
                    ((Number) rowData.get("humidity")).doubleValue(),
                    ((Number) rowData.get("temperature")).doubleValue(),
                    ((Number) rowData.get("wind_speed")).doubleValue(),
                    ((Timestamp) rowData.get("measured_at")).toLocalDateTime(),
                    (String) rowData.get("notes"),
                    location
            ));
        }
        return measurements;
    }
    public List<AQIMeasurement> getMeasurementsByInspectorID(int inspectorId) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<AQIMeasurement> measurements = new ArrayList<>();
        String sql = "SELECT * FROM " + "aqi_measurements";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> FianlResult = result.stream().filter(rowData -> rowData.get("inspector_id").equals(inspectorId))
                .collect(Collectors.toList());

        for(Map<String, Object> rowData : FianlResult)
            measurements.add(new AQIMeasurement(
                    (Integer) rowData.get("measurement_id"),
                    (Integer) rowData.get("report_id"),
                    (Integer) rowData.get("inspector_id"),
                    (Integer) rowData.get("aqi_value"),
                    ((Number) rowData.get("pm25")).doubleValue(),
                    ((Number) rowData.get("pm10")).doubleValue(),
                    ((Number) rowData.get("so2")).doubleValue(),
                    ((Number) rowData.get("no2")).doubleValue(),
                    ((Number) rowData.get("co")).doubleValue(),
                    ((Number) rowData.get("o3")).doubleValue(),
                    ((Number) rowData.get("humidity")).doubleValue(),
                    ((Number) rowData.get("temperature")).doubleValue(),
                    ((Number) rowData.get("wind_speed")).doubleValue(),
                    ((Timestamp)rowData.get("measured_at")).toLocalDateTime(),
                    (String) rowData.get("notes")
            ));
        return measurements;
    }

    public boolean deleteMeasurementByMeasurementId(int  measurementId) {
        String sql = "DELETE FROM " + "aqi_measurements" + " WHERE " + "measurement_id" + " = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {



                pstmt.setInt(1,  measurementId);




            int rowsAffected = pstmt.executeUpdate();

            Map<String, Object> measurement = getMeasurementByMeasurementId(measurementId);
            Object reportId = measurement.get("report_id");
            int report_id = Integer.parseInt(reportId.toString());
          //删除测量数据时,要把对应任务的状态改为PENDING
            updateAssignmengStatus(report_id, "PENDING");

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> getMeasurementByMeasurementId( int measurementId ) {
        String sql = "SELECT * FROM " + "aqi_measurements" + " WHERE " + "measurement_id" + " = ?";
        Map<String, Object> row = new HashMap<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1,  measurementId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        String colName = metaData.getColumnName(i);
                        Object colValue = rs.getObject(i);
                        row.put(colName, colValue);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row;
    }

    /**
     * 根据省名或直辖市名,拿到该地的所有测量报告数据
     *
     * @param location  省名,或者直辖市名
     * @return List<AQIMeasurement> 测量数据的集合
     */
       public List<AQIMeasurement> getAllMeasurementsBylocation(String location){
           List<AQIMeasurement> measurements = getAllMeasurements().stream()
                   .filter(Measurement -> Measurement.getLocation().contains(location))
                   .collect(Collectors.toList());
                 return measurements;
       }


}