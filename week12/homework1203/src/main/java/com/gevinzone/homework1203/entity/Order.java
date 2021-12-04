package com.gevinzone.homework1203.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class Order implements Serializable {
    private int id;
    private int status;
    private Date createTime;
    private Date updateTime;
}
