package com.jjh.eventlistener.repository;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class HSqlRepository {
    
    @Autowired
    private NamedParameterJdbcTemplate template;
    
    @Autowired
    @Qualifier("HSqlMap")
    private Map<String,String> sqlMap;
    
    public <T> T queryForObject(String sqlKey, Map<String,?> paramMap, Class<T> requiredType) throws Exception {
        return template.queryForObject(mappingSql(sqlMap.get(sqlKey),paramMap), paramMap, requiredType);
    }
    
    public int update(String sqlKey, Map<String, ?> paramMap) throws Exception {
        return template.update( mappingSql(sqlMap.get(sqlKey),paramMap), paramMap );
    }
    
    public List<Map<String,Object>> queryForList(String sqlKey, Map<String,?> paramMap) throws Exception {
        return template.queryForList( mappingSql(sqlMap.get(sqlKey),paramMap), paramMap );
    }
    
    public Map<String,Object> queryForMap(String sqlKey, Map<String,?> paramMap) throws Exception {
        return template.queryForMap( mappingSql(sqlMap.get(sqlKey),paramMap), paramMap );
    }
    
    public String mappingSql(String sql, Map<String, ?> paramMap) {
        for(String key : paramMap.keySet()) {
            String value = paramMap.get(key) == null ? "''" : "'" + paramMap.get(key).toString() + "'";
            sql = RegExUtils.replaceAll(sql, "\\$\\b" + key +"\\b", value);
        }
        
        return sql;
    }
    
}