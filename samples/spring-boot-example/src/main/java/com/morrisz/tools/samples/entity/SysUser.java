package com.morrisz.tools.samples.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author zhangyoumao
 */
@Entity
@Table(name = "SYS_USER")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Integer userId;

    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "IS_VALID", length = 1)
    @Type(type = "yes_no")
    private boolean valid;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "CHINESE_NAME")
    private String chineseName;

    @Column(name = "EMAIL")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser sysUser = (SysUser) o;
        return valid == sysUser.valid &&
                Objects.equals(userId, sysUser.userId) &&
                Objects.equals(roleId, sysUser.roleId) &&
                Objects.equals(userName, sysUser.userName) &&
                Objects.equals(chineseName, sysUser.chineseName) &&
                Objects.equals(email, sysUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId, valid, userName, chineseName, email);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
