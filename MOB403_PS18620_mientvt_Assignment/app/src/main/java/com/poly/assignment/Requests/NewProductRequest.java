package com.poly.assignment.Requests;

public class NewProductRequest {

    private String name, image;
    private Integer quantity, category_id;
    private Float price;

    public NewProductRequest() {
    }

    public NewProductRequest(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    public NewProductRequest(String name, Float price, Integer quantity, String image, Integer category_id) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantiy) {
        this.quantity = quantiy;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
