package com.iqy.im.security;

import com.iqy.im.dao.UserDao;
import com.iqy.im.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("User not found.");
        }

        AuthUser authUser = new AuthUser();
        authUser.setId(user.getId());
        authUser.setUsername(username);
        authUser.setPassword(user.getPassword());
        authUser.setRoles(Collections.singletonList("USER"));
        authUser.setPermissions(Collections.emptyList());

        return authUser;
    }
}
