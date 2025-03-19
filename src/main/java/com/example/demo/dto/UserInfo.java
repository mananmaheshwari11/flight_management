package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserInfo {
    public Long id;
    public String name;
    public String email;

}
