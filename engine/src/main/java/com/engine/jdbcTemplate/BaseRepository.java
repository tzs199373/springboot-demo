package com.engine.jdbcTemplate;

import com.engine.beanFactory.BeanFactoryHelper;

public interface BaseRepository {

    default QryCenter getSysQryCenter() {
        return (QryCenter) BeanFactoryHelper.getBean("sysQryCenter");
    }

}
