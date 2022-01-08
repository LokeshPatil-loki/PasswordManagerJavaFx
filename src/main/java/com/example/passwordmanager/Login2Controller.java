package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login2Controller {

    @FXML
    private TextField tfUsername, redirectToRegister;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button btnSubmit;

    private String username, password;

    public void login(ActionEvent event){
        username = tfUsername.getText().trim();
        password = tfPassword.getText().trim();
        System.out.println(username);
        System.out.println(password);

        if(username.length() !=0 && password.length() != 0){
            User user = Main.passwordManager.login(username,password);
            if(user != null){
                Main.loggedInUser = user;
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Login");
                alert.setHeaderText("Login in successfully");
                alert.show();
            }else {
                Main.loggedInUser = null;
                Main.ErrorAlert("Login","Incorrect Username / Password");
            }
        }

    }

    public void redirect(){
        System.out.println("Redirecting to registration page");
    }
}
