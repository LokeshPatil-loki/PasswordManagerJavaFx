package com.example.passwordmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    public static PasswordManager passwordManager;
    public static User loggedInUser;
    public static Stage stage;

    public static void main(String[] args) {
        try {
            passwordManager = new PasswordManager("loki","loki","localhost:3306","PasswordManager");
//            Main.loggedInUser =  passwordManager.login("loki","lokesh");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        launch(args);
    }

    public static void ErrorAlert(String title,String message){
        double x = (double) (stage.getX() + stage.getWidth()) / 2;
        double y= (double) (stage.getY() + stage.getHeight()) / 2;
        System.out.println("x: " + x + ", y: " + y);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setX(x);
        alert.setY(y);
        alert.setTitle(title);
        alert.setHeaderText(title  + " failed");
        alert.setContentText(message);
        alert.show();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("stylesheet.css");
            stage = primaryStage;
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(false);
            primaryStage.centerOnScreen();
            primaryStage.setTitle("Password Manager");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
