package com.gevinzone.homework0702.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    private long id;
    private String username;
    private String password;
    private String salt;
    private String nickname;
    private String idNumber;
    private Date createTime;
    private Date updateTime;
}
