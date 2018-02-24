package com.kolon.common.admin.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseUserInfo extends BaseInfo implements UserDetails{

    private String mngId;
    private String mngPw;
    private String mngNm;
    private String mngEmail;
    private String useYn;
    private String bigo;
    private String regDttm;
    private String regUserId;
    private String updDttm;
    private String updUserId;

    private String uuid;
    private Collection<? extends GrantedAuthority> authorities;

    public BaseUserInfo() {}

    public BaseUserInfo(String pagingEnable, String condOrder, String condAlign)
    {
        super(pagingEnable, condOrder, condAlign);
    }

    public BaseUserInfo(String mngId, String mngNm, String mngEmail, String useYn, String bigo)
    {
        this.mngId      = mngId;
        this.mngNm      = mngNm;
        this.mngEmail   = mngEmail;
        this.useYn      = useYn;
        this.bigo       = bigo;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }

    public String getMngId() {
        return mngId;
    }

    public void setMngId(String mngId) {
        this.mngId = mngId;
    }

    public String getMngPw() {
        return mngPw;
    }

    public void setMngPw(String mngPw) {
        this.mngPw = mngPw;
    }

    public String getMngNm() {
        return mngNm;
    }

    public void setMngNm(String mngNm) {
        this.mngNm = mngNm;
    }

    public String getMngEmail() {
        return mngEmail;
    }

    public void setMngEmail(String mngEmail) {
        this.mngEmail = mngEmail;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getBigo() {
        return bigo;
    }

    public void setBigo(String bigo) {
        this.bigo = bigo;
    }

    public String getRegDttm() {
        return regDttm;
    }

    public void setRegDttm(String regDttm) {
        this.regDttm = regDttm;
    }

    public String getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    public String getUpdDttm() {
        return updDttm;
    }

    public void setUpdDttm(String updDttm) {
        this.updDttm = updDttm;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getPassword() {
        return getMngPw();
    }

    @Override
    public String getUsername() {
        return getMngNm();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
