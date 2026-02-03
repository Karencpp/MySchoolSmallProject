package com.ess.controllers.admin;

import com.ess.models.AQIMeasurement;
import com.ess.models.AirQualityReport;
import com.ess.models.Statistic;
import com.ess.services.MeasurementService;
import com.ess.services.StatService;
import com.ess.utils.AlertUtil;
import com.ess.utils.LoadMeasurementTableUtil;
import com.ess.utils.LoadStatisticTableUtil;
import com.ess.utils.fxmlLoadUtil;
import com.mysql.cj.xdevapi.Table;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AQIController {

    //下面放AQI测量数据表的组件

    @FXML
    TableView<AQIMeasurement> AQITable;
    @FXML   //这个需要独立获取并建立模型
    TableColumn<AQIMeasurement, String>  locationColumn;

    @FXML
    TableColumn<AQIMeasurement,Integer> measurement_id;

    @FXML
    TableColumn<AQIMeasurement,Integer> report_id;

    @FXML
    TableColumn<AQIMeasurement,Integer> inspector_id;

    @FXML
    TableColumn<AQIMeasurement,Integer> aqi_value;

    @FXML
    TableColumn<AQIMeasurement,Double> pm25;

    @FXML
    TableColumn<AQIMeasurement,Double> pm10;

    @FXML
    TableColumn<AQIMeasurement,Double> so2;

    @FXML
    TableColumn<AQIMeasurement,Double> no2;

    @FXML
    TableColumn<AQIMeasurement,Double> co;
    @FXML
    TableColumn<AQIMeasurement,Double> o3;
    @FXML
    TableColumn<AQIMeasurement,Double> temperature;
    @FXML
    TableColumn<AQIMeasurement,Double> humidity;
    @FXML
    TableColumn<AQIMeasurement,Double> windSpeed;
    @FXML
    TableColumn<AQIMeasurement, LocalDateTime> measured_at;
     @FXML
    TableColumn<AQIMeasurement, String> notes;
//下面存放统计数据表的组件
    @FXML
   private   TableView <Statistic>  statisticTable;
    @FXML
  private  TableColumn<Statistic,String> locationOfstatistic;
    @FXML
   private  TableColumn<Statistic,Integer> stat_id;
    @FXML
   private TableColumn<Statistic,String>  period_type;
    @FXML
   private TableColumn<Statistic,LocalDate> period_start;
    @FXML
    private TableColumn<Statistic,LocalDate> period_end;
    @FXML
    private TableColumn<Statistic,Integer> total_reports;
    @FXML
    private TableColumn<Statistic,Integer> completed_reports;
    @FXML
    private  TableColumn<Statistic,Double> avg_aqi;
    @FXML
    private TableColumn<Statistic,Integer> max_aqi;
    @FXML
    private TableColumn<Statistic,Integer> min_aqi;
    @FXML
    private  TableColumn<Statistic,LocalDate> created_at;

    @FXML
    private TextField locationText;


    /**
     * 初始化方法，在控制器类被加载后自动调用
     * 作用：
     * 1. 设置表格列宽自适应
     * 2. 加载测量数据表格
     * 3. 加载统计数据表格
     */
    @FXML
    public void initialize(){

    AQITable.getColumns().forEach(column->
            column.setPrefWidth(Region.USE_COMPUTED_SIZE));
    statisticTable.getColumns().forEach(column->
            column.setPrefWidth(Region.USE_COMPUTED_SIZE));


    LoadMeasurementTableUtil.loadMeasurementTable(
            AQITable,
            locationColumn,
            measurement_id,
            report_id,
            inspector_id,
            aqi_value,
            pm25,
            pm10,
            so2,
            no2,
            co,
            o3,
            temperature,
            humidity,
            windSpeed,
            measured_at,
            notes
    );
    LoadStatisticTableUtil.LoadStatisticTable(
            statisticTable,
            locationOfstatistic,
            stat_id,
            period_type,
            period_start,
            period_end,
            total_reports,
            completed_reports,
            avg_aqi,
            max_aqi,
            min_aqi,
            created_at
    );

}
      
    /**
     * 显示全屏窗口，展示所有测量数据和统计数据
     * 功能：创建新窗口并加载AQI.fxml界面，设置全屏显示
     */
    @FXML
    private void zoomIn(){
          Stage stage = new Stage();
          stage.setTitle("所有的测量数据,和所有的统计数据");
          fxmlLoadUtil.loadFXML(stage,"/fxml/views/admin/AQI.fxml",null);
          stage.setFullScreen(true);
      }
      
    /**
     * 刷新表格数据
     * 功能：
     * 1. 重新加载测量数据表格
     * 2. 重新加载统计数据表格
     * 3. 刷新表格视图
     * 4. 显示刷新成功提示
     */
    @FXML
    private void refresh(){
    LoadMeasurementTableUtil.loadMeasurementTable(
            AQITable,
            locationColumn,
            measurement_id,
            report_id,
            inspector_id,
            aqi_value,
            pm25,
            pm10,
            so2,
            no2,
            co,
            o3,
            temperature,
            humidity,
            windSpeed,
            measured_at,
            notes
    );
    LoadStatisticTableUtil.LoadStatisticTable(
            statisticTable,
            locationOfstatistic,
            stat_id,
            period_type,
            period_start,
            period_end,
            total_reports,
            completed_reports,
            avg_aqi,
            max_aqi,
            min_aqi,
            created_at
    );
          AQITable.refresh();
          statisticTable.refresh();
          AlertUtil.showInfo("提示","刷新成功");
      }
    
    /**
     * 处理提交按钮点击事件，创建统计数据
     * 功能：
     * 1. 获取输入地点
     * 2. 查询该地点的所有测量数据
     * 3. 计算平均AQI值和总报告数
     * 4. 创建统计数据
     * 5. 显示操作结果提示
     */
    @FXML
    private void handleSubmit(){
            String location = locationText.getText();
            if(location.isEmpty()){
                AlertUtil.showError("错误","请输入地点");
                return ;
            }
          List<AQIMeasurement> measurements = new ArrayList<>(new MeasurementService().getAllMeasurementsBylocation( location));
            if(measurements.isEmpty()){
                AlertUtil.showError("错误","没有该地点的测量数据");
                return ;
            }
            Double avgAqi = measurements.stream().mapToInt(AQIMeasurement::getAqi_value).average().orElse(0.0);
            int totalReports = measurements.size();

            boolean success= new StatService().createStat(totalReports,avgAqi,location);
            if(success){
                AlertUtil.showInfo("提示","添加统计数据成功");

            }else{
                AlertUtil.showError("错误","添加统计数据失败");
            }

      }

}
