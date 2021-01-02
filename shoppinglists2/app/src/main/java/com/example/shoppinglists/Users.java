package com.example.shoppinglists;

public class Users {

    private String Name;
    private String Price;

    public Users(){
    }
    public Users(String name, String price){
        this.Name = name;
        this.Price = price;
    }

    public String getName() {
        return Name;
    }
    public String getPrice() {
        return Price;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public String toString() {
        return Name;
    }
}
