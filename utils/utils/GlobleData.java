package com.ess.utils;

import com.ess.models.User;
import javafx.stage.Stage;

public class GlobleData {


    public static  Stage stage;

    //存储全局都用得到的数据,定义成静态并用final修饰,避免被修改
    public static final String loginTitle="公众环保监督系统";
    public static final String PublicTitle= "公众环保监督系统-公众监督员端";
    public static final String InspectorTitle= "公众环保监督系统- 网格员端";
    public static final String AdminTitle= "公众环保监督系统- 管理员端";
    public static final String DecisionTitle= "公众环保监督系统- 决策端";

    //存储当前登录的用户
    public static  User currentUser;


}
