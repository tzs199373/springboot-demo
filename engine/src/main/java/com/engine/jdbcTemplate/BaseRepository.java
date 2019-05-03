package com.engine.jdbcTemplate;

import com.engine.beanFactory.BeanFactoryHelper;

/**
 * Created by LEO on 2019/1/17.
 */
public interface BaseRepository {

    default QryCenter getSysQryCenter() {
        return (QryCenter) BeanFactoryHelper.getBean("sysQryCenter");
    }

}
