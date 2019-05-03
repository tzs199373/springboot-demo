package com.engine.jdbcTemplate;


import com.commonutils.util.json.JSONObject;
import com.commonutils.util.string.StringUtil;
import com.commonutils.util.validate.ObjectCensor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QryCenter extends JdbcTemplate {

    public QryCenter() {
    }

    public QryCenter(DataSource dataSource) {
        super(dataSource);
    }

    public QryCenter(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    public List<Map<String, Object>> executeSqlByMapListWithTrans(String sql, List<Object> queryList) throws DataAccessException {
        if(ObjectCensor.checkListIsNull(queryList)) {
            return this.query(sql, queryList.toArray(), new ClassMapRowMapper());
        } else {
            return this.query(sql, new ClassMapRowMapper());
        }
    }

    /**
     * 批量查询
     * @param sqlMap sql语句
     * @param queryMap 参数
     * @param mapListFlag map或者list标识(map:true、list:false)
     * @return
     * @throws DataAccessException
     */
    public Map<String,Object> executeSqlByMapListWithTrans(LinkedHashMap<String,String> sqlMap, LinkedHashMap<String,List<Object>> queryMap, boolean[] mapListFlag) throws DataAccessException {
        int pos = 0;
        Map<String,Object> retMap = new HashMap<String,Object>();
        for(Map.Entry<String, String> entry : sqlMap.entrySet()) {
            String key = entry.getKey();
            List<Object> queryList = queryMap.get(key);
            if(mapListFlag[pos]) {
                List list = null;
                if(ObjectCensor.checkListIsNull(queryList)) {
                    list = this.query(entry.getValue(), queryList.toArray(), new ClassMapRowMapper());
                } else {
                    list = this.query(entry.getValue(), new ClassMapRowMapper());
                }
                if(ObjectCensor.checkListIsNull(list)) {
                    retMap.put(key, list.get(0));
                } else {
                    retMap.put(key, new HashMap());
                }
            } else {
                if(ObjectCensor.checkListIsNull(queryList)) {
                    retMap.put(key, this.query(entry.getValue(), queryList.toArray(), new ClassMapRowMapper()));
                } else {
                    retMap.put(key, this.query(entry.getValue(), new ClassMapRowMapper()));
                }
            }
            pos++;
        }
        return retMap;
    }

    public List<Map<String, Object>> executeSqlByMapList(String sql, Object... params) throws DataAccessException {
        if(params.length > 0) {
            return this.query(sql, params, new ClassMapRowMapper());
        } else {
            return this.query(sql, new ClassMapRowMapper());
        }
    }

    public JSONObject executeSqlByMapListWithTrans(String querySql, String totalSql, List<Object> queryList) throws DataAccessException {
        JSONObject json = new JSONObject();
        List queryRetList = null;
        List totalRetList = null;
        if(ObjectCensor.checkListIsNull(queryList)) {
            queryRetList = this.query(querySql, queryList.toArray(), new ClassMapRowMapper());
            totalRetList = this.query(totalSql, queryList.toArray(), new ClassMapRowMapper());
        } else {
            queryRetList = this.query(querySql, new ClassMapRowMapper());
            totalRetList = this.query(totalSql, new ClassMapRowMapper());
        }
        json.put("row", queryRetList);
        if(ObjectCensor.checkListIsNull(totalRetList)) {
            String total = StringUtil.getMapKeyVal((Map)totalRetList.get(0), "count");
            json.put("total", total);
        }
        return json;
    }

}
