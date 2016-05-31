package tcp2httpbridge.httpendpoint;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import tcp2httpbridge.common.ResultInfo;
import tcp2httpbridge.common.utils.Base64Util;
import tcp2httpbridge.common.utils.HTTPUtil;

public class HttpSender {
	private static final Logger logger = LoggerFactory.getLogger(HttpSender.class);

	/**
	 * 将TCPbyte转为HTTP请求发出，并接收返回
	 * @param url http服务地址
	 * @param content 未加密的byte[]
	 * @return ResultInfo<byte[]>, byte[] 为未解密的byte
	 * @throws Exception
	 */
	public static ResultInfo get(String url, byte[] content) throws Exception {
		ResultInfo result = new ResultInfo();
		url = url + "?enStr=" + new String(Base64Util.encryBytes(content));
		String rStr = HTTPUtil.get(url);
		logger.info("HTTP 返回:"+rStr);
		result = JSON.parseObject(rStr, ResultInfo.class);
		return result;
	}
	
	public static ResultInfo post(String url, byte[] content) throws Exception {
		ResultInfo result = new ResultInfo();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("enStr", new String(Base64Util.encryBytes(content)));
		String rStr = HTTPUtil.post(url, map, "UTF-8");
		logger.info("HTTP 返回:"+rStr);
		result = JSON.parseObject(rStr, ResultInfo.class);
		return result;
	}

}
