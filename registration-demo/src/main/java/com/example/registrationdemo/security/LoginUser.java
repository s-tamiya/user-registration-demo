package com.example.registrationdemo.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {

    private com.example.registrationdemo.entity.User user;

    public com.example.registrationdemo.entity.User getUser() {
      return this.user;
    }

    public LoginUser(com.example.registrationdemo.entity.User user) {
      super(user.getName(), user.getPassword(), null);
      //super(user.getName(), user.getPassword(), determineRoles(user.getAdmin()));
      this.user = user;
      System.out.println("Login User : name=" + this.user.getName() + " " + " auth=" + this.getAuthorities());
    }

    private static final List<GrantedAuthority> USER_ROLE = AuthorityUtils.createAuthorityList("ROLE_USER");
    private static final List<GrantedAuthority> ADMIN_ROLE = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");

    private static List<GrantedAuthority> determineRoles(boolean isAdmin) {
      return isAdmin ? ADMIN_ROLE : USER_ROLE;
    }
}
