package tcp2httpbridge.httpendpoint.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.common.ConfigLoader;
import tcp2httpbridge.httpendpoint.handler.core.MyHandler;
import tcp2httpbridge.httpendpoint.handler.core.MyRequest;
import tcp2httpbridge.httpendpoint.handler.core.MyResponse;

public class BridgeHealth extends MyHandler {

private static final Logger logger = LoggerFactory.getLogger(BridgeHealth.class);  

	
	@Override
	public void doGet(MyRequest request, MyResponse response) {
		 doPost(request, response);
	}

	@Override
	public void doPost(MyRequest request, MyResponse response) {
		logger.info("接收请求:"+ request.getReuestURI().getPath());
		StringBuilder responseBuilder = new StringBuilder();
		responseBuilder.append("**********params**************\n")
			.append("local tcp port:").append(ConfigLoader.getInstance().getValue("local.tcp.port")).append("\n")
			.append("local http port:").append(ConfigLoader.getInstance().getValue("local.http.port")).append("\n")
			.append("remote tcp port:").append(ConfigLoader.getInstance().getValue("remote.tcp.port")).append("\n")
			.append("remote http port:").append(ConfigLoader.getInstance().getValue("remote.http.port")).append("\n")
			.append("max http handler:").append(ConfigLoader.getInstance().getValue("app.maxhttphandler")).append("\n")
			.append("remote tcp address:").append(ConfigLoader.getInstance().getValue("remote.tcp.server")).append("\n")
			.append("remote http address:").append(ConfigLoader.getInstance().getValue("remote.http.server")).append("\n")
			.append("tcp timeout:").append(ConfigLoader.getInstance().getValue("local.tcp.timeout")).append("\n")
			.append("byte array buffer size:").append(ConfigLoader.getInstance().getValue("app.maxbuffer")).append("\n")
			
			
			.append("**********statistics**************\n")
			.append("success count TCP:"+ConfigLoader.getInstance().getSuccessCount_TCP()).append("\n")
			.append("fail count TCP:"+ConfigLoader.getInstance().getFailCount_TCP()).append("\n")
			.append("TCP buffer use average:"+(ConfigLoader.getInstance().getBufferRate_TCP() / (double)ConfigLoader.getInstance().getSuccessCount_TCP())).append("\n")
			.append("success count HTTP:"+ConfigLoader.getInstance().getSuccessCount_HTTP()).append("\n")
			.append("fail count HTTP:"+ConfigLoader.getInstance().getFailCount_HTTP()).append("\n")
			.append("HTTP buffer use average:"+(ConfigLoader.getInstance().getBufferRate_HTTP() / (double)ConfigLoader.getInstance().getSuccessCount_HTTP())).append("\n")
			
			;
			
			
			
		response.write(responseBuilder.toString());
	}

}
