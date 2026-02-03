package com.ess.controllers.inspector;

import com.ess.models.AQIMeasurement;
import com.ess.utils.AlertUtil;
import com.ess.utils.LoadMeasurementTableUtil;
import com.ess.utils.fxmlLoadUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class checkMeasurementsController {

    @FXML
    TableView<AQIMeasurement> measurementTable;

    @FXML
    TableColumn<AQIMeasurement,Integer>measurement_idColumn;


    @FXML
    TableColumn<AQIMeasurement,Integer> report_idColumn;

    @FXML
    TableColumn<AQIMeasurement,Integer> inspector_idColumn;

    @FXML
    TableColumn<AQIMeasurement,Integer> aqi_valueColumn;

    @FXML
    TableColumn<AQIMeasurement,Double> pm25Column;

    @FXML
    TableColumn<AQIMeasurement,Double> pm10Column;

    @FXML
    TableColumn<AQIMeasurement,Double> so2Column;

    @FXML
    TableColumn<AQIMeasurement,Double> no2Column;

    @FXML
    TableColumn<AQIMeasurement,Double> coColumn;

    @FXML
    TableColumn<AQIMeasurement,Double> o3Column;

    @FXML
    TableColumn<AQIMeasurement,Double> temperatureColumn;

    @FXML
    TableColumn<AQIMeasurement,Double> humidtyColumn;

    @FXML
    TableColumn<AQIMeasurement,Double> wind_speedColumn;

    @FXML
    TableColumn<AQIMeasurement, LocalDateTime> measured_atColumn;

    @FXML
    TableColumn<AQIMeasurement,String> notesColumn;


    /**
     * 网格员查看自己所提交的测量报告时所对应的页面
     */



    @FXML
    public void initialize() {


        LoadMeasurementTableUtil.loadMeasurementTableOfInspector(
                measurementTable,
                measurement_idColumn,
                report_idColumn,
                inspector_idColumn,
                aqi_valueColumn,
                pm25Column,
                pm10Column,
                so2Column,
                no2Column,
                coColumn,
                o3Column,
                temperatureColumn,
                humidtyColumn,
                wind_speedColumn,
                measured_atColumn,
                notesColumn
        );
    }
    @FXML
    private void exit(){
        if(AlertUtil.showConfirmation("退出","你确定要退出吗")){
            Stage stage = (Stage)measurementTable.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private void refresh(){
        LoadMeasurementTableUtil.loadMeasurementTableOfInspector(
                measurementTable,
                measurement_idColumn,
                report_idColumn,
                inspector_idColumn,
                aqi_valueColumn,
                pm25Column,
                pm10Column,
                so2Column,
                no2Column,
                coColumn,
                o3Column,
                temperatureColumn,
                humidtyColumn,
                wind_speedColumn,
                measured_atColumn,
                notesColumn
        );
        measurementTable.refresh();
        AlertUtil.showInfo("提示","刷新成功");

    }

    /**
     * 进入修改网格员提交的测量报告对应页面
     */
    @FXML
    private void modify(){
        Stage stage = new Stage();
        fxmlLoadUtil.loadFXML(stage,"/fxml/views/inspector/modify_measurement.fxml", null);
    }

}
