package com.ust.pos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto extends CommonDto {
    private String name;
    private String userName;
    private String phoneNo;
    private List<String> roles;
    private String password;
}
