package tcp2httpbridge.tcpendpoint;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.httpendpoint.HTTPServer;

public class TCPServer extends Thread{

	protected Socket socket;
	private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);
	
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream()); //获取客户端Socket对象的输入流
			logger.info("TCP server 收到信息 : "+dis.readUTF());
			dis.close();
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
