package com.example.kwokszeyan.callrestful;

public class friend {

    private String name;
    private String gender;
    private Integer friendId;

    public friend(Integer friendId,String name,  String gender) {
        this.friendId = friendId;
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }



}
