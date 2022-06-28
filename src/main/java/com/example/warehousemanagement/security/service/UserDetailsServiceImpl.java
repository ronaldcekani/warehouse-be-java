package com.example.warehousemanagement.security.service;

import com.example.warehousemanagement.security.domain.AppUserDetail;
import com.example.warehousemanagement.user.domain.Role;
import com.example.warehousemanagement.user.domain.User;
import com.example.warehousemanagement.user.persistence.UserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, IllegalStateException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Bad credentials");
        }

//        final HttpServletRequest request = getRequest();
        AppUserDetail userDetail = new AppUserDetail();
        userDetail.setUsername(username);
        userDetail.setPassword(user.getPassword());
        userDetail.setRoles(user.getRoles().stream().map(Role::getName).toList());
        return userDetail;
    }

    private HttpServletRequest getRequest() throws Exception {
        try {
            final RequestAttributes currentRequestAttributes = RequestContextHolder.currentRequestAttributes();
            return ((ServletRequestAttributes) currentRequestAttributes).getRequest();
        } catch (final IllegalStateException e) {
            throw new IllegalStateException("Cannot access current request: " + e.getMessage());
        }
    }
}
