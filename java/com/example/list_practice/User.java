package com.example.list_practice;

public class User {
    String _email, _password, _name;

    public User(String _email, String _password, String _name) {
        this._email = _email;
        this._password = _password;
        this._name = _name;
    }

    public String get_email() {
        return _email;
    }

    public String get_password() {
        return _password;
    }

    public String get_name() {
        return _name;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
