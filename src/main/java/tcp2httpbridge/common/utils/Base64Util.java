package tcp2httpbridge.common.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	
	public static byte[] encryBytes(byte[] in){
		Base64 base64 = new Base64();
		return base64.encode(in);
	}
	
	public static byte[] decryBytes(byte[] in){
		Base64 base64 = new Base64();
		return base64.decode(in);
	}

}
