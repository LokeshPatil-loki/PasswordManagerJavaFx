package com.example.passwordmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public abstract class AuthController {
    @FXML
    protected TextField tfUsername, redirectToRegister;

    @FXML
    protected PasswordField tfPassword;

    @FXML
    protected Button btnSubmit;

    protected String username, password;

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    public abstract void redirect(MouseEvent event);
}
