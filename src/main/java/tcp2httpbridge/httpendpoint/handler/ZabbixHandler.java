package tcp2httpbridge.httpendpoint.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import tcp2httpbridge.common.ConfigLoader;
import tcp2httpbridge.common.ResultInfo;
import tcp2httpbridge.common.StaticValue;
import tcp2httpbridge.common.utils.Base64Util;
import tcp2httpbridge.httpendpoint.handler.core.MyHandler;
import tcp2httpbridge.httpendpoint.handler.core.MyRequest;
import tcp2httpbridge.httpendpoint.handler.core.MyResponse;
import tcp2httpbridge.tcpendpoint.TCPClient;

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
		ResultInfo<byte[]> info = new ResultInfo<byte[]>();
		byte[] decBytes = Base64Util.decryBytes(enStr.getBytes());
		try {
			byte[] returnBytes = TCPClient.send(ConfigLoader.getInstance().getValue("remote.tcp.server"),
					Integer.parseInt(ConfigLoader.getInstance().getValue("remote.tcp.port")), decBytes);
			info.put("enStr", Base64Util.encryBytes(returnBytes));
		} catch (IOException e) {
			info.setStateId(-1);
			info.setErrorMsg(e.toString());
		}
		response.write(JSONObject.toJSONString(info));
	}


}
