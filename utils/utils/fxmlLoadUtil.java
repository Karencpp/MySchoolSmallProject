package com.ess.utils;

import com.ess.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class fxmlLoadUtil {
    /**
     * 加载FXML文件
     * @param stage
     * @param fxml
     * @param scene
     */
    public static void loadFXML(Stage stage, String fxml, Scene scene) {
         if(fxml.equals("/fxml/views/common/login.fxml")) {
             stage.setTitle(GlobleData.loginTitle);
         }
         else if(fxml.equals("/fxml/views/common/register.fxml")){
             stage.setTitle(GlobleData.loginTitle);
         }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLoadUtil.class.getResource(fxml));

        try {
            Parent root = fxmlLoader.load();
            scene=new Scene(root);
            scene.getStylesheets().add(fxmlLoadUtil.class.getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);

            stage.show();

        } catch (
                IOException e) {

            System.out.println("加载文件出错啦!");
            e.printStackTrace();
        }


    }

}
