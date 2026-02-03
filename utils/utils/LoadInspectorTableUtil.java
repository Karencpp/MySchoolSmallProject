package com.ess.utils;

import com.ess.models.AirQualityReport;
import com.ess.models.User;
import com.ess.services.ReportService;
import com.ess.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;


public class LoadInspectorTableUtil {

    //拿到角色为inspector的用户列表


    public static void loadInspectorTable(
            TableView<User> inspectorTable,
            TableColumn<User, Integer> inspector_idOfInspectorTableColumn,
            TableColumn<User, String> inspector_locationColumn

    ){   inspectorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inspectorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        inspector_idOfInspectorTableColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        inspector_locationColumn.setCellValueFactory(new PropertyValueFactory<>("placeOfinspector"));

        inspectorTable.setItems(getInspectorData());
        inspectorTable.setEditable(true);

    }
//完美封装
   private static ObservableList<User> getInspectorData(){
        List<User> inspectorList= new UserService().getAllUsers().stream()
               .filter(user -> user.getRole().equals("网格员"))
               .toList();
       //拿到集合的stream流

         ObservableList<User> UserData = FXCollections.observableArrayList(
               inspectorList);
         return UserData;
   }
}
