package com.ess.controllers.admin;


import com.ess.services.ReportService;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import com.ess.utils.fxmlLoadUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
//通过 @FXML 注解绑定 FXML 文件中的 UI 元素（如表格、标签等

/**
 * 管理员仪表盘控制器类，负责处理管理员界面的主要操作和导航功能
 */
public class AdminDashboardController  {
    private ReportService reportService = new ReportService();

    @FXML
    private BorderPane mainContent;

    /**
     * 处理退出操作，显示确认对话框并根据用户选择执行退出或返回
     * @param event 触发此操作的ActionEvent事件
     */
    @FXML
    void handleExit(ActionEvent event) {
        //退出之前加一个判断
        if(AlertUtil.showConfirmation("退出", "确定要退出吗？")){
            fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/common/login.fxml", null);
        }
          else{
              return;
        }
    }

    /**
     * 处理报告管理操作，加载并显示报告管理界面
     * @param event 触发此操作的ActionEvent事件
     */
    @FXML
    void handleReportManagement(ActionEvent event) {
        try {
            // 加载报告管理界面
            FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/fxml/views/admin/ReportManagementView.fxml")
            );

            BorderPane reportManagementView = loader.load();
             mainContent.setCenter(reportManagementView);
             // 将报告管理界面显示在主界面的中心区域
        } catch (Exception e) {
            showError("加载错误", "无法加载报告管理界面: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 处理任务管理操作，加载并显示任务管理界面
     * @param event 触发此操作的ActionEvent事件
     */
    @FXML
    void handleTaskManagement(ActionEvent event) {
        try {
            // 加载任务管理界面
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/views/admin/AssignmentManagementView.fxml")
            );
            BorderPane taskManagementView = loader.load();

            // 将任务管理界面显示在主界面的中心区域
            mainContent.setCenter(taskManagementView);
        } catch (Exception e) {
            showError("加载错误", "无法加载任务管理界面: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 处理用户管理操作，加载并显示用户管理界面
     * @param event 触发此操作的ActionEvent事件
     */
    @FXML
    void handleUserManagement(ActionEvent event) {
        try {
            // 加载用户管理界面
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/views/admin/UserManagementView.fxml")
            );
            BorderPane userManagementView = loader.load();

            // 将用户管理界面显示在主界面的中心区域
            mainContent.setCenter(userManagementView);
        } catch (Exception e) {
            showError("加载错误", "无法加载用户管理界面: " + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 显示错误对话框
     * @param title 错误对话框的标题
     * @param message 要显示的错误信息
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * 处理AQI管理操作，加载并显示AQI管理界面
     * @param event 触发此操作的ActionEvent事件
     */
    @FXML
    void handleAQIManagement(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/views/admin/AQI.fxml")
        );
        try {
            AnchorPane root = fxmlLoader.load();
            mainContent.setCenter(root);
        } catch (IOException e) {
            System.out.println("加载AQI管理界面出错了");
            e.printStackTrace();
        }


    }
    /**
     * 切换到公共仪表盘界面
     */
    @FXML
    private void switchToPublic(){
        GlobleData.stage.setTitle(GlobleData.PublicTitle);
        fxmlLoadUtil.loadFXML(GlobleData.stage,"/fxml/views/Public/dashboard.fxml", null);

    }
}










