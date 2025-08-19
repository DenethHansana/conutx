package com.s23010403.conutx;

public class UserModel {
    private String name, email, phone;

    public UserModel(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
