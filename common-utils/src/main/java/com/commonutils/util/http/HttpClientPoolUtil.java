package com.commonutils.util.http;

import com.commonutils.util.json.JSONObject;
import com.commonutils.util.string.StringUtil;
import com.commonutils.util.validate.ObjectCensor;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public class HttpClientPoolUtil {
	private static CloseableHttpClient httpClient;

	private static final Logger logger = LoggerFactory.getLogger(HttpClientPoolUtil.class);

	static {
			//采用绕过验证的方式处理https请求
			SSLContext sslcontext = createIgnoreVerifySSL();
			//设置协议http和https对应的处理socket链接工厂的对象
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslcontext))
					.build();
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			cm.setDefaultMaxPerRoute(20);
			cm.setMaxTotal(500);
			httpClient = HttpClients.custom().setKeepAliveStrategy((HttpResponse response, HttpContext context)-> {
				HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				int keepTime = 30;
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						try {
							return Long.parseLong(value) * 1000;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				return keepTime * 1000;
			}).setConnectionManager(cm).build();
	}


	/**
	 * 绕过验证
	 */
	private static SSLContext createIgnoreVerifySSL()  {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSLv3");
			// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
			X509TrustManager trustManager = new X509TrustManager() {
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) {
				}
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) {
				}
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sc.init(null, new TrustManager[] { trustManager }, null);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sc;
	}

	/**
	 * 创建请求
	 *
	 * @param url 请求url
	 * @param methodName 请求的方法类型
	 * @param headMap 请求头
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static HttpRequestBase getRequest(String url, String methodName,Map<String, String> headMap)
			throws KeyManagementException, NoSuchAlgorithmException {
		HttpRequestBase method;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000).setConnectTimeout(30 * 1000)
				.setConnectionRequestTimeout(30 * 1000).setExpectContinueEnabled(false).build();

		if (HttpPut.METHOD_NAME.equalsIgnoreCase(methodName)) {
			method = new HttpPut(url);
		} else if (HttpPost.METHOD_NAME.equalsIgnoreCase(methodName)) {
			method = new HttpPost(url);
		} else if (HttpGet.METHOD_NAME.equalsIgnoreCase(methodName)) {
			method = new HttpGet(url);
		} else if (HttpDelete.METHOD_NAME.equalsIgnoreCase(methodName)) {
			method = new HttpDelete(url);
		} else {
			method = new HttpPost(url);
		}
		if(!headMap.isEmpty()){
			for(Entry<String, String> value:headMap.entrySet()){
				method.addHeader(value.getKey(), value.getValue());
			}
		}
		method.setConfig(requestConfig);
		return method;
	}

	/**
	 ****************************************************** 四大基本方法*********************************************
	 */

	/**
	 * 执行http get请求
	 *
	 * @param url 请求url
	 * @param headMap 请求头
	 * @return
	 */
	public static <R> R get(String url,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		HttpEntity httpEntity = null;
		HttpRequestBase method = null;
		R responseBody;
		try {
			method = getRequest(url, HttpGet.METHOD_NAME,headMap);
			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(method, context);
			responseBody = afterResposne.apply(httpResponse);
		} catch (Exception e) {
			if(method != null){
				method.abort();
			}
			throw e;
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consumeQuietly(httpEntity);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return responseBody;
	}

	/**
	 * 执行http post请求
	 *
	 * @param url 		请求地址
	 * @param data  	请求数据		若为application/x-www-form-urlencoded,data为键值对字符串；若为application/json：data为json字符串
	 * @param headMap  	请求头 		headMap内需要指定Content-Type
	 * @return
	 */
	public static <R> R post(String url, String data,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		HttpEntity httpEntity = null;
		HttpEntityEnclosingRequestBase method = null;
		R responseBody;
		try {
			method = (HttpEntityEnclosingRequestBase) getRequest(url, HttpPost.METHOD_NAME,headMap);
			method.setEntity(new StringEntity(data,Charset.forName("UTF-8")));
			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(method, context);
			responseBody = afterResposne.apply(httpResponse);

		} catch (Exception e) {
			if(method != null){
				method.abort();
			}
			throw e;
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consumeQuietly(httpEntity);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return responseBody;
	}

	/**
	 * 执行http put请求
	 *
	 * @param url 		请求地址
	 * @param data  	请求数据
	 * @param headMap  	请求头
	 * @return
	 */
	public static <R> R put(String url, String data,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		HttpEntity httpEntity = null;
		HttpEntityEnclosingRequestBase method = null;
		R responseBody;
		try {
			method = (HttpEntityEnclosingRequestBase) getRequest(url, HttpPut.METHOD_NAME,headMap);
			method.setEntity(new StringEntity(data,Charset.forName("UTF-8")));
			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(method, context);
			responseBody = afterResposne.apply(httpResponse);

		} catch (Exception e) {
			if(method != null){
				method.abort();
			}
			throw e;
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consumeQuietly(httpEntity);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return responseBody;
	}

	/**
	 * 执行DELETE 请求
	 *
	 * @param url 		请求地址
	 * @param headMap  	请求头
	 * @return
	 */
	public static <R> R delete(String url,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		HttpEntity httpEntity = null;
		HttpRequestBase method = null;
		R responseBody;
		try {
			method = getRequest(url, HttpDelete.METHOD_NAME,headMap);
			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(method, context);
			responseBody = afterResposne.apply(httpResponse);
		} catch (Exception e) {
			if(method != null){
				method.abort();
			}
			throw e;
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consumeQuietly(httpEntity);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return responseBody;
	}

	/**
	 ****************************************************** 具体协议*********************************************
	 */
	/**
	 * @param url
	 * @param param		JSON字符串,将转为键值对形式追加到url后面
	 * @param body		JSON字符串
	 * @param headMap	消息头，内含"Content-Type"="application/x-www-form-urlencoded"
	 * @return
	 * @throws Exception
	 */
	public static <R> R postForm(String url, String param,String body, Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		headMap.put("Content-Type","application/x-www-form-urlencoded");

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (!ObjectCensor.checkObjectIsNull(body)) {
			JSONObject joinParam = JSONObject.fromObject(body);
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
		return HttpClientPoolUtil.post(appendParamToURL(url,param), sb.toString(),headMap,afterResposne);
	}

	/**
	 * @param url
	 * @param param		JSON字符串,将转为键值对形式追加到url后面
	 * @param body		JSON字符串
	 * @param headMap	消息头，内含"Content-Type"="application/json"
	 * @return
	 * @throws Exception
	 */
	public static <R> R postJSON(String url, String param,String body,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		headMap.put("Content-Type","application/json");
		return HttpClientPoolUtil.post(appendParamToURL(url,param), body,headMap,afterResposne);
	}

	/**
	 * @param url
	 * @param headMap		消息头，底层API会构建"Content-Type"="multipart/form-data"，不要再包含Content-Type，否则会覆盖
	 * @param formBodyParts	消息体，普通字段以及文件
	 * @return
	 * @throws Exception
	 */
	public static <R> R postMultipartData(String url, Map<String, String> headMap, ArrayList<FormBodyPart> formBodyParts, Function<CloseableHttpResponse,R> afterResposne) throws Exception {
		HttpEntity httpEntity = null;
		HttpEntityEnclosingRequestBase method = null;
		R responseBody;
		try {
			method = (HttpEntityEnclosingRequestBase) getRequest(url,HttpPost.METHOD_NAME,headMap);
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			for (int i = 0; i < formBodyParts.size(); i++) {
				multipartEntityBuilder.addPart(formBodyParts.get(i).getName(),formBodyParts.get(i).getBody());
			}
			httpEntity = multipartEntityBuilder.build();
			method.setEntity(httpEntity);

			HttpContext context = HttpClientContext.create();
			CloseableHttpResponse httpResponse = httpClient.execute(method, context);
			responseBody = afterResposne.apply(httpResponse);
		} catch (Exception e) {
			if(method != null){
				method.abort();
			}
			throw e;
		} finally {
			if (httpEntity != null) {
				try {
					EntityUtils.consumeQuietly(httpEntity);
				} catch (Exception e) {
					throw e;
				}
			}
		}
		return responseBody;
	}
	/**
	 * @param url
	 * @param param		JSON字符串,将转为键值对形式追加到url后面
	 * @param body		JSON字符串
	 * @param headMap	消息头，内含"Content-Type"="application/x-www-form-urlencoded"
	 * @return
	 * @throws Exception
	 */
	public static <R> R putForm(String url, String param,String body,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		headMap.put("Content-Type","application/x-www-form-urlencoded");

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (!ObjectCensor.checkObjectIsNull(body)) {
			JSONObject joinParam = JSONObject.fromObject(body);
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
		return HttpClientPoolUtil.put(appendParamToURL(url,param), sb.toString(),headMap,afterResposne);
	}

	/**
	 * @param url
	 * @param param		JSON字符串,将转为键值对形式追加到url后面
	 * @param body		JSON字符串
	 * @param headMap	消息头，内含"Content-Type"="application/json"
	 * @return
	 * @throws Exception
	 */
	public static <R> R putJSON(String url, String param,String body,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		headMap.put("Content-Type","application/json");
		return HttpClientPoolUtil.put(appendParamToURL(url,param),body,headMap,afterResposne);
	}

	/**
	 * @param url
	 * @param param		JSON字符串,将转为键值对形式追加到url后面
	 * @return
	 * @throws Exception
	 */
	public static <R> R delete(String url,String param,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		return HttpClientPoolUtil.delete(appendParamToURL(url,param),headMap,afterResposne);
	}

	/**
	 * @param url
	 * @param param		JSON字符串,将转为键值对形式追加到url后面
	 * @return
	 * @throws Exception
	 */
	public static <R> R get(String url, String param,Map<String, String> headMap,Function<CloseableHttpResponse,R> afterResposne) throws Exception{
		return HttpClientPoolUtil.get(appendParamToURL(url,param),headMap,afterResposne);
	}

	private static String appendParamToURL(String url,String param) throws Exception{
		if(ObjectCensor.isStrRegular(param)){
			JSONObject json  = JSONObject.fromObject(param);
			Iterator iter =  json.keys();
			if(json.size() > 0 ){
				StringBuffer sb = new StringBuffer(url+"?");
				while(iter.hasNext())
				{
					String key =iter.next()+"";
					String val= StringUtil.getJSONObjectKeyVal(json,key);
					sb.append(key+"="+URLEncoder.encode(val, "UTF-8")+"&");
				}
				sb.deleteCharAt(sb.length() - 1);
				url = sb.toString();
			}
		}
		return url;
	}
	/**
	 ****************************************************** Response解析*********************************************
	 */
	public static Function getResponseString = new Function<CloseableHttpResponse,String>() {
		@Override
		public String apply(CloseableHttpResponse httpResponse) {
			String str = "";
			int httpResponseCode = httpResponse.getStatusLine().getStatusCode();//状态码
			logger.info("httpResponseCode[{}]",httpResponseCode);
			try {
				str = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
			return str;
		}
		@Override
		public <V> Function<V, String> compose(Function<? super V, ? extends CloseableHttpResponse> before) {
			return null;
		}
		@Override
		public <V> Function<CloseableHttpResponse, V> andThen(Function<? super String, ? extends V> after) {
			return null;
		}
	};
	/**
	 ****************************************************** example*********************************************
	 */
	public static void main(String[] args) throws Exception {
		Object result = HttpClientPoolUtil.postMultipartData("http://192.168.137.67:8080/file/upload",
				new HashMap<String,String>(),
				new ArrayList<FormBodyPart>(){{
					add(new FormBodyPart(
							"uploadFile",
							new FileBody(
									new File(
											"C:\\Users\\asus\\Desktop\\log\\1.zip"),
									ContentType.create("application/x-gzip-compressed"),
									"1.zip")
					));
					add(new FormBodyPart(
							"key",
							new StringBody("value", ContentType.TEXT_PLAIN)
					));
				}},getResponseString);
		System.out.println("http result: " + result);
	}
}
