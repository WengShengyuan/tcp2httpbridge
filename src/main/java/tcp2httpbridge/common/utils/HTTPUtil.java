package tcp2httpbridge.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HTTPUtil {

	
	private static final int timeOut = 5000;
	private static final String CHARSET = "UTF-8";
	
	
	public static String get(String pageUrl) throws Exception{
		HttpClient client  = HttpClients.createDefault();
	    HttpGet httpGet = new HttpGet();
	    httpGet.setURI(new URI(pageUrl));
	    String content = "";
	    BufferedReader in=null;
	    try {
			HttpResponse response = client.execute(httpGet);      
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {      
			    	in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			    	StringBuffer sb = new StringBuffer("");
			    	String line = "";
			    	while((line = in.readLine())!=null){
			    		sb.append(line).append("\n");
			    	}
			    	in.close();
			    	content = sb.toString();
			        
			} else {
				throw new Exception("网络解析错误:" + response.getStatusLine());
			}
		} catch (Exception e) {
			throw e;
		} finally{
	    	if(in != null){
	    		in.close();
	    	}
	    }
	    return content;
	}
	

	/**
	 * POST方式获取HTTP响应
	 *  Function: post
	 * 
	 *  @author wengshengyuan  DateTime 2015-8-19 上午9:02:14
	 *  @param postUrl
	 *  @param map
	 *  @param encoding
	 *  @return
	 *  @throws Exception
	 */
	public static String post(String postUrl, HashMap<String, String> map,
			String encoding) throws Exception {
		URL url = new URL(postUrl);
		HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					connection.getOutputStream(), encoding);
			if (map != null) {
				osw.write(parseParam(map));
			}
			osw.flush();
			osw.close();
		} catch (Exception e) {
//			e.printStackTrace();
		} 
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			throw e;
		}
		
		finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return buffer.toString();

	}
	
	public static String postFormEntity(String postUrl, HashMap<String, String> map, String encoding) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(postUrl);
		try {

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();// 设置请求和传输超时时间
			httppost.setConfig(requestConfig);
			httppost.addHeader("Content-type",
					"application/x-www-form-urlencoded");
			httppost.setEntity(createHttpEntity(map, encoding));

			HttpResponse response = httpClient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */

				HttpEntity hEntity = response.getEntity();
				String strResult = EntityUtils.toString(hEntity);
				return strResult;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	
	private static HttpEntity createHttpEntity(HashMap<String, String> map, String encoding)
			throws UnsupportedEncodingException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for(String k : map.keySet()){
			nameValuePairs.add(new BasicNameValuePair(k,map.get(k)));
		}
		HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs,encoding);
		return entity;
	}
	
	private static String parseParam(HashMap<String, String> map)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key));
			sb.append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}
	
}
