package com.ouc.dcrm.system.model;

import java.util.Date;

public class User {
    private Integer id;

    private String login_name;

    private String password;

    private String true_name;

    private String mobile_phone;

    private String email;

    private String related_role_ids;

    private Integer sex;

    private String last_login_ip;

    private String current_login_ip;

    private Integer login_attempt_times;

    private Date login_faild_time;

    private Integer password_first_modified_flag;

    private Date gmt_create;

    private Date gmt_modified;

    private String create_by;

    private String last_modified_by;

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

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip == null ? null : last_login_ip.trim();
    }

    public String getCurrent_login_ip() {
        return current_login_ip;
    }

    public void setCurrent_login_ip(String current_login_ip) {
        this.current_login_ip = current_login_ip == null ? null : current_login_ip.trim();
    }

    public Integer getLogin_attempt_times() {
        return login_attempt_times;
    }

    public void setLogin_attempt_times(Integer login_attempt_times) {
        this.login_attempt_times = login_attempt_times;
    }

    public Date getLogin_faild_time() {
        return login_faild_time;
    }

    public void setLogin_faild_time(Date login_faild_time) {
        this.login_faild_time = login_faild_time;
    }

    public Integer getPassword_first_modified_flag() {
        return password_first_modified_flag;
    }

    public void setPassword_first_modified_flag(Integer password_first_modified_flag) {
        this.password_first_modified_flag = password_first_modified_flag;
    }

    public Date getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Date gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Date getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(Date gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by == null ? null : create_by.trim();
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }

    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by == null ? null : last_modified_by.trim();
    }
}