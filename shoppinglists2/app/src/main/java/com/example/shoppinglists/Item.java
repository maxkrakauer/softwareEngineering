package com.example.shoppinglists;

public class Item {
    String  _name, _desc;
    double _price;
    int _amount;

    public Item(String _name, String _desc, double _price, int _amount) {
        this._name = _name;
        this._desc = _desc;
        this._price = _price;
        this._amount = _amount;
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

    public int get_amount() {
        return _amount;
    }

    public void set_amount(int _amount) {
        this._amount = _amount;
    }
}
