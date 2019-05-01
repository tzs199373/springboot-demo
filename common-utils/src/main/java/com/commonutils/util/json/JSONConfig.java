package com.commonutils.util.json;

import com.commonutils.util.date.SysDate;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JSONConfig {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // ����������ֶ�ȫ������
        objectMapper.setSerializationInclusion(Include.ALWAYS);
        // ȡ��Ĭ��ת��timestamps��ʽ
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // ���Կ�BeanתJson�Ĵ���
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // �������ڸ�ʽͳһΪ yyyy:MM:dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(SysDate.STANDARD_FORMAT));
        // ������Json�ַ����д��ڣ�����Java�����в����ڵĶ�Ӧ���Ե�״������ֹ����
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // ������δ֪���Ե�ʱ���׳��쳣
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // ǿ��JSON ���ַ���("")ת��Ϊnull����ֵ:
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // ��JSON������C/C++ ��ʽ��ע��(�Ǳ�׼��Ĭ�Ͻ���)
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // ����û�����ŵ��ֶ������Ǳ�׼��
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // �������ţ��Ǳ�׼��
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

}
