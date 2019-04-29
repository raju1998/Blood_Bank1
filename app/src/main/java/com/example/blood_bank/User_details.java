package com.example.blood_bank;

class User_details {
    private String _name;
    private String _BloodGroup;
    private String _number;
    private  String _location;

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    String get_name() {
        return _name;
    }

    void set_name(String _name) {
        this._name = _name;
    }

    String get_BloodGroup() {
        return _BloodGroup;
    }

    void set_BloodGroup(String _BloodGroup) {
        this._BloodGroup = _BloodGroup;
    }

    String get_number() {
        return _number;
    }

    void set_number(String _number) {
        this._number = _number;
    }
}
