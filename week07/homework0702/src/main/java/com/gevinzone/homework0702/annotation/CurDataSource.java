package com.gevinzone.homework0702.annotation;

import com.gevinzone.homework0702.datasource.DataSourceEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurDataSource {
    DataSourceEnum value() default DataSourceEnum.MASTER;
}
