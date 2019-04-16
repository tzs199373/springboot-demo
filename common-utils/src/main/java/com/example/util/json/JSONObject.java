package com.example.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections4.map.ListOrderedMap;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by LEO on 2019/1/28.
 */
public class JSONObject implements Serializable {

    private boolean nullObject;

    private Map properties;

    public JSONObject() {
        this.properties = new ListOrderedMap();
    }

    public JSONObject(Map map) {
        this.properties = map;
    }

    public JSONObject( boolean isNull ) {
        this();
        this.nullObject = isNull;
    }

    public Map toMap() {
        return this.properties;
    }

    public static JSONObject fromObject( Object object ) {
        Map map = new ListOrderedMap();
        try {
            String value = JsonUtils.beanToJson(object);
            map = JSONConfig.getInstance().readValue(value, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(map);
    }

    public static Object toBean( JSONObject jsonObject, Class beanClass ) {
        return JsonUtils.jsonToBean(jsonObject.toString(), beanClass);
    }

    public Object put( Object key, Object value ) {
        if( key == null ){
            throw new IllegalArgumentException( "key is null." );
        }
        Object previous = this.properties.get( key );
        if(value instanceof JSONObject){
            this.properties.put( String.valueOf( key ), ((JSONObject) value).toMap() );
        } else if(value instanceof JSONArray) {
            this.properties.put( String.valueOf( key ), ((JSONArray) value).toList() );
        } else {
            this.properties.put( String.valueOf( key ), value );
        }
        return previous;
    }

    public Object element( Object key, Object value ) {
        return this.put(key, value);
    }

    public void putAll( Map map ) {
        for(Iterator entries = map.entrySet().iterator(); entries.hasNext(); ){
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            this.properties.put( key, value );
        }
    }

    public void putAll( JSONObject map ) {
        this.putAll(map.toMap());
    }

    public Object remove( Object key ) {
        return properties.remove( key );
    }

    public Object remove( String key ) {
        verifyIsNull();
        return this.properties.remove( key );
    }

    public int size() {
        return this.properties.size();
    }

    public JSONArray toJSONArray( JSONArray names ) {
        verifyIsNull();
        if( names == null || names.size() == 0 ){
            return null;
        }
        JSONArray ja = new JSONArray();
        for( int i = 0; i < names.size(); i += 1 ){
            ja.add( this.get( names.getString( i ) ) );
        }
        return ja;
    }

    public boolean isNullObject() {
        return nullObject;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    private void verifyIsNull() {
        if( isNullObject() ){
            throw new JSONException( "null object" );
        }
    }

    public void clear() {
        this.properties.clear();
    }

    public Object get( Object key ) {
        if( key instanceof String ){
            return get( (String) key );
        }
        return null;
    }

    public Object get( String key ) {
        verifyIsNull();
        return this.properties.get( key );
    }

    public boolean getBoolean( String key ) {
        verifyIsNull();
        Object o = get( key );
        if( o != null ){
            if( o.equals( Boolean.FALSE )
                    || (o instanceof String && ((String) o).equalsIgnoreCase( "false" )) ){
                return false;
            }else if( o.equals( Boolean.TRUE )
                    || (o instanceof String && ((String) o).equalsIgnoreCase( "true" )) ){
                return true;
            }
        }
        throw new JSONException( "JSONObject[" + key + "] is not a Boolean." );
    }

    public double getDouble( String key ) {
        verifyIsNull();
        Object o = get( key );
        if( o != null ){
            try{
                return o instanceof Number ? ((Number) o).doubleValue()
                        : Double.parseDouble( (String) o );
            }catch( Exception e ){
                throw new JSONException( "JSONObject[" + key + "] is not a number." );
            }
        }
        throw new JSONException( "JSONObject[" + key + "] is not a number." );
    }

    public int getInt( String key ) {
        verifyIsNull();
        Object o = get( key );
        if( o != null ){
            return o instanceof Number ? ((Number) o).intValue() : (int) getDouble( key );
        }
        throw new JSONException( "JSONObject[" + key + "] is not a number." );
    }

    public JSONArray getJSONArray( String key ) {
        verifyIsNull();
        Object o = get( key );
        if(o != null) {
            try {
                List list = null;
                if(o instanceof  List) {
                    list = (List)o;
                } else {
                    list = JSONConfig.getInstance().readValue(o.toString(), List.class);
                }
                return new JSONArray(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new JSONException( "JSONObject[" + key + "] is not a JSONArray." );
    }

    public JSONObject getJSONObject( String key ) {
        verifyIsNull();
        Object o = get( key );
        if(o != null) {
            try {
                Map map = null;
                if(o instanceof  Map) {
                    map = (Map)o;
                } else {
                    map = JSONConfig.getInstance().readValue(o.toString(), Map.class);
                }
                return new JSONObject(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new JSONException( "JSONObject[" + key + "] is not a JSONObject." );
    }

    public long getLong( String key ) {
        verifyIsNull();
        Object o = get( key );
        if( o != null ){
            return o instanceof Number ? ((Number) o).longValue() : (long) getDouble( key );
        }
        throw new JSONException( "JSONObject[" + key + "] is not a number." );
    }

    public String getString( String key ) {
        verifyIsNull();
        Object o = get( key );
        if( o != null ){
            if(o instanceof String) {
                return o.toString();
            } else {
                try {
                    return JSONConfig.getInstance().writeValueAsString(o).toString();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new JSONException( "JSONObject[" + key + "] not found." );
    }

    public Iterator keys() {
        verifyIsNull();
        return keySet().iterator();
    }

    public Set keySet() {
        return Collections.unmodifiableSet( properties.keySet() );
    }

    @Override
    public String toString() {
        try {
            return JSONConfig.getInstance().writeValueAsString(this.properties).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
