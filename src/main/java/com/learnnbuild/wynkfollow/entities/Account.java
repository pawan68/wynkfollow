package com.learnnbuild.wynkfollow.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@MappedSuperclass
public abstract class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "user_name")
    public String userName;

    @Column(name = "password")
    public String password;

    public Date joiningDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }
    //    public Person person;

}
