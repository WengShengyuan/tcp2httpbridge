package tcp2httpbridge.common;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
	
	private Properties pro;
	private InputStream input;
	private Long successCount_TCP=0L;
	private Long failCount_TCP=0L;
	private Double bufferRate_TCP=0.0;
	private Long successCount_HTTP=0L;
	private Long failCount_HTTP=0L;
	private Double bufferRate_HTTP=0.0;
	
	private volatile static ConfigLoader instance;
	
	public static ConfigLoader getInstance(){
		if(instance == null){
			synchronized(ConfigLoader.class){
				if(instance == null){
					instance = new ConfigLoader("app.properties");
				}
			}
		}
		return instance;
	}
	
	private ConfigLoader(String path){
		pro = new Properties();
		try {
			// 读取属性文件
			input =  ConfigLoader.class.getClassLoader().getResourceAsStream("app.properties");
			// 装载文件
			pro.load(input);
			input.close();
		} catch (Exception e) { }
		
	}
	
	public void markSuccess_TCP(double bufferRate){
		this.bufferRate_TCP += bufferRate;
		this.successCount_TCP++;
	}
	
	public void markFail_TCP(){
		this.failCount_TCP++;
	}
	
	public void markSuccess_HTTP(double bufferRate){
		this.bufferRate_HTTP+=bufferRate;
		this.successCount_HTTP++;
	}
	
	public void markFail_HTTP(){
		this.failCount_HTTP++;
	}
	
	
	
	public Long getSuccessCount_TCP() {
		return successCount_TCP;
	}

	public Long getFailCount_TCP() {
		return failCount_TCP;
	}

	public Double getBufferRate_TCP() {
		return bufferRate_TCP;
	}

	public Long getSuccessCount_HTTP() {
		return successCount_HTTP;
	}

	public Long getFailCount_HTTP() {
		return failCount_HTTP;
	}

	public Double getBufferRate_HTTP() {
		return bufferRate_HTTP;
	}

	public String getValue(String key){
		if (pro.containsKey(key)) {
			String value = pro.getProperty(key);
			return value;
		} else {
			return "";
		}
	}
}
