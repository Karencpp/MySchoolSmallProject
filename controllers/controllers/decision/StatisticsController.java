package com.ess.controllers.decision;

import com.ess.models.AqiStat;
import com.ess.models.Statistic;
import com.ess.services.StatService;
import com.ess.utils.GlobleData;
import com.ess.utils.fxmlLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * StatisticsController类负责空气质量统计信息的可视化展示
 * 实现Initializable接口以支持FXML初始化
 */
public class StatisticsController implements Initializable {

    @FXML private BarChart<String, Number> regionalAqiChart;
    @FXML private PieChart aqiLevelPieChart;
    @FXML private TableView<AqiStat> dataTable;

    /**
     * 加载统计数据
     * 模拟数据服务 - 实际项目中应替换为DAO实现
     * @return 包含所有统计信息的列表
     */
    private List<Statistic> loadStatistics() {
        List<Statistic> stats = new ArrayList<>( new StatService().getAllStatistics());

        return stats;
    }

    /**
     * 初始化方法，在控制器类被实例化时自动调用
     * 用于初始化所有图表和表格组件
     * @param location  FXML文件的位置
     * @param resources 资源包
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRegionalAqiChart();
        initAqiLevelPieChart();
        initDataTable();
    }

    /**
     * 初始化区域AQI柱状图
     * 创建并配置平均AQI的可视化展示，包括颜色编码
     */
    private void initRegionalAqiChart() {
        regionalAqiChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("平均AQI");

        List<Statistic> stats = loadStatistics();
        for (Statistic stat : stats) {
            series.getData().add(new XYChart.Data<>(
                    stat.getLocation(),
                    stat.getAvg_aqi()
            ));
        }

        regionalAqiChart.getData().add(series);

        /* 设置柱子颜色：
           根据AQI值范围设置不同颜色：
           优(绿色) ≤50
           良(黄色) ≤100
           轻度(橙色) ≤150
           中度(红色) ≤200
           重度(紫色) ≤300
           严重(褐红色) >300 */
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    double aqi = data.getYValue().doubleValue();
                    if (aqi <= 50) newNode.setStyle("-fx-bar-fill: #00E400;"); // 优-绿色
                    else if (aqi <= 100) newNode.setStyle("-fx-bar-fill: #FFFF00;"); // 良-黄色
                    else if (aqi <= 150) newNode.setStyle("-fx-bar-fill: #FF7E00;"); // 轻度-橙色
                    else if (aqi <= 200) newNode.setStyle("-fx-bar-fill: #FF0000;"); // 中度-红色
                    else if (aqi <= 300) newNode.setStyle("-fx-bar-fill: #99004C;"); // 重度-紫色
                    else newNode.setStyle("-fx-bar-fill: #7E0023;"); // 严重-褐红色
                }
            });
        }
    }

    /**
     * 初始化AQI等级饼图
     * 创建并配置各AQI等级的分布情况
     */
    private void initAqiLevelPieChart() {
        Map<String, Integer> levelCount = new HashMap<>();
        levelCount.put("优", 0);
        levelCount.put("良", 0);
        levelCount.put("轻度污染", 0);
        levelCount.put("中度污染", 0);
        levelCount.put("重度污染", 0);
        levelCount.put("严重污染", 0);

        List<Statistic> stats = loadStatistics();
        int total = stats.size();

        for (Statistic stat : stats) {
            double aqi = stat.getAvg_aqi();
            if (aqi <= 50) levelCount.put("优", levelCount.get("优") + 1);
            else if (aqi <= 100) levelCount.put("良", levelCount.get("良") + 1);
            else if (aqi <= 150) levelCount.put("轻度污染", levelCount.get("轻度污染") + 1);
            else if (aqi <= 200) levelCount.put("中度污染", levelCount.get("中度污染") + 1);
            else if (aqi <= 300) levelCount.put("重度污染", levelCount.get("重度污染") + 1);
            else levelCount.put("严重污染", levelCount.get("严重污染") + 1);
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : levelCount.entrySet()) {
            double percentage = (double) entry.getValue() / total * 100;
            pieData.add(new PieChart.Data(
                    entry.getKey() + ": " + String.format("%.1f%%", percentage),
                    entry.getValue()
            ));
        }

        aqiLevelPieChart.setData(pieData);

        /* 计算每个AQI等级的百分比并创建饼图数据集 */
        setPieChartColors();
    }

    /**
     * 设置饼图的颜色样式
     * 为每个AQI等级扇形区域分配对应的颜色
     */
    private void setPieChartColors() {
        aqiLevelPieChart.getData().forEach(data -> {
            String name = data.getName();
            if (name.contains("优")) data.getNode().setStyle("-fx-pie-color: #00E400;");
            else if (name.contains("良")) data.getNode().setStyle("-fx-pie-color: #FFFF00;");
            else if (name.contains("轻度")) data.getNode().setStyle("-fx-pie-color: #FF7E00;");
            else if (name.contains("中度")) data.getNode().setStyle("-fx-pie-color: #FF0000;");
            else if (name.contains("重度")) data.getNode().setStyle("-fx-pie-color: #99004C;");
            else if (name.contains("严重")) data.getNode().setStyle("-fx-pie-color: #7E0023;");
        });
    }

    /**
     * 初始化数据表格
     * 创建并配置包含详细统计信息的表格视图
     */
    private void initDataTable() {
        ObservableList<AqiStat> tableData = FXCollections.observableArrayList();

        List<Statistic> stats = loadStatistics();
        for (Statistic stat : stats) {
            tableData.add(new AqiStat(
                    stat.getLocation(),
                    stat.getAvg_aqi(),
                    stat.getTotal_reports()
            ));
        }

        dataTable.setItems(tableData);
    }

    /**
     * 切换到公共仪表盘界面
     * 处理用户界面导航到公共仪表盘的操作
     */
    @FXML
    private void switchToPublic(){
        GlobleData.stage.setTitle(GlobleData.PublicTitle);
        fxmlLoadUtil.loadFXML(GlobleData.stage,"/fxml/views/Public/dashboard.fxml", null);
    }
}