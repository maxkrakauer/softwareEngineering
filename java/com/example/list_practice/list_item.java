package com.example.list_practice;

public class list_item {
    String _name,_desc;
    double _price;

    public list_item(String _name, String _desc, double _price) {
        this._name = _name;
        this._desc = _desc;
        this._price = _price;
    }

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

    public double get_price() {
        return _price;
    }

    public void set_price(double _price) {
        this._price = _price;
    }
}
