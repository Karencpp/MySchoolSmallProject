package com.ess.controllers.Public;



import com.ess.models.User;
import com.ess.services.ReportService;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.stage.Stage;



public class ReportController  {





    @FXML private Label welcomeLabel;



    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button getBackBt;

    @FXML
    private TextArea locationArea;

    @FXML
    private Button submitBt;

    @FXML
    void handleGetBack(ActionEvent event) {
        // 回到dashboard
            Stage stage = (Stage) getBackBt.getScene().getWindow();
       stage.close();
    }

    /**
     * 提交公众监督员提交的报告,调用service层方法来创建报告
     * @param event
     */
    @FXML
    void handleSubmit(ActionEvent event) {
        String description = descriptionArea.getText();
        String location = locationArea.getText();
        if (description.isEmpty() || location.isEmpty()) {
            AlertUtil.showError("错误", "内容不能为空");
            return;
        }
        ReportService  reportService = new ReportService();
        boolean success = reportService.createReport(GlobleData.currentUser.getUserId(), location, 0, 0, description);
         if(success){
             AlertUtil.showInfo("成功", "提交成功");
         }

    }

   @FXML
           public void initialize()
    {
        welcomeLabel.setText("欢迎, " + GlobleData.currentUser.getFullName());
    }



}