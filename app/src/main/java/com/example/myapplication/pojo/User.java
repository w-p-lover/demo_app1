package com.example.myapplication.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userName;
    private String pwd;
    private String tel;
    private int sex;
    private String mail;
}
