package com.example.list_practice;

public class Item {
    String _id, _name, _desc;
    double _price;

    public Item(String _id, String _name, String _desc, double _price) {
        this._id = _id;
        this._name = _name;
        this._desc = _desc;
        this._price = _price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
