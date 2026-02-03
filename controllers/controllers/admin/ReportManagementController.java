package com.ess.controllers.admin;

import com.ess.models.AirQualityReport;
import com.ess.services.ReportService;
import com.ess.utils.AlertUtil;
import com.ess.utils.LoadReportTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class ReportManagementController {

    @FXML
    private TableView<AirQualityReport> reportTable;

    @FXML
    private TableColumn<AirQualityReport, Integer> reportIdColumn;

    @FXML
    private TableColumn<AirQualityReport, Integer> userIdColumn;

    @FXML
    private TableColumn<AirQualityReport, String> locationColumn;

    @FXML
    private TableColumn<AirQualityReport, String> descriptionColumn;

    @FXML
    private TableColumn<AirQualityReport, String> statusColumn;

    @FXML
    private TableColumn<AirQualityReport,LocalDateTime> timeColumn;

    @FXML private  TextField TextFieldOfID;

    // 从数据库获取数据的方法
    private ObservableList<AirQualityReport> reportData = FXCollections.observableArrayList(
            new ReportService().getAllReports());
            // 这里应该用实际从数据库查询的数据填充


    /**
     * 初始化方法，在控制器被创建时调用
     * 用于加载报告表格数据并设置表格列
     */
    public void initialize() {

        LoadReportTableUtil.loadReportTable(
                reportTable,
                reportIdColumn,
                userIdColumn,
                locationColumn,
                descriptionColumn,
                statusColumn,
                timeColumn
        );
    }

    /**
     * 处理报告按钮点击事件
     * 打开处理报告的窗口界面
     * @param event 触发该方法的动作事件
     */
    @FXML
    void hanleRep(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("处理报告");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/views/admin/handle_report.fxml"));
        try {
            BorderPane root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
             e.printStackTrace();
            System.out.println("加载处理报告页面失败");
        }

    }

    /**
     * 刷新按钮点击事件处理方法
     * 从新加载所有报告数据并刷新表格显示
     * @param event 触发该方法的动作事件
     */
    @FXML
    void handleRefresh(ActionEvent event) {
        reportTable.getItems().clear();
        reportTable.getItems().addAll(new ReportService().getAllReports());
        reportTable.refresh();
        AlertUtil.showInfo("提示","刷新成功");
    }

    /**
     * 退出按钮点击事件处理方法
     * 关闭当前窗口
     * @param event 触发该方法的动作事件
     */
    @FXML
    public void handleExit(ActionEvent event) {
        Stage stage  = (Stage)TextFieldOfID.getScene().getWindow();
        stage.close();
    }


}
