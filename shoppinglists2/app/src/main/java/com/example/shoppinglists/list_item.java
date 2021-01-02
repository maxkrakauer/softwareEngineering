package com.example.shoppinglists;

public class list_item {
    String _name,_desc,_price,_amount;


    public list_item(String _name, String _desc, String _price, String _amount) {
        this._name = _name;
        this._desc = _desc;
        this._price = _price;
        this._amount = _amount;
    }

    public list_item(){}

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }

    public String get_amount() {
        return _amount;
    }

    public void set_amount(String _amount) {
        this._amount = _amount;
    }

    @Override
    public String toString() {
        return _name;
    }


}
