package com.ess.controllers.admin;


import com.ess.models.Assignment;

import com.ess.services.AssignmentService;
import com.ess.utils.AlertUtil;
import com.ess.utils.LoadAssignmentTableUtil;
import com.ess.utils.fxmlLoadUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AssignmentManagementController {

    @FXML
    private TableView<Assignment> assignmentTable;

    @FXML
    private TableColumn<Assignment, Integer> assignment_idColumn;

    @FXML
    private TableColumn<Assignment, Integer> report_idColumn;

    @FXML
    private TableColumn<Assignment, Integer> inspector_idColumn;

    @FXML
    private TableColumn<Assignment, Integer> assigned_byColumn;

    @FXML
    private TableColumn<Assignment, LocalDate> assigned_atColumn;

    @FXML
    private TableColumn<Assignment, LocalDate> deadlineColumn;

    @FXML
    private TableColumn<Assignment, String> statusColumn;

    //================================下面放报告表组件


      private   ObservableList<Assignment> assignmentsData = FXCollections.observableArrayList(
                new AssignmentService().getAllAssignments()
        );

    /**
     * 初始化方法，在控制器初始化时调用
     * 用于加载并配置任务表格的数据和列
     */
    public void initialize() {
        // 初始化任务表格
        LoadAssignmentTableUtil.loadAssignmentTable(
                assignmentTable,
                assignment_idColumn,
                report_idColumn,
                inspector_idColumn,
                assigned_byColumn,
                assigned_atColumn,
                deadlineColumn,
                statusColumn
        );
    }

    /**
     * 处理添加任务按钮的点击事件
     * @param event ActionEvent 触发该方法的事件对象
     * 打开新的窗口显示添加任务管理页面
     */
    @FXML
    void handleAddAssi(ActionEvent event) {
        //添加任务
        Stage stage = new Stage();

        stage.setTitle("指派任务页面");

        stage.setMinWidth(800); // 根据 FXML 的 prefWidth 设置合适的值

        stage.setMinHeight(600);

        Scene scene=null;

        fxmlLoadUtil.loadFXML(stage,"/fxml/views/admin/assigned_assignment.fxml",scene);

    }

    /**
     * 处理刷新按钮的点击事件
     * @param event ActionEvent 触发该方法的事件对象
     * 刷新任务列表数据并显示提示信息
     */
    @FXML
    void handleRefresh(ActionEvent event) {
        assignmentsData = FXCollections.observableArrayList(
                new AssignmentService().getAllAssignments()
        );
        assignmentTable.setItems(assignmentsData);
        assignmentTable.refresh();
        AlertUtil.showInfo("提示","刷新成功");

    }
}
