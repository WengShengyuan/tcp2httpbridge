package tcp2httpbridge.tcpendpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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
	public static byte[] send(String ip, int port, byte[] content) throws IOException {
		logger.info("发送:"+new String(content)+"->"+ip+":"+port);
		Socket socket = new Socket(ip, port);
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		
		logger.info("开始输出socket...");
		os.write(content);
		os.flush();
		logger.info("开始读入socket...");
		byte[] responseData = new byte[Integer.parseInt(ConfigLoader.getInstance().getValue("app.maxbuffer"))];
		int readCount = 0;
		while (true) {
			int read = is.read(responseData, 0, responseData.length - readCount);
			if (read <= 0) {
				break;
			}
			readCount += read;
		}
		byte[] r = new byte[readCount];
		for(int i = 0 ; i < readCount; i ++){
			r[i] = responseData[i];
		}
		logger.info("关闭socket...");
		os.close();
		is.close();
		socket.close();
		logger.info("读入socket结束，接收返回:"+new String(r));
		return r;
	}
	
}
