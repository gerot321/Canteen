package com.example.gerrys.canteen.Model;

/**
 * Created by Cj_2 on 2017-11-26.
 */

public class Order {
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Size;

    public Order() {
    }

    public Order(String productId, String productName, String quantity, String price, String size) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Size = size;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}
