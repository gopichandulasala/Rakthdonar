package com.example.rakthdonar;

public class User {
    String bloodgroup,age,gender,weight,recentlydonated,mail;
    public User(){

    }

    public User(String age,String bloodgroup,String gender,String recentlydonated,String weight,String mail){
        this.bloodgroup=bloodgroup;
        this.age=age;
        this.gender=gender;
        this.weight=weight;
        this.recentlydonated=recentlydonated;
        this.mail=mail;
    }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }

    public String getWeight() {
        return weight;
    }

    public String getRecentlydonated() {
        return recentlydonated;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRecentlydonated(String recentlydonated) {
        this.recentlydonated = recentlydonated;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
