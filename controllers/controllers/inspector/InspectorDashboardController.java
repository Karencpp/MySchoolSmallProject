package com.ess.controllers.inspector;


import com.ess.models.AirQualityReport;
import com.ess.models.User;
import com.ess.models.Assignment;
import com.ess.services.AssignmentService;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import com.ess.utils.LoadAssignmentTableUtil;
import com.ess.utils.fxmlLoadUtil;
import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class InspectorDashboardController  {
    @FXML private Label welcomeLabel;
    @FXML private TableView<Assignment> assignmentsTable;

    @FXML private TableColumn<Assignment,Integer>assignmentIdColumn;
    @FXML private TableColumn<Assignment, Integer> reportIdColumn;
    @FXML private TableColumn<Assignment, Integer>assigned_byColumn;
    @FXML private TableColumn<Assignment, LocalDate> assigned_atColumn;
    @FXML private TableColumn<Assignment,Integer>inspectorIdColumn;
    @FXML private TableColumn<Assignment, LocalDate> deadlineColumn;
    @FXML private TableColumn<Assignment, String> statusColumn;

    @FXML private Label locationLabel;//显示网格员负责的位置


    private AssignmentService assignmentService = new AssignmentService();



    /**
     * 网格员端主页面
     * 主要显示网格员所被指派的任务
     */

    @FXML
    private void initialize() {
        // 初始化表格列
        LoadAssignmentTableUtil.loadAssignmentTableOfInspector(
                assignmentsTable,
                assignmentIdColumn,
                reportIdColumn,
                inspectorIdColumn,
                assigned_byColumn,
                assigned_atColumn,
                deadlineColumn,
                statusColumn
        );
        welcomeLabel.setText("欢迎您，" + GlobleData.currentUser.getFullName());
    }


    /**
     * 进入提交网格员测量报告的页面
     * @throws IOException
     */
    @FXML
    private void handleNewMeasurement() throws IOException {
       Stage stage = new Stage();
       fxmlLoadUtil.loadFXML(stage,"/fxml/views/inspector/measurement_form.fxml", null);

    }

    /**
     * 退出
     * @throws IOException
     */
    @FXML
    private void handleLogout() throws IOException {
        if (AlertUtil.showConfirmation("确认退出", "确定要退出系统吗?")) {
             fxmlLoadUtil.loadFXML(GlobleData.stage,"/fxml/views/common/login.fxml", null);
        }
    }

    /**
     * 刷新任务表格
     */
    @FXML
    private void handleRefresh(){
        LoadAssignmentTableUtil.loadAssignmentTableOfInspector(
                assignmentsTable,
                assignmentIdColumn,
                reportIdColumn,
                inspectorIdColumn,
                assigned_byColumn,
                assigned_atColumn,
                deadlineColumn,
                statusColumn
        );
        assignmentsTable.refresh();
        AlertUtil.showInfo("提示","刷新成功");
    }

    /**
     * 查看自己的测量报告
     */
    @FXML
    private void checkMyMeasurements(){
           Stage stage = new Stage();
           fxmlLoadUtil.loadFXML(stage,"/fxml/views/inspector/checkMeasurements.fxml", null);
    }

    /**
     * 跳转到公众监督员端
     */
    @FXML
    private void switchToPublic(){
        GlobleData.stage.setTitle(GlobleData.PublicTitle);
        fxmlLoadUtil.loadFXML(GlobleData.stage,"/fxml/views/Public/dashboard.fxml", null);
    }
}