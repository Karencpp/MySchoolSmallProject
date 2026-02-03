package com.ess.utils;

import com.ess.models.AQIMeasurement;
import com.ess.services.MeasurementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class LoadMeasurementTableUtil {

    /**
     * 加载测量表格
     * @param measurementTable
     * @param measurement_idColumn
     * @param report_idColumn
     * @param inspector_idColumn
     * @param aqi_valueColumn
     * @param pm25Column
     * @param pm10Column
     * @param so2Column
     * @param no2Column
     * @param coColumn
     * @param o3Column
     * @param temperatureColumn
     * @param humidtyColumn
     * @param wind_speedColumn
     * @param measured_atColumn
     * @param notesColumn
     */

    public static void loadMeasurementTableOfInspector(

            TableView<AQIMeasurement> measurementTable,

            TableColumn<AQIMeasurement,Integer> measurement_idColumn,
    TableColumn<AQIMeasurement,Integer>  report_idColumn,


    TableColumn<AQIMeasurement,Integer> inspector_idColumn,


    TableColumn<AQIMeasurement,Integer> aqi_valueColumn,


    TableColumn<AQIMeasurement,Double> pm25Column,


    TableColumn<AQIMeasurement,Double> pm10Column,


    TableColumn<AQIMeasurement,Double> so2Column,


    TableColumn<AQIMeasurement,Double> no2Column,


    TableColumn<AQIMeasurement,Double> coColumn,


    TableColumn<AQIMeasurement,Double> o3Column,


    TableColumn<AQIMeasurement,Double> temperatureColumn,


    TableColumn<AQIMeasurement,Double> humidtyColumn,


    TableColumn<AQIMeasurement,Double> wind_speedColumn,


    TableColumn<AQIMeasurement, LocalDateTime> measured_atColumn,


    TableColumn<AQIMeasurement,String> notesColumn


    )
    {
        measurement_idColumn.setCellValueFactory(new PropertyValueFactory<>("measurement_id"));
        report_idColumn.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        inspector_idColumn.setCellValueFactory(new PropertyValueFactory<>("inspector_id"));
        aqi_valueColumn.setCellValueFactory(new PropertyValueFactory<>("aqi_value"));
        pm25Column.setCellValueFactory(new PropertyValueFactory<>("pm25"));
        pm10Column.setCellValueFactory(new PropertyValueFactory<>("pm10"));
        so2Column.setCellValueFactory(new PropertyValueFactory<>("so2"));
        no2Column.setCellValueFactory(new PropertyValueFactory<>("no2"));
        coColumn.setCellValueFactory(new PropertyValueFactory<>("co"));
        o3Column.setCellValueFactory(new PropertyValueFactory<>("o3"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        humidtyColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        wind_speedColumn.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
        measured_atColumn.setCellValueFactory(new PropertyValueFactory<>("measured_at"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        ObservableList<AQIMeasurement> measurementsData = LoadMeasurementTableUtil.getMeasurementsOfinspector();

        measurementTable.setItems(measurementsData);

    }

    public static void loadMeasurementTable(

            TableView<AQIMeasurement> measurementTable,

            TableColumn<AQIMeasurement,String>locationColumn,

            TableColumn<AQIMeasurement,Integer> measurement_idColumn,
            TableColumn<AQIMeasurement,Integer>  report_idColumn,


            TableColumn<AQIMeasurement,Integer> inspector_idColumn,


            TableColumn<AQIMeasurement,Integer> aqi_valueColumn,


            TableColumn<AQIMeasurement,Double> pm25Column,


            TableColumn<AQIMeasurement,Double> pm10Column,


            TableColumn<AQIMeasurement,Double> so2Column,


            TableColumn<AQIMeasurement,Double> no2Column,


            TableColumn<AQIMeasurement,Double> coColumn,


            TableColumn<AQIMeasurement,Double> o3Column,


            TableColumn<AQIMeasurement,Double> temperatureColumn,


            TableColumn<AQIMeasurement,Double> humidtyColumn,


            TableColumn<AQIMeasurement,Double> wind_speedColumn,


            TableColumn<AQIMeasurement, LocalDateTime> measured_atColumn,


            TableColumn<AQIMeasurement,String> notesColumn




    )
    { measurementTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        measurement_idColumn.setCellValueFactory(new PropertyValueFactory<>("measurement_id"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        report_idColumn.setCellValueFactory(new PropertyValueFactory<>("report_id"));
        inspector_idColumn.setCellValueFactory(new PropertyValueFactory<>("inspector_id"));
        aqi_valueColumn.setCellValueFactory(new PropertyValueFactory<>("aqi_value"));
        pm25Column.setCellValueFactory(new PropertyValueFactory<>("pm25"));
        pm10Column.setCellValueFactory(new PropertyValueFactory<>("pm10"));
        so2Column.setCellValueFactory(new PropertyValueFactory<>("so2"));
        no2Column.setCellValueFactory(new PropertyValueFactory<>("no2"));
        coColumn.setCellValueFactory(new PropertyValueFactory<>("co"));
        o3Column.setCellValueFactory(new PropertyValueFactory<>("o3"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        humidtyColumn.setCellValueFactory(new PropertyValueFactory<>("humidity"));
        wind_speedColumn.setCellValueFactory(new PropertyValueFactory<>("windSpeed"));
        measured_atColumn.setCellValueFactory(new PropertyValueFactory<>("measured_at"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        ObservableList<AQIMeasurement> measurementsData = LoadMeasurementTableUtil.getAllMeasurements();

        measurementTable.setItems(measurementsData);

    }

















    private static ObservableList<AQIMeasurement> getMeasurementsOfinspector() {
        return FXCollections.observableArrayList(
                new MeasurementService().getMeasurementsByInspectorID(GlobleData.currentUser.getUserId())
        );

    }
    private static ObservableList<AQIMeasurement> getAllMeasurements() {
        return FXCollections.observableArrayList(
                new MeasurementService().getAllMeasurements()
        );
    }

}
