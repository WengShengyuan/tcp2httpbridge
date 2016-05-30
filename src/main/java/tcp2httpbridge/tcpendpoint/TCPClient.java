package tcp2httpbridge.tcpendpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.common.StaticValue;

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
		byte[] buf = new byte[StaticValue.PARAM.BUFFERSIZE];
		os.write(content);
		os.flush();
		socket.shutdownOutput();
		int length = is.read(buf);
		byte[] data = new byte[length];
		for(int i=0;i<length;i++){
			data[i] = buf[i];
		}
		logger.info("接收返回:"+new String(data));
		return data;
	}
	
}
