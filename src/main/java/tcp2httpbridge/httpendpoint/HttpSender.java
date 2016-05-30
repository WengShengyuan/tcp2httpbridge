package tcp2httpbridge.httpendpoint;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import tcp2httpbridge.common.ResultInfo;
import tcp2httpbridge.common.utils.Base64Util;
import tcp2httpbridge.httpendpoint.handler.ZabbixHandler;

public class HttpSender {
	private static final Logger logger = LoggerFactory.getLogger(HttpSender.class);

	/**
	 * 将TCPbyte转为HTTP请求发出，并接收返回
	 * @param url http服务地址
	 * @param content 未加密的byte[]
	 * @return ResultInfo<byte[]>, byte[] 为未解密的byte
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static ResultInfo<byte[]> send(String url, byte[] content) throws Exception {
		ResultInfo<byte[]> result = new ResultInfo<byte[]>();
		HttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		try {
			HttpGet get = new HttpGet(url + "?enStr=" + new String(Base64Util.encryBytes(content)));
			httpResponse = (CloseableHttpResponse) httpClient.execute(get);
			// response实体
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				String r = EntityUtils.toString(entity);
				result = JSON.parseObject(r, ResultInfo.class);
			}
			return result;
		} catch (Exception e) {
			logger.error("http请求错误", e);
			throw e;
		} finally {
			httpResponse.close();
			httpClient = null;
		}
	}

}
