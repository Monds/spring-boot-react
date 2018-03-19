package com.monds.demos.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @Getter
    private String username;

    @Getter
    private String password;

    @Getter
    private boolean enabled;

}
