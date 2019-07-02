package com.example.kwokszeyan.smarter;

import java.util.Date;

public class Credential {

    private String username;
    private int resid;
    private String password;
    private Date registerdate;

    public Credential() {
    }


    public Credential(String username, int resid, String password) {
        this.username = username;
        this.resid = resid;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }
}
