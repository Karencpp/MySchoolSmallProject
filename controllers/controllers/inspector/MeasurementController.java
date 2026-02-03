package com.ess.controllers.inspector;

import com.ess.config.DatabaseConfig;
import com.ess.models.Assignment;
import com.ess.models.User;
import com.ess.services.AssignmentService;
import com.ess.services.MeasurementService;
import com.ess.utils.AQICalculator;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Collectors;


/**
 * 网格员提交测量报告时对应的页面
 */




public class MeasurementController {
    @FXML private TextField reportIdField;

    @FXML private TextField pm25Field;
    @FXML private TextField pm10Field;
    @FXML private TextField so2Field;
    @FXML private TextField no2Field;
    @FXML private TextField coField;
    @FXML private TextField o3Field;
    @FXML private TextField tempField;
    @FXML private TextField humidityField;
    @FXML private TextField windSpeedField;
    @FXML private TextField notesField;
    private int reportId;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    private User currentUser;
    private MeasurementService measurementService = new MeasurementService();

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * 处理网格员输入的测量数据,判断输入格式,并调用service层方法来提交数据
     */
    @FXML
    private void handleSubmitMeasurement() {









        int reportId =0;
        double pm25 = 0;
        double pm10 = 0;
        double so2 = 0;
        double no2 = 0;
        double co = 0;
        double o3 = 0;
        double temp = 0;
        double humidity = 0;
        double windSpeed = 0;
        try {
            reportId = Integer.parseInt(reportIdField.getText());
            final  int reportId1=reportId;
            ArrayList<Assignment> Select =new AssignmentService().getAssignmentsByInspector(GlobleData.currentUser.getUserId()).stream()
                    .filter(assignment -> (assignment.getReport_id() == reportId1)&&(assignment.getStatus().equals("待处理")))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (Select.size()==0) {
                AlertUtil.showError("错误", "没有此任务或该任务已经完成");
                return;
            }
            pm25 = Double.parseDouble(pm25Field.getText());
            pm10 = Double.parseDouble(pm10Field.getText());
            so2 = Double.parseDouble(so2Field.getText());
            no2 = Double.parseDouble(no2Field.getText());
            co = Double.parseDouble(coField.getText());
            o3 = Double.parseDouble(o3Field.getText());
            temp = Double.parseDouble(tempField.getText());
            humidity = Double.parseDouble(humidityField.getText());
            windSpeed = Double.parseDouble(windSpeedField.getText());
        } catch (Exception e) {
                AlertUtil.showError("错误", "所有字段必须输入有效的数字");
                return;
        }






        String notes = notesField.getText();


        int aqi_value = AQICalculator.calculateAQI(
                pm25,
                pm10,
                so2,
                no2,
                co,
                o3


        );



        try {

            boolean success = measurementService.createMeasurement(
                    reportId,
                    GlobleData.currentUser.getUserId(),
                    aqi_value,
                    pm25,
                    pm10,
                    so2,
                    no2,
                    co,
                    o3,
                    temp,
                    humidity,
                    windSpeed,
                    notes
            );

            if (success) {
                AlertUtil.showInfo("成功", "AQI检测数据已提交");
                Clear();
            } else {
                AlertUtil.showError("错误", "提交检测数据失败");
            }
        } catch (NumberFormatException e) {
            AlertUtil.showError("错误", "所有字段必须输入有效的数字");
        }
    }
@FXML
    private void Clear() {
        reportIdField.clear();

        pm25Field.clear();
        pm10Field.clear();
        so2Field.clear();
        no2Field.clear();
        coField.clear();
        o3Field.clear();
        tempField.clear();
        humidityField.clear();
        windSpeedField.clear();
    }
    @FXML
    private void handleCancel() {
        Stage stage = (Stage)tempField.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void levelOne(){
        int key = (int)(Math.random()*3)+1;
        switch (key){
            case 1:
                pm25Field.setText("35");
                pm10Field.setText("50");
                so2Field.setText("20");
                no2Field.setText("40");
                coField.setText("2");
                o3Field.setText("30");
                tempField.setText("25");
                humidityField.setText("60");
                windSpeedField.setText("10");
                notesField.setText("无");

                break;

            case 2:
                pm25Field.setText("20");
                pm10Field.setText("30");
                so2Field.setText("30");
                no2Field.setText("20");
                coField.setText("3");
                o3Field.setText("80");
                tempField.setText("30");
                humidityField.setText("70");
                windSpeedField.setText("10");
                notesField.setText("无");

                break;
            case 3:
                pm25Field.setText("10");
                pm10Field.setText("20");
                so2Field.setText("40");
                no2Field.setText("30");
                coField.setText("5");
                o3Field.setText("70");
                tempField.setText("30");
                humidityField.setText("70");
                windSpeedField.setText("20");
                notesField.setText("无");

                break;

        }
    }
    @FXML private void levelTwo(){
        pm25Field.setText("75");
        pm10Field.setText("50");
        so2Field.setText("50");
        no2Field.setText("40");
        coField.setText("2");
        o3Field.setText("100");
        tempField.setText("25");
        humidityField.setText("60");
        windSpeedField.setText("10");
        notesField.setText("无");

    }
    @FXML
    private void levelThree(){
        pm25Field.setText("35");
        pm10Field.setText("250");
        so2Field.setText("50");
        no2Field.setText("40");
        coField.setText("2");
        o3Field.setText("100");
        tempField.setText("25");
        humidityField.setText("60");
        windSpeedField.setText("10");
        notesField.setText("无");


    }
    @FXML
    private void levelFour(){
        pm25Field.setText("35");
        pm10Field.setText("50");
        so2Field.setText("800");
        no2Field.setText("40");
        coField.setText("2");
        o3Field.setText("100");
        tempField.setText("25");
        humidityField.setText("60");
        windSpeedField.setText("10");
        notesField.setText("无");


    }
    @FXML
    private void levelFive(){
        pm25Field.setText("35");
        pm10Field.setText("50");
        so2Field.setText("50");
        no2Field.setText("400");
        coField.setText("2");
        o3Field.setText("100");
        tempField.setText("25");
        humidityField.setText("60");
        windSpeedField.setText("10");
        notesField.setText("无");



    }
    @FXML
    private void levelSix(){
        pm25Field.setText("35");
        pm10Field.setText("50");
        so2Field.setText("50");
        no2Field.setText("40");
        coField.setText("2");
        o3Field.setText("500");
        tempField.setText("25");
        humidityField.setText("60");
        windSpeedField.setText("10");
        notesField.setText("无");


    }

}