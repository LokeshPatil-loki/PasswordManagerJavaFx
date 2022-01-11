package com.example.passwordmanager;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.*;

public class ManagerUI implements Initializable {

    @FXML
    private ListView<String> accountListView;

    @FXML
    private TextField tfAccountName, tfUsername, tfPassword, tfConfirmPassword, tfSearch;

    @FXML
    private PasswordField pfPassword, pfConfirmPassword;

    @FXML
    private Button btnSubmit, btnAddCancel,btnDelete;

    @FXML
    private ToggleButton btnToggleEdit;

    @FXML
    private Pane backgroundPane;

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
                int selectedIndex = accountListView.getSelectionModel().getSelectedIndex();
                if(mouseEvent.getButton() == MouseButton.PRIMARY){
                    Account selectedAccount = list.get(selectedIndex);
                    isEditable = false;
                    enableFields();
                    toggleEditing();
                    showPass();
                    populateFields(selectedAccount);
                    btnAddCancel.setText("Cancel");
                    btnSubmit.setDisable(true);
                    btnToggleEdit.setVisible(true);
                    btnDelete.setVisible(true);
                    backgroundPane.setVisible(true);
                    btnSubmit.setLayoutX(235);
                    btnSubmit.setPrefWidth(128);
                    btnSubmit.setText("Update");
                }else if(mouseEvent.getButton() == MouseButton.SECONDARY){
//                    accountListView.setOnConte
//                    System.out.println(accountNameList.get(selectedIndex));
                }
            }
        });
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
                        // Update ListView
//                        accountListView.getItems().removeAll(accountNameList);
//                        getAllAccounts();
//                        accountListView.getItems().addAll(accountNameList);
                        updateListView();
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

    public void updateAccount(Account account){
        Account result = Main.passwordManager.updateAccount(account);
        if(result != null){
            clearFields();
            updateListView();
            btnAddCancel.setText("Add");
            disableFields();
            btnToggleEdit.setVisible(false);
            btnDelete.setVisible(false);
            backgroundPane.setVisible(false);
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
            btnDelete.setVisible(false);
            backgroundPane.setVisible(false);

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

    private void updateListView(){
        accountListView.getItems().removeAll(accountNameList);
        getAllAccounts();
        accountListView.getItems().addAll(accountNameList);
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

    public void search(String searchTerm) {
        if(searchTerm.trim().length() == 0){
            System.out.println("Empty");
            updateListView();
        }else{
            ArrayList<Account> searchAccountList = Main.passwordManager.searchAccounts(Main.loggedInUser,searchTerm);
            if(searchAccountList.size() == 0){
                System.out.println("size 0");
                Main.ErrorAlert("Search","No result found");
                tfSearch.setText("");
                updateListView();
            }else{
                System.out.println("NotEmpty");
                accountListView.getItems().removeAll(accountNameList);
                list.clear();
                list = searchAccountList;
                accountNameList.clear();
                for (int i = 0; i < list.size(); i++) {
                    accountNameList.add(list.get(i).getAc_name());
                }
                accountListView.getItems().addAll(accountNameList);
            }

        }
    }

    public void searchBox(KeyEvent keyEvent) {
        String searchTerm = "";
        switch (keyEvent.getCode()){
            case ESCAPE:
                if(tfSearch.getText().length() != 0){
                    tfSearch.setText("");
                    updateListView();
                }
                break;
            case BACK_SPACE:
                if(tfPassword.getText().length() != 0){
                    searchTerm = tfSearch.getText();
                    search(searchTerm);
                }else{
                    updateListView();
                }
                break;
            default:
                searchTerm = tfSearch.getText()+keyEvent.getText();
                search(searchTerm);
        }
    }

    public void deleteAccount(ActionEvent event) {
        Account account = new Account(Main.loggedInUser.getUserid(),tfAccountName.getText(),tfUsername.getText(),tfPassword.getText());
        try {
            Account result = Main.passwordManager.deleteAccount(account);
            if(result != null){
                clearFields();
                updateListView();
                btnAddCancel.setText("Add");
                disableFields();
                btnToggleEdit.setVisible(false);
                btnDelete.setVisible(false);
                backgroundPane.setVisible(false);
                btnToggleEdit.setSelected(false);
                btnSubmit.setLayoutX(46);
                btnSubmit.setPrefWidth(319);
                btnSubmit.setText("Save");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
