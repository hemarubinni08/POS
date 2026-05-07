package com.ust.pos.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends CommonFields {
    private String username;
    private String name;
    private String phoneNo;
    private List<String> roles;
    private String password;
}
