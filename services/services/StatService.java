package com.ess.services;

import com.ess.config.DatabaseConfig;
import com.ess.models.Statistic;

import java.sql.*;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatService {
    /**
     *   传入必要的需要统计的信息
     *   在数据库新建一条统计报告
     * @param totalReports
     * @param avgAqi
     * @param location
     * @return
     */

    public boolean createStat( int totalReports,  double avgAqi,    String location) {
        String sql = "INSERT INTO statistics ( total_reports,  avg_aqi, location,period_type,period_start,period_end,completed_reports,max_aqi,min_aqi) " +
                "VALUES (?, ?, ?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, totalReports);
            stmt.setDouble(2, avgAqi);
            stmt.setString(3, location);
            stmt.setString(4, "DAILY");
            stmt.setDate(5, Date.valueOf(LocalDate.now()));
            stmt.setDate(6, Date.valueOf(LocalDate.now()));
            stmt.setInt(7, totalReports);
            stmt.setInt(8, 400);
            stmt.setInt(9,50);


            return stmt.executeUpdate() > 0;
             }catch (Exception e){
            System.out.println("添加统计数据失败");
              e.printStackTrace();
              return false;
        }
  }


    /**
     * 拿到所有的统计报告
     * @return
     */




    public   List<Statistic> getAllStatistics() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Statistic> statistics= new ArrayList<>();
        String sql = "SELECT * FROM " + "statistics";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 获取结果集的元数据
            int columnCount = rs.getMetaData().getColumnCount();

            // 遍历结果集
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                result.add(row);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("获取统计数据时出问题了");
        }
       for(Map<String, Object> rowData : result){


           Statistic statistic = new Statistic(
                   (Integer) rowData.get("stat_id"),
                   (String) rowData.get("period_type"),
                   ((Date) rowData.get("period_start")).toLocalDate(),
                   ((Date) rowData.get("period_end")).toLocalDate(),
                   (Integer) rowData.get("total_reports"),
                   (Integer) rowData.get("completed_reports"),
                     Double.parseDouble(rowData.get("avg_aqi").toString()),
                   (Integer) rowData.get("max_aqi"),
                   (Integer) rowData.get("min_aqi"),
                    ((Timestamp)rowData.get("created_at")).toLocalDateTime(),
                   (String) rowData.get("location")
            );

           if(rowData.get("period_type").equals("DAYLY"))statistic.setPeriod_type("日");
           if(rowData.get("period_type").equals("WEEKLY"))statistic.setPeriod_type("周");
           if(rowData.get("period_type").equals("MONTHLY"))statistic.setPeriod_type("月");
           if(rowData.get("period_type").equals("YEARLY"))statistic.setPeriod_type("年");

           statistics.add( statistic);

       }

        return statistics;
    }


  }