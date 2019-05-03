package com.engine.dao;


import com.commonutils.util.validate.ObjectCensor;
import com.engine.jdbcTemplate.BaseRepository;
import com.engine.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tzs
 * @version 1.0
 * @Description
 * @since 2019/4/16
 */
public interface UserRespository  extends JpaRepository<User, Integer> ,BaseRepository{
    List<User> findUsersByUsername(String username);

    default List<User> qryUsersByUsername(String username) {
        StringBuffer query = new StringBuffer("SELECT * FROM USER u ");
        query.append("WHERE 1=1 ");
        List lstParam = new ArrayList();
        if(ObjectCensor.isStrRegular(username)){
            query.append("AND u.username = ? ");
            lstParam.add(username);
        }
        return getSysQryCenter().executeSqlByMapListWithTrans(query.toString(), lstParam);
    }
}