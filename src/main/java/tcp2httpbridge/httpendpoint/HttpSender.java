package tcp2httpbridge.httpendpoint;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpSender {
	
	public static String send(String url, byte[] content){
		
		Base64 base64 = new Base64();
		byte[] encrypted = base64.encode(content);
		String enStr = new String(encrypted);
		System.out.println("encrypted : "+ enStr);
		String r = "";
		/* 1 生成 HttpClinet 对象并设置参数 */
	    HttpClient httpClient = HttpClients.createDefault();
	    CloseableHttpResponse httpResponse = null;
	    try {
            //用get方法发送http请求
            HttpGet get = new HttpGet(url+"?enStr="+enStr);
            System.out.println("执行get请求:...."+get.getURI());
            //发送get请求
            httpResponse = (CloseableHttpResponse) httpClient.execute(get);
                //response实体
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    r=  EntityUtils.toString(entity);
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				httpResponse.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	    return r;
	}

}
