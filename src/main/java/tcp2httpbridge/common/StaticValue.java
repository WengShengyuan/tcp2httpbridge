package tcp2httpbridge.common;

public class StaticValue {
	
	public static class APPTYPE {
		public static String SERVER = "server";
		public static String CLIENT = "client";
	}
	
	public static class PARAM {
		public static int TCPPORT=1234;
		public static int HTTPPORT=8888;
		public static int MAXHTTP=100;
		public static int BUFFERSIZE=20480;
		public static String HTTPSERVER="http://localhost";
		
	}
	
	public static class URL {
		public static String ROOT = "tcp2httpbridge";
		public static String ZABBIX= "zabbix";
		public static String TCPSERVER="127.0.0.1";
		public static int TCPPORT=10051;
	}

}
