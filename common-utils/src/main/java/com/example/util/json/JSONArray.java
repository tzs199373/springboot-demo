package com.example.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONArray implements Serializable {

    private List elements;

    public JSONArray(List list) {
        this.elements = list;
    }

    public List toList() {
        return this.elements;
    }

    public static JSONArray fromObject( Object object ) {
        List list = new ArrayList();
        try {
            String value = JsonUtils.beanToJson(object);
            list = JSONConfig.getInstance().readValue(value, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONArray(list);
    }

    public JSONArray() {
        this.elements = new ArrayList();
    }

    private List getList() {
        return this.elements;
    }

    public static List toList(JSONArray jsonArray) {
        return jsonArray.getList();
    }

    public boolean add( Object value ) {
        if(value instanceof JSONObject){
            return this.elements.add(((JSONObject) value).toMap());
        } else if(value instanceof JSONArray) {
            return this.elements.add(((JSONArray) value).toList());
        } else {
            return this.elements.add(value);
        }
    }

    public void clear() {
        this.elements.clear();
    }

    public Object get( int index ) {
        return this.elements.get( index );
    }

    public boolean getBoolean( int index ) {
        Object o = get( index );
        if( o != null ){
            if( o.equals( Boolean.FALSE ) || (o instanceof String && ((String) o).equalsIgnoreCase( "false" )) ){
                return false;
            }else if( o.equals( Boolean.TRUE ) || (o instanceof String && ((String) o).equalsIgnoreCase( "true" )) ){
                return true;
            }
        }
        throw new JSONException( "JSONArray[" + index + "] is not a Boolean." );
    }

    public double getDouble( int index ) {
        Object o = get( index );
        if( o != null ){
            try{
                return o instanceof Number ? ((Number) o).doubleValue() : Double.parseDouble( (String) o );
            }catch( Exception e ){
                throw new JSONException( "JSONArray[" + index + "] is not a number." );
            }
        }
        throw new JSONException( "JSONArray[" + index + "] is not a number." );
    }

    public int getInt( int index ) {
        Object o = get( index );
        if( o != null ){
            return o instanceof Number ? ((Number) o).intValue() : (int) getDouble( index );
        }
        throw new JSONException( "JSONArray[" + index + "] is not a number." );
    }

    public JSONArray getJSONArray( int index ) {
        Object o = get( index );
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
        throw new JSONException( "JSONArray[" + index + "] is not a JSONArray." );
    }

    public JSONObject getJSONObject( int index ) {
        Object o = get( index );
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
        throw new JSONException( "JSONArray[" + index + "] is not a JSONObject." );
    }

    public long getLong( int index ) {
        Object o = get( index );
        if( o != null ){
            return o instanceof Number ? ((Number) o).longValue() : (long) getDouble( index );
        }
        throw new JSONException( "JSONArray[" + index + "] is not a number." );
    }

    public String getString( int index ) {
        Object o = get( index );
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
        throw new JSONException( "JSONArray[" + index + "] not found." );
    }

    public int lastIndexOf( Object o ) {
        return this.elements.lastIndexOf( o );
    }

    public Object remove( int index ) {
        return this.elements.remove( index );
    }

    public boolean remove( Object o ) {
        return this.elements.remove( o );
    }

    public int size() {
        return this.elements.size();
    }

    public Object[] toArray() {
        return this.elements.toArray();
    }

    public Object[] toArray( Object[] array ) {
        return this.elements.toArray( array );
    }

    @Override
    public String toString() {
        try {
            return JSONConfig.getInstance().writeValueAsString(this.elements).toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
