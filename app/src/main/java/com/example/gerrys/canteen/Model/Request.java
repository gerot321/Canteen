package com.example.gerrys.canteen.Model;

import java.util.List;

/**
 * Created by Cj_2 on 2017-11-26.
 */

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private String method;
    private List<Order> product; // list of shoe order


    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> product,String status,String method) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.product = product;
        this.status = status; // default is 0, 0 = Order Placed, 1 = Shipping, 2 = Shipped
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getPaymentMethod() {
        return method;
    }

    public void setPaymentMethod(String method) {
        this.method = method;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getProduct() {
        return product;
    }

    public void setProduct(List<Order> product) {
        this.product = product;
    }
}
