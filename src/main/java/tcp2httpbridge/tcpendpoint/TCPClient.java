package tcp2httpbridge.tcpendpoint;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPClient {
	
	private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);
	
	public static void send(String ip, int port, String content) throws IOException {
		logger.info("发送:"+content+"->"+ip+":"+port);
		Socket socket = new Socket(ip, port);
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); //获取Socket对象的
		dos.writeUTF(content);
		dos.flush(); //确保所有数据都已经输出  
	    dos.close(); //关闭输出流  
	    socket.close(); //关闭Socket连接  
	}
	
	public static void main(String[] args){
		try {
			send("127.0.0.1",1234,"content");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
