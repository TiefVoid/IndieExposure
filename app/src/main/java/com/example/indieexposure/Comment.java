package com.example.indieexposure;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String user2;
    private String desc2;
    private String pfp2;
    private String post;
    private String user_key;
    private long fechaHora2;

    public Comment() {

    }

    public Comment(String user2, String desc2, String pfp2, String post, long fechaHora2, String user_key) {
        this.user2 = user2;
        this.desc2 = desc2;
        this.pfp2 = pfp2;
        this.post = post;
        this.user_key = user_key;
        this.fechaHora2 = fechaHora2;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPfp() {
        return pfp2;
    }

    public void setPfp(String pfp) {
        this.pfp2 = pfp;
    }

    public String getUser() {
        return user2;
    }

    public void setUser(String user) {
        this.user2 = user;
    }

    public String getDesc() {
        return desc2;
    }

    public void setDesc(String desc) {
        this.desc2 = desc;
    }

    public long getFechaHora() {
        return fechaHora2;
    }

    public void setFechaHora(long fechaHora) {
        this.fechaHora2 = fechaHora;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pfp", pfp2);
        result.put("user", user2);
        result.put("desc", desc2);
        result.put("fechaHora", fechaHora2);
        result.put("post", post);

        return result;
    }



}
