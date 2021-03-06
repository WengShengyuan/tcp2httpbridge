package tcp2httpbridge.tcpendpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.common.ConfigLoader;

public class TCPClient {
	
	private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);
	
	/**
	 * 发送TCP数据，并接收返回
	 * @param ip TCP主机地址
	 * @param port TCP端口
	 * @param content 未加密的内容
	 * @return 未加密的返回
	 * @throws IOException
	 */
	public static byte[] send(String ip, int port, byte[] content) throws Exception{
		logger.info("Socket Client准备连接 : "+ip+":"+port);
		byte[] r;
		double rate = 0.0;
		Socket socket = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			socket = new Socket(ip, port);
			socket.setKeepAlive(true);
			socket.setSoTimeout(Integer.parseInt(ConfigLoader.getInstance().getValue("local.tcp.timeout"))*1000);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			
			logger.info("socket输出:"+new String(content));
			os.write(content);
			os.flush();
			byte[] responseData = new byte[Integer.parseInt(ConfigLoader.getInstance().getValue("app.maxbuffer"))];
			int readCount = 0;
			while (true) {
				int read = is.read(responseData, 0, responseData.length - readCount);
				if (read <= 0) {
					break;
				}
				readCount += read;
			}
			r = new byte[readCount];
			for(int i = 0 ; i < readCount; i ++){
				r[i] = responseData[i];
			}
			logger.info("socket读入:"+new String(r));
			rate = readCount / (Double.parseDouble(ConfigLoader.getInstance().getValue("app.maxbuffer")));
			logger.info("TCP CLIENT buffer 使用率:"+ rate);
			os.close();
			is.close();
			socket.close();
			ConfigLoader.getInstance().markSuccess_HTTP(rate);
			return r;
		} catch (Exception e) {
			logger.error("TCPCLIENT 发送TCP数据错误",e);
			throw e;
		} finally {
			if(is!=null){is.close();}
			if(os!=null){os.close();}
			if(socket!=null){socket.close();}
		}
	}
}
