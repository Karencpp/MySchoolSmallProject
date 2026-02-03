package com.ess.controllers.Public;

import com.ess.models.AirQualityReport;
import com.ess.models.User;
import com.ess.services.ReportService;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import com.ess.utils.LoadReportTableUtil;
import com.ess.utils.fxmlLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;


import javafx.scene.control.Label;

public class PublicDashboardController {


    @FXML private Label welcomeLabel;
    @FXML private TableView<AirQualityReport> reportsTable;

    @FXML
    private TableColumn<AirQualityReport, Integer> Report_id;

    @FXML
    private TableColumn<AirQualityReport, Integer> User_id;

    @FXML
    private TableColumn<AirQualityReport, String> Location;
    @FXML
    private TableColumn<AirQualityReport, String> Description;

    @FXML
    private TableColumn<AirQualityReport, String> Staus;

    @FXML
    private TableColumn<AirQualityReport, LocalDateTime> Create_at;


    /**
     * 初始化表格,从数据库拿到数据,呈现在页面表格中
     */


      public void initialize(){

          LoadReportTableUtil.loadReportTableOfPublic(
                  reportsTable,
                  Report_id,
                  User_id,
                  Location,
                  Description,
                  Staus,
                  Create_at

          );
          welcomeLabel.setText("欢迎, " + GlobleData.currentUser.getFullName());
       }

    /**
     * 处理公众监督员提交的报告
     * @throws IOException
     */
    @FXML
    private void handleNewReport() throws IOException {

     Stage stage =new Stage();
     stage.setTitle("新增报告");
     fxmlLoadUtil.loadFXML(stage,"/fxml/views/Public/report_form.fxml", null);

    }

    /**
     * 退出
     * @throws IOException
     */

    @FXML
    private void handleLogout() throws IOException {
        if (AlertUtil.showConfirmation("确认退出", "确定要退出系统吗?")) {
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/views/common/login.fxml"));
            Parent root = fxmlLoader.load();
            GlobleData.stage.setScene(new Scene(root));
            GlobleData.stage.centerOnScreen();
        }
    }

    /**
     * 刷新报告表格
     */
    @FXML
    private void handleRefresh() {
       LoadReportTableUtil.loadReportTableOfPublic(
                reportsTable,
                Report_id,
                User_id,
                Location,
                Description,
                Staus,
                Create_at
        );
        reportsTable.refresh();
        AlertUtil.showInfo("提示", "报告列表已刷新");
    }

    /**
     * 跳转到其他角色页面
     */
    @FXML
    private void switchToOtherRole(){
          if(AlertUtil.showConfirmation("确认切换角色", "确定要切换角色吗?")){
              fxmlLoadUtil.loadFXML(GlobleData.stage,"/fxml/views/common/login.fxml", null);
          }
    }
}