package com.example.gerrys.canteen.Model;

/**
 * Created by Cj_2 on 2017-11-03.
 */

public class User {
    private String name;
    private String Password;
    private String Phone;
    private String Status;

    public User(){

    }

    public User(String name, String password,String status) {
        this.name = name;
        Password = password;
        Status = status;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public String getUserStatus() {
        return Status;
    }

    public void setUserStatus(String status) {
        Status = status;
    }
}
