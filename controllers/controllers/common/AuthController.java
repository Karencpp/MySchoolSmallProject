package com.ess.controllers.common;

import com.ess.models.User;
import com.ess.services.AuthService;
import com.ess.utils.AlertUtil;
import com.ess.utils.GlobleData;
import com.ess.utils.fxmlLoadUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * AuthController类处理用户认证相关的操作，包括登录和注册功能。
 */
public class AuthController {



    public AuthController(){

    }
    
    /**
     * 初始化控制器时加载的FXML文件路径。
     * 
     * @FXML 注解表示该方法由JavaFX框架管理
     */



    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField locationField;

    /**
     * 认证服务实例，用于处理实际的认证逻辑。
     */
    private AuthService authService = new AuthService();
    
    /**
     * 获取表单中的用户名输入。
     * 
     * @return 用户名字符串
     */
    public String getUsername() {
        return usernameField.getText();
    }
    
    /**
     * 获取表单中的密码输入。
     * 
     * @return 密码字符串
     */
    public String getPassword() {
        return passwordField.getText();
    }

    /**
     * 处理用户登录请求。
     * 
     * 从表单字段获取用户名和密码，验证输入是否为空。
     * 调用认证服务进行用户验证。
     * 如果验证成功，将用户对象存储在全局数据中，并根据用户角色重定向到仪表盘。
     * 如果验证失败，显示错误消息。
     * 
     * @throws IOException 如果加载仪表盘FXML文件时发生I/O错误
     */
    @FXML
    private void handleLogin() throws IOException {
        // 获取用户名和密码
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 验证输入是否为空
        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showError("错误", "用户名和密码不能为空");
            return;
        }

        // 执行认证
        User user = authService.authenticate(username, password);
        GlobleData.currentUser = user; //绑定当前用户
        
        // 处理认证结果
        if (user != null) {
            System.out.println("登录成功");
            redirectToDashboard(user);
        } else {
            AlertUtil.showError("错误", "用户名或密码不正确");
        }
    }
    
    /**
     * 获取当前登录用户的User对象。
     * 
     * @return 当前登录用户的User对象，如果未登录则返回null
     */
    public User getCurrentUser() {
        return GlobleData.currentUser;
    }

    /**
     * 处理用户注册请求。
     * 
     * 从表单字段获取注册信息。
     * 验证输入数据的完整性和有效性。
     * 创建用户对象并设置属性。
     * 调用注册服务创建用户。
     * 根据注册结果显示成功或错误消息。
     * 
     * @throws IOException 如果加载登录FXML文件时发生I/O错误
     */
    @FXML
    private void handleRegister() throws IOException {
        // 获取表单数据
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();
        String placeOfInspector = locationField.getText();;
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        // 验证输入
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                role == null || fullName.isEmpty()) {
            AlertUtil.showError("错误", "请填写所有必填字段");
            return;
        }

        if (!password.equals(confirmPassword)) {
            AlertUtil.showError("错误", "两次输入的密码不一致");
            return;
        }

        if (password.length() < 6) {
            AlertUtil.showError("错误", "密码长度不能少于6位");
            return;
        }
        
        // 检查用户名是否已存在
        if (authService.isUsernameExists(username)) {
            AlertUtil.showError("错误", "用户名已被使用");
            return;
        }

        // 创建用户对象
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(role);
        if(!locationField.getText().isEmpty()){
            newUser.setPlaceOfinspector(placeOfInspector);
        }
        newUser.setFullName(fullName);
        newUser.setEmail(email.isEmpty() ? null : email);
        newUser.setPhone(phone.isEmpty() ? null : phone);

        // 调用服务注册用户
        boolean success = authService.register(newUser);

        if (success) {
            AlertUtil.showInfo("成功", "注册成功，请登录");
            switchToLogin();
        } else {
            AlertUtil.showError("错误", "注册失败，用户名可能已被使用");
            return;
        }
    }
    
    /**
     * 检查用户名是否已存在。
     * 
     * @param username 要检查的用户名
     * @return 如果用户名已存在返回true，否则返回false
     */
    public boolean isUsernameExists(String username) {
        return authService.isUsernameExists(username);
    }

    /**
     * 根据用户角色重定向到相应的仪表盘界面。
     * 
     * @param user 已经登录的用户对象
     * @throws IllegalArgumentException 如果遇到未知的用户角色
     */
    private void redirectToDashboard(User user)  {

        switch (user.getRole()) {
            case "PUBLIC":
                fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/Public/dashboard.fxml", null);
                GlobleData.stage.setTitle(GlobleData.PublicTitle);
                break;
            case "INSPECTOR":
                 fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/inspector/dashboard.fxml", null);
                 GlobleData.stage.setTitle(GlobleData.InspectorTitle);
                break;
            case "ADMIN":
                   fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/admin/new_dashboard.fxml", null);
                      GlobleData.stage.setTitle(GlobleData.AdminTitle);
                break;
            case "DECISION_MAKER":
                     fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/decision/dashboard.fxml", null);
                      GlobleData.stage.setTitle(GlobleData.DecisionTitle);
                break;
            default:
                throw new IllegalArgumentException("未知用户角色: " + user.getRole());
        }
    }
    
    /**
     * 获取用户当前的角色。
     * 
     * @return 用户当前的角色字符串
     */
    public String getCurrentUserRole() {
        return GlobleData.currentUser != null ? GlobleData.currentUser.getRole() : null;
    }

    @FXML
    private void switchToRegister() {
        fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/common/register.fxml", null);
    }
    
    /**
     * 切换到登录界面。
     * 
     * 加载登录界面的FXML文件并显示在当前舞台上。
     */
    @FXML
    private void switchToLogin()  {
        fxmlLoadUtil.loadFXML(GlobleData.stage, "/fxml/views/common/login.fxml", null);
    }
    
    /**
     * 清除所有表单字段的内容。
     * 
     * 用于在切换界面或提交后重置表单状态。
     */
    public void clearFormFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        fullNameField.clear();
        emailField.clear();
        phoneField.clear();
        locationField.clear();
    }
}