package com.commonutils.util.jdbcTemplate;

import com.commonutils.util.beanFactory.BeanFactoryHelper;

/**
 * Created by LEO on 2019/1/17.
 */
public interface BaseRepository {

    default QryCenter getSysQryCenter() {
        return (QryCenter) BeanFactoryHelper.getBean("sysQryCenter");
    }

}
