package com.avijitbabu.dummy_mobile_app.Model;


public class Product {

    private String product_name;
    private String product_id;
    private String sku;
    private String stock;
    private Integer price;
    private String product_image;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Product(String product_id, String product_name,  String sku, String stock, Integer price, String product_image) {
        this.product_name = product_name;
        this.product_id = product_id;
        this.sku = sku;
        this.stock = stock;
        this.price = price;
        this.product_image = product_image;
    }

//    public Product(String product_name, String sku, String stock, Integer price, String product_image) {
//        this.product_name = product_name;
//        this.sku = sku;
//        this.stock = stock;
//        this.price = price;
//
//        this.product_image = product_image;
//
//    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
