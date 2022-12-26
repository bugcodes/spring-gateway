package com.bugcoder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zbj
 * @date 2022/12/19
 */
@Data
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1711433680701645987L;

    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}
