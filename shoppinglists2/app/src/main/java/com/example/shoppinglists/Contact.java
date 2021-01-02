package com.example.shoppinglists;

public class Contact {
    String _name, _id;
    Boolean _following;

    public Contact(String _name, String _id, Boolean _following) {
        this._name = _name;
        this._id = _id;
        this._following = _following;
    }

    public Contact(String _name, String _id) {
        this._name = _name;
        this._id = _id;
    }

    public Boolean get_following() {
        return _following;
    }

    public void set_following(Boolean _following) {
        this._following = _following;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
