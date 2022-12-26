package com.bugcoder.service;

import cn.hutool.core.collection.CollUtil;
import com.bugcoder.constant.MessageConstant;
import com.bugcoder.domain.SecurityUser;
import com.bugcoder.domain.UserDTO;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zbj
 * @date 2022/12/19
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    private List<UserDTO> userList;

    @Resource
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new UserDTO(1L,"zbj", password,1, CollUtil.toList("ADMIN")));
        userList.add(new UserDTO(2L,"bugcoder", password,1, CollUtil.toList("TEST")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
