package com.panda.project.system.user.domain;

import com.panda.framework.web.domain.BaseEntity;

/**
 * 用户对象 sys_user
 * 
 * @author panda
 */
public class UserLogin extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private String username ;
    private String password ;
    private Boolean rememberMe;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
