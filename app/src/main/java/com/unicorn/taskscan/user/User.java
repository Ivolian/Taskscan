package com.unicorn.taskscan.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String account;

    @NotNull
    private String pwd;

    @Generated(hash = 289850843)
    public User(Long id, @NotNull String account, @NotNull String pwd) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
