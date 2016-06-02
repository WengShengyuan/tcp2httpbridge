package tcp2httpbridge.tcpendpoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.common.ConfigLoader;
import tcp2httpbridge.common.ResultInfo;
import tcp2httpbridge.common.utils.Base64Util;
import tcp2httpbridge.httpendpoint.HttpSender;

public class TCPServer extends Thread{

	protected Socket socket;
	private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);
	
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			byte[] buf = new byte[Integer.parseInt(ConfigLoader.getInstance().getValue("app.maxbuffer"))];  
			int length = is.read(buf);
			byte[] data = new byte[length];
			for(int i=0;i<length;i++){
				data[i] = buf[i];
			}
			logger.info("TCP server 接收到数据: " + new String(data));
			logger.info("TCP SERVER buffer 使用率:"+ length / (Double.parseDouble(ConfigLoader.getInstance().getValue("app.maxbuffer"))));
			socket.shutdownInput();
			ResultInfo result = HttpSender.post(
					ConfigLoader.getInstance().getValue("remote.http.server")+":"+
					ConfigLoader.getInstance().getValue("remote.http.port")+"/"+
					ConfigLoader.getInstance().getValue("api.zabbix"), data);
			
			if(result.getStateId()<0){
				logger.error("状态码不为0,"+result.getErrorMsg());
			} else {
				if(result.getContent().isEmpty()){
					logger.info("返回的content为空，不处理本次交互");
				} else {
					byte[] en = result.getContent().getBytes();
					byte[] de = Base64Util.decryBytes(en);
					logger.info("TCP 写入:"+new String(de));
					OutputStream os = socket.getOutputStream();
					os.write(de);
					os.flush();
					socket.shutdownOutput();
				}
			}
			logger.info("完成TCP交互，退出socket");
			socket.close();
		} catch (Exception e) {
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
					logger.info("建立新连接...");
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
