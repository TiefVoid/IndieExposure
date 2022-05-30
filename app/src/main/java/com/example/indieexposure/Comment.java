package com.example.indieexposure;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String user2;
    private String desc2;
    private String pfp2;
    private long fechaHora2;



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

        return result;
    }



}
