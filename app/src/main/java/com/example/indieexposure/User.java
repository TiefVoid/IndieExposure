package com.example.indieexposure;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    String user;
    String profile_picture;
    String email;
    String key;
    String bio;
    String pseud;
    long last_login;

    public User(){

    }

    public User(String user, String profile_picture, String email, String key, String bio, String pseud, long last_login) {
        this.user = user;
        this.profile_picture = profile_picture;
        this.email = email;
        this.key = key;
        this.bio = bio;
        this.pseud = pseud;
        this.last_login = last_login;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPseud() {
        return pseud;
    }

    public void setPseud(String pseud) {
        this.pseud = pseud;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getLast_login() {
        return last_login;
    }

    public void setLast_login(long last_login) {
        this.last_login = last_login;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("profile_picture", profile_picture);
        result.put("user", user);
        result.put("email", email);
        result.put("last_login", last_login);

        return result;
    }
}
