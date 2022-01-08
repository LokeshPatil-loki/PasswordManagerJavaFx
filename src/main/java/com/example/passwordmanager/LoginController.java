package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController extends AuthController{

    public void login(ActionEvent event){
        username = tfUsername.getText().trim();
        password = tfPassword.getText();
        System.out.println(username);
        System.out.println(password);

        if(username.length() !=0 && password.trim().length() != 0){
            User user = Main.passwordManager.login(username,password);
            if(user != null){
                Main.loggedInUser = user;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login");
                alert.setHeaderText("Login in successfully");
                alert.show();
            }else {
                Main.loggedInUser = null;
                Main.ErrorAlert("Login","Incorrect Username / Password");
            }
        }

    }

    public void redirect(MouseEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            scene = new Scene(root);
            scene.getStylesheets().add("stylesheet.css");
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Main.ErrorAlert("Redirection",e.getMessage());
        }
    }

}
