package tcp2httpbridge.httpendpoint.handler;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.httpendpoint.handler.core.MyHandler;
import tcp2httpbridge.httpendpoint.handler.core.MyRequest;
import tcp2httpbridge.httpendpoint.handler.core.MyResponse;

public class ZabbixHandler extends MyHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(ZabbixHandler.class);  

	
	@Override
	public void doGet(MyRequest request, MyResponse response) {
		 doPost(request, response);
	}

	@Override
	public void doPost(MyRequest request, MyResponse response) {
		logger.info("接收请求:"+ request.getReuestURI().getPath());
		String enStr = request.getParamter("enStr");
		logger.info("获取字符串:"+enStr);
		Base64 base64 = new Base64();
		byte[] dec = base64.decode(enStr.getBytes());
		String deStr = new String(dec);
		logger.info("解密后:"+deStr);
		
		response.write(deStr);
	}


}
