package com.ess.utils;

import com.ess.models.User;
import com.ess.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class LoadUserTableUtil {
    /**
     * 加载用户表格
     * @param userTable
     * @param userIdColumn
     * @param usernameColumn
     * @param passwordColumn
     * @param roleColumn
     * @param fullNameColumn
     * @param emailColumn
     * @param phoneColumn
     * @param createdAtColumn
     * @param locationColumn
     */
    public static void loadUserTable(

            TableView<User> userTable,


            TableColumn<User, Integer> userIdColumn,


            TableColumn<User, String> usernameColumn,


            TableColumn<User, String> passwordColumn,


            TableColumn<User, String> roleColumn,


            TableColumn<User, String> fullNameColumn,


            TableColumn<User, String> emailColumn,


            TableColumn<User, String> phoneColumn,


            TableColumn<User, LocalDate> createdAtColumn,

            TableColumn<User, String> locationColumn



    ){   userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("placeOfinspector"));
        userTable.setItems(getUserData());


    }
    private static ObservableList<User> getUserData(){
      ObservableList<User> userData = FXCollections.observableArrayList(
                new UserService().getAllUsers()
        );

      return userData;

    }
}
