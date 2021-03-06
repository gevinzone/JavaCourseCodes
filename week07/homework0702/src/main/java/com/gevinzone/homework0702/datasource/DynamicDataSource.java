package com.gevinzone.homework0702.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private final ThreadLocal<DataSourceEnum> content_holder = new ThreadLocal<>();
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }
    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public void setDataSource(DataSourceEnum dataSource) {
        content_holder.set(dataSource);
    }

    public DataSourceEnum getDataSource() {
        return content_holder.get();
    }

    public void clearDataSource() {
        content_holder.remove();
    }
}
