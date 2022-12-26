package com.bugcoder.component;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.bugcoder.domain.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 从header获取登录用户信息
 * @author zbj
 * @date 2022/12/20
 */
@Component
public class LoginHandler {

    public UserDTO getCurrentUser(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject jsonObject = new JSONObject(userStr);
        UserDTO currentUser = new UserDTO();
        currentUser.setUsername(jsonObject.getStr("user_name"));
        currentUser.setId(Convert.toLong(jsonObject.get("id")));
        currentUser.setRoles(Convert.toList(String.class, jsonObject.get("authorities")));
        return currentUser;
    }
}
