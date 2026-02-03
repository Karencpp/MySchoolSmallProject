package com.ess.utils;

import com.ess.models.Statistic;
import com.ess.services.StatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class LoadStatisticTableUtil{
    /**
     * 加载统计表格
     * @param statisticTable
     * @param locationOfstatistic
     * @param stat_id
     * @param period_type
     * @param period_start
     * @param period_end
     * @param total_reports
     * @param completed_reports
     * @param avg_aqi
     * @param max_aqi
     * @param min_aqi
     * @param created_at
     */
    public  static void LoadStatisticTable(
            TableView<Statistic> statisticTable,

    TableColumn<Statistic,String> locationOfstatistic,

    TableColumn<Statistic,Integer> stat_id,

    TableColumn<Statistic,String>  period_type,

    TableColumn<Statistic, LocalDate> period_start,

    TableColumn<Statistic,LocalDate> period_end,

    TableColumn<Statistic,Integer> total_reports,

    TableColumn<Statistic,Integer> completed_reports,

    TableColumn<Statistic,Double> avg_aqi,

    TableColumn<Statistic,Integer> max_aqi,

    TableColumn<Statistic,Integer> min_aqi,

    TableColumn<Statistic,LocalDate> created_at


){statisticTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        locationOfstatistic.setCellValueFactory(new PropertyValueFactory<>("location"));
        stat_id.setCellValueFactory(new PropertyValueFactory<>("stat_id"));
        period_type.setCellValueFactory(new PropertyValueFactory<>("period_type"));
        period_start.setCellValueFactory(new PropertyValueFactory<>("period_start"));
        period_end.setCellValueFactory(new PropertyValueFactory<>("period_end"));
        total_reports.setCellValueFactory(new PropertyValueFactory<>("total_reports"));
        completed_reports.setCellValueFactory(new PropertyValueFactory<>("completed_reports"));
        avg_aqi.setCellValueFactory(new PropertyValueFactory<>("avg_aqi"));
        max_aqi.setCellValueFactory(new PropertyValueFactory<>("max_aqi"));
        min_aqi.setCellValueFactory(new PropertyValueFactory<>("min_aqi"));
        created_at.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        statisticTable.setItems(getSatisticsData());
    }

    private static ObservableList<Statistic> getSatisticsData (){

        ObservableList<Statistic> satisticsData = FXCollections.observableArrayList(
                new StatService().getAllStatistics()
        );
          return satisticsData;

    }






}
