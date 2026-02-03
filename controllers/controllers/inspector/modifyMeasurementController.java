package com.ess.controllers.inspector;

import com.ess.models.AQIMeasurement;
import com.ess.services.MeasurementService;
import com.ess.utils.AQICalculator;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 网格员要修改自己所提交的测量数据时所对应的页面
 */

public class modifyMeasurementController {
    @FXML
    TextField measurement_idTextField;

    @FXML
    TextField pm25TextField;

    @FXML
    TextField pm10TextField;

    @FXML
    TextField so2TextField;

    @FXML
    TextField no2TextField;

    @FXML
    TextField coTextField;

    @FXML
    TextField o3TextField;

    @FXML
    TextField temperatureTextField;

    @FXML
    TextField humidityTextField;

    @FXML
    TextField windSpeedTextField;
    @FXML
    TextField notesTextField;

   String measurement_id;

    @FXML
    private void initialize() {
        measurement_id = measurement_idTextField.getText();
    }

    /**
     * 加载数据对应测量的数据
     */
    @FXML
    private void LoadData(){

            measurement_id = measurement_idTextField.getText();
            if(checkInputsOfMeasurementID(measurement_id)) {


                Map<String, Object> measurement = new MeasurementService().getMeasurementByMeasurementId(Integer.parseInt(measurement_id));
                pm25TextField.setText(measurement.get("pm25").toString());
                pm10TextField.setText(measurement.get("pm10").toString());
                so2TextField.setText(measurement.get("so2").toString());
                no2TextField.setText(measurement.get("no2").toString());
                coTextField.setText(measurement.get("co").toString());
                o3TextField.setText(measurement.get("o3").toString());
                temperatureTextField.setText(measurement.get("temperature").toString());
                humidityTextField.setText(measurement.get("humidity").toString());
                windSpeedTextField.setText(measurement.get("wind_speed").toString());
                notesTextField.setText(measurement.get("notes").toString());
            }
    }

    /**
     * 确认修改网格员提交的测量数据
     */
    @FXML
    private void confirm(){
        measurement_id = measurement_idTextField.getText();
        if(checkInputsOfMeasurementID(measurement_id)) {

     try{
             int measurement_id = Integer.parseInt(measurement_idTextField.getText());

            double pm25 = Double.parseDouble(pm25TextField.getText());
         double pm10 = Double.parseDouble(pm10TextField.getText());
         double so2 = Double.parseDouble(so2TextField.getText());
         double no2 = Double.parseDouble(no2TextField.getText());
         double co = Double.parseDouble(coTextField.getText());
         double o3 = Double.parseDouble(o3TextField.getText());
         double temperature = Double.parseDouble(temperatureTextField.getText());
         double humidity = Double.parseDouble(humidityTextField.getText());
         double windSpeed = Double.parseDouble(windSpeedTextField.getText());

         int aqi_value= AQICalculator.calculateAQI(pm25, pm10, so2, no2, co, o3);

         String notes = notesTextField.getText();

         //拿到当前对应的公共监督报告ID,否则无法创建新测量数据
         int currentReportId = (Integer) (new MeasurementService().getMeasurementByMeasurementId(measurement_id).get("report_id"));

         //删除旧的测量数据
          new MeasurementService().deleteMeasurementByMeasurementId(measurement_id);

          //创建新的测量数据
         new MeasurementService().createMeasurement(currentReportId, GlobleData.currentUser.getUserId(), aqi_value, pm25, pm10, so2, no2, co, o3, temperature, humidity, windSpeed, notes);

         AlertUtil.showInfo("成功", "AQI检测数据已修改");
         clearTextField();

     }catch (Exception e){
                 AlertUtil.showError("错误", "所有字段必须输入有效的数字");
                 return;
             }
     }
    }
    @FXML
    private void exit(){

       Stage stage= (Stage)humidityTextField.getScene().getWindow();
       stage.close();
    }

    @FXML
    private void delete(){

        if(checkInputsOfMeasurementID(measurement_id)) {


            if (new MeasurementService().deleteMeasurementByMeasurementId(Integer.parseInt(measurement_id))) {
                AlertUtil.showInfo("成功", "删除成功");
            } else {
                AlertUtil.showError("错误", "删除失败,系统这块的功能可能有bug了,请联系开发人员解决");
            }
        }
    }
    private boolean checkInputsOfMeasurementID(String measurement_id){



            if (measurement_id.isEmpty()) {
                AlertUtil.showError("错误", "测量ID不能为空");
                return false;
            }
            //判断是否为数字
            if (!measurement_id.matches("[0-9]+")) {
                AlertUtil.showError("错误", "测量ID只能为数字");
                return  false;
            }
            ArrayList<AQIMeasurement> aqiMeasurements = new ArrayList<>(new MeasurementService().getMeasurementsByInspectorID(GlobleData.currentUser.getUserId()).stream()
                    .filter(m -> m.getMeasurement_id() == Integer.parseInt(measurement_id))
                    .collect(Collectors.toList()));
            if (aqiMeasurements.size() == 0) {
                AlertUtil.showError("错误", "没有此测量记录");
                return  false;
            }
            return true;

    }
//清理所有的TextField
    public void clearTextField() {
        measurement_idTextField.clear();
        pm25TextField.clear();
        pm10TextField.clear();
        so2TextField.clear();
        no2TextField.clear();
        coTextField.clear();
        o3TextField.clear();
        temperatureTextField.clear();
        humidityTextField.clear();
        windSpeedTextField.clear();
        notesTextField.clear();
    }


}
