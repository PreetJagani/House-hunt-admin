package com.example.househuntadmin;

public class House {

    private String name,address,price,space,phoneNumber,rating,imageUrl,rent,exclusive,des;

    public House()
    {

    }

    public House(String name, String address,String price, String space,String phoneNumber ,String rating,String imageUrl,String rent,String exclusive,String des) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.space = space;
        this.phoneNumber = phoneNumber;
        this.rating = rating;

        this.imageUrl = imageUrl;

        this.exclusive = exclusive;
        this.rent = rent;
        this.des = des;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getSpace() {
        return space;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getExclusive() {
        return exclusive;
    }

    public void setExclusive(String exclusive) {
        this.exclusive = exclusive;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
