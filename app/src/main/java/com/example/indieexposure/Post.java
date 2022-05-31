package com.example.indieexposure;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {
    private String user;
    private String desc;
    private String img;
    private String audio;
    private String pfp;
    private String user_key;
    private long fechaHora;

    public Post(){

    }

    public Post(String user, String desc, String img, String audio, String pfp, long fechaHora, String user_key) {
        this.user = user;
        this.desc = desc;
        this.img = img;
        this.audio = audio;
        this.pfp = pfp;
        this.fechaHora = fechaHora;
        this.user_key = user_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public long getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(long fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("pfp", pfp);
        result.put("user", user);
        result.put("desc", desc);
        result.put("img", img);
        result.put("audio", audio);
        result.put("fechaHora", fechaHora);

        return result;
    }

}
