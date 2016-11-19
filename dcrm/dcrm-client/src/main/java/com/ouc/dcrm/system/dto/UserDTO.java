package com.ouc.dcrm.system.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = -773456235054L;
   
    private Integer id;

    private String login_name;

    private String password;

    private String true_name;

    private String mobile_phone;

    private String email;

    private String related_role_ids;

    private Integer sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name == null ? null : login_name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name == null ? null : true_name.trim();
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone == null ? null : mobile_phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRelated_role_ids() {
        return related_role_ids;
    }

    public void setRelatedRoleIds(String related_role_ids) {
        this.related_role_ids = related_role_ids == null ? null : related_role_ids.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
