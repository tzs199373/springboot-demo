package com.commonutils.util.http;

import com.commonutils.util.json.JSONObject;
import com.commonutils.util.string.StringUtil;
import com.commonutils.util.validate.ObjectCensor;
import com.ning.http.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

public class HttpUtil {
	private static Log log = LogFactory.getLog(HttpUtil.class);

	private static Request buildRequest(String url , String params) throws Exception {
		RequestBuilder requestBuilder = new RequestBuilder("POST");
		requestBuilder.setBody(params);
		requestBuilder.addHeader("Accept", "application/json");
		requestBuilder.addHeader("Content-type", "application/json;charset=UTF-8");
		requestBuilder.setMethod("POST");
		requestBuilder.setUrl(url);
		return requestBuilder.build();
	}

	public static String asyncHttp(String url, String params) {
		AsyncHttpClient asyncHttpClient = null;
		try{
			Request request = buildRequest(url , params);
			AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder().setRequestTimeout(50000).setConnectTimeout(50000).setReadTimeout(50000);
			asyncHttpClient = new AsyncHttpClient(builder.build());
			Future<Response> responseFuture = asyncHttpClient.executeRequest(request);
			Response response = responseFuture.get();
			return response.getResponseBody();
		}catch (Exception err) {
			err.printStackTrace();
		} finally {
			if(asyncHttpClient != null) {
				asyncHttpClient.close();
			}
		}
		return "{\"code\":400,\"error_code\":\"100001\",\"error_desc\":\"connection error\"}";
	}

	public static String postBody(String url, Map<String, String> headerMap, String body) throws Exception {
		// 结果
		String content = null;
		CloseableHttpResponse response = null;
		// 实例化httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 实例化post方法
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(120 * 1000).setConnectionRequestTimeout(120 * 1000)
				.setSocketTimeout(120 * 1000).build();
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-Type" , "application/json");
		try {
			if (!ObjectCensor.checkObjectIsNull(headerMap)) {
				for (Entry<String, String> e : headerMap.entrySet()) {
					httpPost.setHeader(e.getKey(), e.getValue());
				}
			}
			// 将参数给post方法
			if (ObjectCensor.isStrRegular(body)) {
				StringEntity stringEntity = new StringEntity(body , "UTF-8");
				httpPost.setEntity(stringEntity);
			}
			// 执行post方法
			response = httpClient.execute(httpPost);
			content = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	public static String httpPOST(String url, Map<String, String> headerMap,String data) throws Exception {
		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (!ObjectCensor.checkObjectIsNull(data)) {
			JSONObject joinParam = JSONObject.fromObject(data);
			Iterator iter = joinParam.keys();
			while (iter.hasNext())
			{
				String keyT =iter.next()+"";
				String key  = StringUtil.getRstFieldName_ByVoFldName(keyT).toLowerCase();
				String val= StringUtil.getJSONObjectKeyVal(joinParam,keyT);
				sb.append(key);
				sb.append("=");
				sb.append(URLEncoder.encode(val, "UTF-8"));
				sb.append("&");
			}
			sb.replace(0, sb.length(), sb.substring(0, sb.length() - 1));
		}
		// 结果
		String content = "";
		CloseableHttpResponse response = null;
		// 实例化httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 实例化post方法
		HttpPost httpPost = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(120 * 1000).setConnectionRequestTimeout(120 * 1000)
				.setSocketTimeout(120 * 1000).build();
		httpPost.setConfig(requestConfig);
		//httpPost.setHeader("Content-Type" , "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-Type" , "application/x-www-form-urlencoded");
		if (!ObjectCensor.checkObjectIsNull(headerMap)) {
			for (Entry<String, String> e : headerMap.entrySet()) {
				httpPost.setHeader(e.getKey(), e.getValue());
			}
		}
		try {

			// 将参数给post方法
			if (ObjectCensor.isStrRegular(sb.toString())) {
				StringEntity stringEntity = new StringEntity(sb.toString() , "UTF-8");
				httpPost.setEntity(stringEntity);
			}
			// 执行post方法
			response = httpClient.execute(httpPost);
			log.error(response.getStatusLine());
			content = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			content = e.toString();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
}
