package com.ess.utils;

import com.ess.models.AirQualityReport;
import com.ess.services.ReportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoadReportTableUtil {

    /**
     * 加载空气报告数据
     * @param reportTable
     * @param reportIdColumn
     * @param userIdColumn
     * @param locationColumn
     * @param descriptionColumn
     * @param statusColumn
     * @param timeColumn
     */
    public static void loadReportTable(



             TableView<AirQualityReport> reportTable,


           TableColumn<AirQualityReport, Integer> reportIdColumn,


           TableColumn<AirQualityReport, Integer> userIdColumn,


           TableColumn<AirQualityReport, String> locationColumn,


             TableColumn<AirQualityReport, String> descriptionColumn,


             TableColumn<AirQualityReport, String> statusColumn,


           TableColumn<AirQualityReport, LocalDateTime> timeColumn
    ) {
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 初始化表格列
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // 设置状态列的编辑功能
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        // 设置表格数据
        reportTable.setItems(getReportData());
        reportTable.setEditable(true);
    }
    public static void loadReportTable(

            TableView<AirQualityReport> reportTable,
            TableColumn<AirQualityReport, Integer> report_idOfReportTable,

            TableColumn<AirQualityReport, String> locationOfReportTableColumn,

            TableColumn<AirQualityReport, LocalDate> Create_atOfReportTableColumn,
            TableColumn<AirQualityReport, String> statusOfReportTableColumn

    ){
        report_idOfReportTable.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        locationOfReportTableColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        Create_atOfReportTableColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        statusOfReportTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        // 设置表格数据
        reportTable.setItems(getReportData());
        reportTable.setEditable(true);
    }
    private static ObservableList<AirQualityReport> getReportData(){
        ObservableList<AirQualityReport> reportData = FXCollections.observableArrayList(
              new   ReportService().getAllReports());

        return reportData;
    }
    private static ObservableList<AirQualityReport> getReportDataOfPublic(){
        ObservableList<AirQualityReport> reportData = FXCollections.observableArrayList(
                new   ReportService().getReportsByUser(GlobleData.currentUser.getUserId()));

        return reportData;

    }
    public static void loadReportTableOfPublic(



            TableView<AirQualityReport> reportTable,


            TableColumn<AirQualityReport, Integer> reportIdColumn,


            TableColumn<AirQualityReport, Integer> userIdColumn,


            TableColumn<AirQualityReport, String> locationColumn,


            TableColumn<AirQualityReport, String> descriptionColumn,


            TableColumn<AirQualityReport, String> statusColumn,


            TableColumn<AirQualityReport, LocalDateTime> timeColumn



    ) {
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 初始化表格列
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // 设置状态列的编辑功能
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        // 设置表格数据
        reportTable.setItems(getReportDataOfPublic());
        reportTable.setEditable(true);
    }

}
