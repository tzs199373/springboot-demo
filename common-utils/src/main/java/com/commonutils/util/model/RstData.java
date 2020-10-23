package com.commonutils.util.model;

import com.commonutils.util.json.JSONArray;
import com.commonutils.util.json.JSONObject;

public class RstData {
    private int code = ResultStatus.SUCCESS;

    private Object data;

    private String msg = ResultStatus.SUCCESS_INFO;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        if(data instanceof JSONObject){
            this.data = ((JSONObject) data).toMap();
        }else if(data instanceof JSONArray){
            this.data = ((JSONArray) data).toList();
        }else{
            this.data = data;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
