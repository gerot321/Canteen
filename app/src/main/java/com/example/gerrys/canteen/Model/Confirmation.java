package com.example.gerrys.canteen.Model;

public class Confirmation {
    private String phone;
    private String name;
    private String status;
    private String image;
    private String id;

    public Confirmation() {
    }

    public Confirmation(String id,String phone, String name, String status,String image) {
        this.phone = phone;
        this.name = name;
        this.status = status;
        this.image =  image;
        this.id =  id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
