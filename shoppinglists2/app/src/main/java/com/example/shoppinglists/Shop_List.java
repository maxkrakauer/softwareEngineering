package com.example.shoppinglists;

import java.util.ArrayList;
import java.util.Date;

public class Shop_List {
    String _id, _name;
    Date _date_added;
    ArrayList<Item>_items;

    public Shop_List(String _id, String _name, Date _date_added, ArrayList<Item> _items) {
        this._id = _id;
        this._name = _name;
        this._date_added = _date_added;
        this._items = _items;
    }

    public Shop_List(String _id, String _name) {
        this._id = _id;
        this._name = _name;
        _date_added = new Date();
        _items = new ArrayList<Item>();
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

    public Date get_date_added() {
        return _date_added;
    }

    public void set_date_added(Date _date_added) {
        this._date_added = _date_added;
    }

    public ArrayList<Item> get_items() {
        return _items;
    }

    public void set_items(ArrayList<Item> _items) {
        this._items = _items;
    }
}
