package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisterController extends AuthController{

    public void register(ActionEvent event){
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText();

        if(username.length() > 0 && password.trim().length() > 0){
            User user = Main.passwordManager.register(username,password);
            if(user != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("User " + username + " is registered successfully");
                alert.setContentText("Now try to login with your username and password");
                alert.show();
            }
        }
    }

    @Override
    public void redirect(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            scene = new Scene(root);
            scene.getStylesheets().add("stylesheet.css");
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
