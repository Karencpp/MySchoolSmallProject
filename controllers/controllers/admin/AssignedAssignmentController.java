package com.ess.controllers.admin;

import com.ess.models.AirQualityReport;
import com.ess.models.Assignment;
import com.ess.models.User;
import com.ess.services.AssignmentService;
import com.ess.services.ReportService;
import com.ess.utils.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

import static com.ess.utils.GlobleData.currentUser;

public class AssignedAssignmentController {
    //===========下面放任务表组件
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
//===============下面放报告表组件
    @FXML
    private  TableView<AirQualityReport> reportTable;
    @FXML

    private TableColumn<AirQualityReport,Integer>report_idOfReportTable;
    @FXML
    private TableColumn<AirQualityReport,String>locationOfReportTableColumn;
    @FXML
    private TableColumn<AirQualityReport,LocalDate>Create_atOfReportTableColumn;
    @FXML
    private TableColumn<AirQualityReport, String> statusOfReportTableColumn;

//下面放网格员表组件
@FXML
private TableView<User> inspectorTable;

    @FXML
    private TableColumn<User, Integer> inspector_idOfInspectorTableColumn;
    @FXML
    private TableColumn<User, String> inspector_locationColumn;
//下面放文本框组件
@FXML
private TextField reportIDTextField;
    @FXML
    private TextField deadlineTextField;
    @FXML
    private TextField inspectorIDTextField;

    /**
     * 初始化方法，用于加载任务表、报告表和检查人员表的数据到界面。
     * 该方法会调用三个工具类来分别加载不同的表格数据。
     */
    public void initialize()
    {
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
        LoadReportTableUtil.loadReportTable(
                reportTable,
                report_idOfReportTable,
                locationOfReportTableColumn,
                Create_atOfReportTableColumn,
                statusOfReportTableColumn

        );
        LoadInspectorTableUtil.loadInspectorTable(
                inspectorTable,
                inspector_idOfInspectorTableColumn,
                inspector_locationColumn
        );

    }


    /**
     * 处理退出按钮点击事件。
     * 显示确认对话框，如果用户确认退出，则关闭当前窗口。
     * 
     * @param event 触发此操作的动作事件
     */
    @FXML
    public void handleExit(ActionEvent event){
          Stage stage = (Stage)reportIDTextField.getScene().getWindow();
          if(AlertUtil.showConfirmation("提示", "确定要退出吗？"))
          stage.close();
    }
    /**
     * 处理创建任务按钮点击事件。
     * 验证用户输入数据的有效性，并尝试创建新的任务。
     * 
     * @param event 触发此操作的动作事件
     */
    @FXML
    public void handleAssi(ActionEvent event){
        //拿到输入框中的数据,并对格式进行检查

        //检查输入框中数据是否为空
        if(reportIDTextField.getText().isEmpty()||deadlineTextField.getText().isEmpty()||inspectorIDTextField.getText().isEmpty()) {
            AlertUtil.showError("错误", "请填写所有必填字段");
            return;
        }
        //检查网格员ID是否为数字
        if(!inspectorIDTextField.getText().matches("[0-9]+")) {

            AlertUtil.showError("错误", "网格员ID只能为数字");

            return;
        }
        //检查报告ID是否为数字
        if(!reportIDTextField.getText().matches("[0-9]+")) {

            AlertUtil.showError("错误", "报告ID只能为数字");

            return;
        }

        //检查输入的网格员ID是否在数据库中
        if(!inspectorTable.getItems().stream().anyMatch(user -> user.getUserId() == Integer.parseInt(inspectorIDTextField.getText()))) {

            AlertUtil.showError("错误", "网格员ID不存在");
            return;
        }
        //检查输入的报告ID是否在数据库中
        if(!reportTable.getItems().stream().
                anyMatch(report -> report.getReportId() == Integer.parseInt(reportIDTextField.getText()))) {
            AlertUtil.showError("错误", "报告ID不存在");
            return;
        }
        //检查报告的状态是否是是待处理
        if(!reportTable.getItems().stream().anyMatch(report -> report.getReportId()==Integer.parseInt(reportIDTextField.getText())&&report.getStatus().equals("待处理"))){
                       AlertUtil.showError("错误", "该报告已经被处理");
                       return;
        }
        //检查输入的截止日期格式是否正确
        if(!DateUtil.isValidDate(deadlineTextField.getText())) {
            AlertUtil.showError("错误", "截止日期格式错误");
            return;
        }
        //检查输入的截止日期是否早于当前日期
        if(DateUtil.parseDate(deadlineTextField.getText()).isBefore(LocalDate.now())) {
            AlertUtil.showError("错误", "截止日期不能早于当前日期");
            return;
        }
        //检查输入的截止日期格式是否正确
        if(!DateUtil.isValidDate(deadlineTextField.getText())) {
            AlertUtil.showError("错误", "截止日期格式错误");
            return;
        }
// 把输入框中的数据类型转为创建任务需要的类型
       int reportID = Integer.parseInt(reportIDTextField.getText());
       int inspectorID = Integer.parseInt(inspectorIDTextField.getText());
       int assigned_by= currentUser.getUserId();
       LocalDate deadline = DateUtil.parseDate(deadlineTextField.getText());


        //根据输入框中数据来创建一个任务
        if(new AssignmentService().createAssignment(reportID, inspectorID, assigned_by, deadline))
            AlertUtil.showInfo("创建任务", "任务创建成功！,同时您已经成功把对应报告的状态改成了"+"已处理");


    }
    /**
     * 处理刷新按钮点击事件。
     * 重新加载任务表和报告表的数据，并刷新表格显示。
     * 
     * @param event 触发此操作的动作事件
     */
    @FXML
    public void handleRefresh(ActionEvent event){
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
         LoadReportTableUtil.loadReportTable(
                reportTable,
                report_idOfReportTable,
                locationOfReportTableColumn,
                Create_atOfReportTableColumn,
                 statusOfReportTableColumn
        );
         reportTable.refresh();
        assignmentTable.refresh();
        AlertUtil.showInfo("提示","刷新成功");
    }
}
