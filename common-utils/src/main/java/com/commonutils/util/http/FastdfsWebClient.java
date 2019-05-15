package com.commonutils.util.http;

import com.commonutils.util.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FastdfsWebClient {
    private static final Logger logger = LoggerFactory.getLogger(FastdfsWebClient.class);
    private static String FASTDFS_SERVICE_ADDRESS = "";//文件服务器上传路径

    /**
     * 文件对象传输
     * @param file
     * @return
     * @throws IOException
     */
    public static String upload(File file) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000000).build();
        HttpPost httpPost = new HttpPost(FASTDFS_SERVICE_ADDRESS);
        httpPost.setConfig(requestConfig);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file",file);
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity responseEntity = httpResponse.getEntity();
        int statusCode= httpResponse.getStatusLine().getStatusCode();
        JSONObject uploadResult = JSONObject.fromObject(EntityUtils.toString(responseEntity,"utf-8"));

        //成功或失败，约定
        if (statusCode == 200 && uploadResult.getBoolean("flag")) {
            return uploadResult.getString("url");
        } else {
            logger.error("上传文件服务器失败：" + uploadResult.getString("msg"));
            return uploadResult.getString("msg");
        }
    }
}
