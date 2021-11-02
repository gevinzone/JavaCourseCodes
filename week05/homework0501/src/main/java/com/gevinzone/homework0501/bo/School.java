package com.gevinzone.homework0501.bo;

import lombok.ToString;

import javax.annotation.Resource;

@ToString
public class School {
    @Resource(name = "class1")
    private Klass class1;
    @Resource(name = "class2")
    private Klass class2;
    @Resource(name = "class3")
    private Klass class3;
    @Resource(name = "student01")
    private Student superStudent;
    @Resource
    private Student student101;
}
