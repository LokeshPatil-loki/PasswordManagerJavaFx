package com.example.passwordmanager;

public class Account {
    private int userid;
    private String ac_name;
    private String username;
    private String password;

    public Account(int userid, String ac_name, String username, String password) {
        this.userid = userid;
        this.ac_name = ac_name;
        this.username = username;
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAc_name() {
        return ac_name;
    }

    public void setAc_name(String ac_name) {
        this.ac_name = ac_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] toStringArray(){
        String strArr[] =  new String[4];
        strArr[0] = this.userid+"";
        strArr[1] = this.ac_name;
        strArr[2] = this.username;
        strArr[3] = this.password;
        return  strArr;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userid=" + userid +
                ", ac_name='" + ac_name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
