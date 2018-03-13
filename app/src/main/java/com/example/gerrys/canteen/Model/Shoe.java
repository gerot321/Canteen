package com.example.gerrys.canteen.Model;

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class Shoe {
    private String Name, Image, Description, Price, Stock, MerchantId;

    public Shoe(){

    }

    public Shoe(String name, String image, String description, String price, String stock, String merchantId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Stock = stock;
        MerchantId = merchantId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }
}
