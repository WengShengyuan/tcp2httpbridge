package tcp2httpbridge.tcpendpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.common.StaticValue;
import tcp2httpbridge.httpendpoint.HttpSender;

public class TCPServer extends Thread{

	protected Socket socket;
	private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);
	
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			byte[] buf = new byte[StaticValue.PARAM.BUFFERSIZE];  
			int length = is.read(buf);
			byte[] data = new byte[length];
			for(int i=0;i<length;i++){
				data[i] = buf[i];
			}
			logger.info("TCP server 接收到数据: " + new String(data));
			String request = new String(data); 
			
			String result = HttpSender.send("http://127.0.0.1:8888/tcp2httpbridge/zabbix", data);
			
			System.out.println(result);  
			is.close();
			socket.close();
		} catch (IOException e) {
			logger.error("接收TCP数据出现错误", e);
		}
	}
	
	public static void startServer(int port, Class obj){
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			logger.info("TCP server 监听 : " + port);
			while(true){
				Socket e =null;
				try {
					// 在这里一直等待链接
					e = serverSocket.accept();
					
					// 一旦链接，将socket转入新进程进行处理，主进程重新监听新请求
					logger.info("建立连接...");
					TCPServer server = (TCPServer) obj.newInstance();
					server.socket = e;
					server.start();
				} catch (InstantiationException e1) {
					logger.error("建立TCP连接错误",e1);
					e.close();
				} catch (IllegalAccessException e1) {
					logger.error("建立TCP连接错误",e1);
					e.close();
				} 
			}
		} catch (IOException e) {
			logger.error("启动TCP监听错误",e);
		}
	}
	
}
