package com.example.passwordmanager;

import java.sql.*;
import java.util.ArrayList;

public class PasswordManager {
    private final String UNAME,PASS,HOST,DATABASE,URL;
    private final String USER_UID = "userid", USER_UNAME  = "username", USER_PASS = "password";
    private Connection con;
    private String query;

    // Constructor which initializes Final members(UNAME,PASS,HOST,DATABASE) and Initializes Mysql Connection object named con.
    public PasswordManager(String UNAME, String PASS, String HOST, String DATABASE) throws SQLException {
        this.UNAME = UNAME;
        this.PASS = PASS;
        this.HOST = HOST;
        this.DATABASE = DATABASE;
        this.URL = "jdbc:mysql://"+this.HOST+"/"+DATABASE;
        con = DriverManager.getConnection(URL,UNAME,PASS);
        System.out.println("Connection created");
    }

    public User register(String username, String password){
        query = "Insert into User(username,password)values(?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,username);
            pst.setString(2,password);
            if(pst.executeUpdate() > 0){
//                System.out.println("greter than 0");
                User user = getUserData(username,password);
                if(user != null){
                    System.out.println("Not null");
                    return user;
                }
            }else{
                System.out.println("Less than 0=0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            Main.ErrorAlert("Registration",e.getMessage());
        }
        return null;
    }

    public User login(String username, String password){
        return getUserData(username,password);
    }

    private User getUserData(String username, String password){
        String query = "select * from User where username = ? and password = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet rs = pst.executeQuery();
            boolean bool = rs.next();
            System.out.println(rs == null);
            if(bool){
                User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account addAccount(Account account){
        String query = "Insert into Password(userid,acc_name,username,password) values(?,?,?,?)";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,account.getUserid());
            pst.setString(2,account.getAc_name());
            pst.setString(3,account.getUsername());
            pst.setString(4,EncryptDecrypt.encrypt(account.getPassword()));
            int rowAffected = pst.executeUpdate();
            if(rowAffected > 0 ){
                System.out.println("Account added to password manager successfully!");
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            Main.ErrorAlert("Saving Account",e.getMessage());
        }
        return null;
    }

    public Account updateAccount(Account account){
        String query = "Update Password set username = ?, password = ? where userid = ? and acc_name = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1,account.getUsername());
            pst.setString(2,EncryptDecrypt.encrypt(account.getPassword()));
            pst.setInt(3,account.getUserid());
            pst.setString(4,account.getAc_name());
            int rowAffected = pst.executeUpdate();
            if(rowAffected > 0 ){
                System.out.println("Account added to password manager successfully!");
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Account deleteAccount(Account account) throws SQLException {
        String query = "Delete from Password where userid = " + account.getUserid() + " and acc_name = '" + account.getAc_name() + "'";
        Statement statement = con.createStatement();
        if(statement.executeUpdate(query) > 0){
            return account;
        }
        return null;
    }

    public ArrayList<Account> getAllAccounts(User user){
        ArrayList<Account> accountArrayList = new ArrayList<Account>();
        try {
            Statement statement = con.createStatement();
            String query = "Select * from Password where userid = " + user.getUserid() + " order by id desc";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Account account = new Account(
                        rs.getInt(1),rs.getString(2),
                        rs.getString(3),EncryptDecrypt.decrypt(rs.getString(4)));
                accountArrayList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountArrayList;
    }

    public ArrayList<Account> searchAccounts(User user, String searchTerm){
        ArrayList<Account> accountArrayList = new ArrayList<Account>();
        try {
            Statement statement = con.createStatement();
            String query = "Select * from Password where userid = " + user.getUserid() + " and acc_name like '%" + searchTerm.trim()  +  "%' order by id desc";
            System.out.println(query);
//            String query = "Select * from Password where userid = " + user.getUserid() + " order by id desc";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                Account account = new Account(
                        rs.getInt(1),rs.getString(2),
                        rs.getString(3),EncryptDecrypt.decrypt(rs.getString(4)));
                System.out.println(account.getAc_name());
                accountArrayList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountArrayList;
    }

}
