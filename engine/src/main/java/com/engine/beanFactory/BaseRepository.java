package com.engine.beanFactory;

import com.engine.jdbcTemplate.QryCenter;

public interface BaseRepository {

    default QryCenter getSysQryCenter() {
        return (QryCenter) BeanFactoryHelper.getBean("sysQryCenter");
    }

}
