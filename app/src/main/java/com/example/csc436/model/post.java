package com.example.csc436.model;

public class post {

    public String fullname;
    public String username;
    public String current_user;
    public String savecurrentdate;
    public String savecurrenttime;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(String current_user) {
        this.current_user = current_user;
    }

    public String getSavecurrentdate() {
        return savecurrentdate;
    }

    public void setSavecurrentdate(String savecurrentdate) {
        this.savecurrentdate = savecurrentdate;
    }

    public String getSavecurrenttime() {
        return savecurrenttime;
    }

    public void setSavecurrenttime(String savecurrenttime) {
        this.savecurrenttime = savecurrenttime;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }

    public String post;
    public String pfp;

    public post(String fullname, String username, String current_user, String savecurrentdate, String savecurrenttime, String post, String pfp) {
        this.fullname = fullname;
        this.username = username;
        this.current_user = current_user;
        this.savecurrentdate = savecurrentdate;
        this.savecurrenttime = savecurrenttime;
        this.post = post;
        this.pfp = pfp;
    }



}
