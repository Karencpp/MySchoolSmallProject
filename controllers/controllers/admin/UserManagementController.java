package com.ess.controllers.admin;

import com.ess.models.User;
import com.ess.services.UserService;
import com.ess.utils.AlertUtil;
import com.ess.utils.LoadUserTableUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class UserManagementController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> fullNameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, LocalDate> createdAtColumn;
    @FXML
    private TableColumn<User, String> locationColumn;

    // 从数据库获取数据的方法
    private ObservableList<User> userData = FXCollections.observableArrayList(
            new UserService().getAllUsers()
    );

    /**
     * 初始化方法，用于加载用户表格数据
     */
    public void initialize() {
        // 初始化表格列
        LoadUserTableUtil.loadUserTable(

                userTable,
                userIdColumn,
                usernameColumn,
                passwordColumn,
                roleColumn,
                fullNameColumn,
                emailColumn,
                phoneColumn,
                createdAtColumn,
                locationColumn
        );
    }
    /**
     * 处理刷新按钮点击事件
     * @param event 触发此方法的动作事件
     */
    @FXML
    public void handleRefresh(ActionEvent event){
        LoadUserTableUtil.loadUserTable(
                userTable,
                userIdColumn,
                usernameColumn,
                passwordColumn,
                roleColumn,
                fullNameColumn,
                emailColumn,
                phoneColumn,
                createdAtColumn,
                locationColumn
        );
        userTable.refresh();
        AlertUtil.showInfo("刷新","刷新成功!");
    }
    /**
     * 处理添加用户按钮点击事件
     * @param event 触发此方法的动作事件
     */
    @FXML
    public void handleAddUser(ActionEvent event){
        AlertUtil.showInfo("添加用户","尚未开发,敬请期待!");
    }
    /**
     * 处理修改用户信息按钮点击事件
     * @param event 触发此方法的动作事件
     */
    @FXML
    public void ModifyUserInformation(ActionEvent event){
        AlertUtil.showInfo("修改用户信息","尚未开发,敬请期待!");
    }
}
