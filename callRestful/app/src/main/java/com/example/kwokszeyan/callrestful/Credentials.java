package com.example.kwokszeyan.callrestful;

import java.util.Date;

public class Credentials {

    private String username;
    private String password;
    private int resid;
    private Date registerdate;

    public Credentials(String username, int resid, String password, Date registerdate) {
        this.username = username;
        this.resid = resid;
        this.password = password;
        this.registerdate = registerdate;
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
