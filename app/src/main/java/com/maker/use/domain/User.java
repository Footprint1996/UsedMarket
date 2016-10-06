package com.maker.use.domain;

import java.io.Serializable;

/**
 * 用户bean
 * Created by XT on 2016/10/6.
 */

public class User implements Serializable {
    public String username;
    public String password;
    public String sex;

    public String toString() {
        return username + "," + password + "," + sex;
    }
}