package com.example.passwordmanager;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerUI implements Initializable {

    @FXML
    private ListView<String> accountListView;

    @FXML
    private TextField tfAccountName, tfUsername, tfPassword, tfConfirmPassword;

    @FXML
    private PasswordField pfPassword, pfConfirmPassword;

    @FXML
    private Button btnSubmit, btnAddCancel;

    @FXML
    private ToggleButton btnToggleEdit;

    private ArrayList<String> accountNameList = new ArrayList<String>();

    private ArrayList<Account> list = new ArrayList<Account>();

    private boolean isEditable = true;

    private void getAllAccounts(){
        list.clear();
        list = Main.passwordManager.getAllAccounts(Main.loggedInUser);
        accountNameList.clear();
        for (int i = 0; i < list.size(); i++) {
            accountNameList.add(list.get(i).getAc_name());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFields();
        getAllAccounts();
        try {
            accountListView.getItems().addAll(accountNameList);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        accountListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == MouseButton.PRIMARY){
                    int selectedIndex = accountListView.getSelectionModel().getSelectedIndex();
                    Account selectedAccount = list.get(selectedIndex);
                    isEditable = false;
                    enableFields();
                    toggleEditing();
                    showPass();
                    populateFields(selectedAccount);
                    btnAddCancel.setText("Cancel");
                    btnSubmit.setDisable(true);
                    btnToggleEdit.setVisible(true);
                    btnSubmit.setLayoutX(183);
                    btnSubmit.setPrefWidth(180);
                    btnSubmit.setText("Update");
                }
            }
        });
//        accountListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>(){
//
//            @Override
//            public void onChanged(Change<? extends String> change) {
//                int selectedIndex = accountListView.getSelectionModel().getSelectedIndex();
//                Account selectedAccount = list.get(selectedIndex);
//                isEditable = false;
//                enableFields();
////                tfUsername.setEditable(false);
////                tfAccountName.setEditable(false);
////                tfPassword.setEditable(false);
////                tfConfirmPassword.setEditable(false);
////                pfPassword.setEditable(false);
////                pfConfirmPassword.setEditable(false);
//
////                pfConfirmPassword.setVisible(false);
////                pfPassword.setVisible(false);
////                tfConfirmPassword.setVisible(true);
////                tfPassword.setVisible(true);
//                toggleEditing();
//                showPass();
////                tfAccountName.setText(selectedAccount.getAc_name());
////                tfUsername.setText(selectedAccount.getUsername());
////                pfPassword.setText(selectedAccount.getPassword());
////                pfConfirmPassword.setText(selectedAccount.getPassword());
////                tfPassword.setText(selectedAccount.getPassword());
////                tfConfirmPassword.setText(selectedAccount.getPassword());
//                populateFields(selectedAccount);
//                btnAddCancel.setText("Cancel");
//                btnSubmit.setDisable(true);
//            }
//        });
    }

    public void saveAccount(ActionEvent event){
        String accountName, username,password,confirmPassword;
        accountName = tfAccountName.getText().trim();
        username = tfUsername.getText().trim();
        password = pfPassword.getText();
        confirmPassword = pfConfirmPassword.getText();

        if(btnSubmit.getText().equals("Save")){
            if(accountName.length() > 0 && username.length() > 0 && password.trim().length() > 0 && confirmPassword.trim().length() > 0){
                if(password.equals(confirmPassword)){
                    Account account = new Account(Main.loggedInUser.getUserid(),accountName,username,pfPassword.getText());
                    Account result = Main.passwordManager.addAccount(account);
                    if(result != null){
                        clearFields();
                        accountListView.getItems().removeAll(accountNameList);
                        getAllAccounts();
                        accountListView.getItems().addAll(accountNameList);
                        btnAddCancel.setText("Add");
                        disableFields();
                    }
                }else{
                    System.out.println("Both passwords are not same, please try again");
                }
            }
        }else if(btnSubmit.getText().equals("Update") && btnToggleEdit.isSelected()){
            Account account = new Account(Main.loggedInUser.getUserid(),accountName,username,tfPassword.getText());
            updateAccount(account);
        }

    }

    private void updateAccount(Account account){
        Account result = Main.passwordManager.updateAccount(account);
        if(result != null){
            clearFields();
            accountListView.getItems().removeAll(accountNameList);
            getAllAccounts();
            accountListView.getItems().addAll(accountNameList);
            btnAddCancel.setText("Add");
            disableFields();
            btnToggleEdit.setVisible(false);
            btnToggleEdit.setSelected(false);
            btnSubmit.setLayoutX(46);
            btnSubmit.setPrefWidth(319);
            btnSubmit.setText("Save");
        }
    }

    public void addCancel(ActionEvent event) {
        if(btnAddCancel.getText().equals("Cancel")){
            clearFields();
            btnAddCancel.setText("Add");
            disableFields();
            btnToggleEdit.setVisible(false);
            btnSubmit.setLayoutX(46);
            btnSubmit.setPrefWidth(319);
            btnSubmit.setText("Save");
        }else if(btnAddCancel.getText().equals("Add")){
            isEditable = true;
            toggleEditing();
            hidePass();
            enableFields();
            btnAddCancel.setText("Cancel");
            btnToggleEdit.setSelected(false);
        }
    }

    public void actionEditToggle(ActionEvent event){
        if(btnToggleEdit.isSelected()){
            btnSubmit.setDisable(false);
            isEditable = true;
            toggleEditing();
        }else{
            btnSubmit.setDisable(true);
            isEditable = false;
            toggleEditing();
            tfAccountName.setEditable(false);
        }
    }

    private void toggleEditing(){
        tfUsername.setEditable(isEditable);
        tfAccountName.setEditable(isEditable);
        tfPassword.setEditable(isEditable);
        tfConfirmPassword.setEditable(isEditable);
        pfPassword.setEditable(isEditable);
        pfConfirmPassword.setEditable(isEditable);
    }

    private void showPass(){
        pfConfirmPassword.setVisible(false);
        pfPassword.setVisible(false);
        tfConfirmPassword.setVisible(true);
        tfPassword.setVisible(true);
    }

    private void hidePass(){
        pfConfirmPassword.setVisible(true);
        pfPassword.setVisible(true);
        tfConfirmPassword.setVisible(false);
        tfPassword.setVisible(false);
    }

    private void populateFields(Account selectedAccount){
        tfAccountName.setText(selectedAccount.getAc_name());
        tfUsername.setText(selectedAccount.getUsername());
        pfPassword.setText(selectedAccount.getPassword());
        pfConfirmPassword.setText(selectedAccount.getPassword());
        tfPassword.setText(selectedAccount.getPassword());
        tfConfirmPassword.setText(selectedAccount.getPassword());
    }

    private void clearFields(){
        tfAccountName.setText("");
        tfUsername.setText("");
        pfPassword.setText("");
        pfConfirmPassword.setText("");
        tfPassword.setText("");
        tfConfirmPassword.setText("");
    }

    private void disableFields(){
        tfAccountName.setDisable(true);
        tfUsername.setDisable(true);
        pfPassword.setDisable(true);
        pfConfirmPassword.setDisable(true);
        tfPassword.setDisable(true);
        tfConfirmPassword.setDisable(true);
        btnSubmit.setDisable(true);
    }
    private void enableFields(){
        tfAccountName.setDisable(false);
        tfUsername.setDisable(false);
        pfPassword.setDisable(false);
        pfConfirmPassword.setDisable(false);
        tfPassword.setDisable(false);
        tfConfirmPassword.setDisable(false);
        btnSubmit.setDisable(false);
    }
}
