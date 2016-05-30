package tcp2httpbridge.common;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
	
	private Properties pro;
	private FileInputStream input;
	
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
			input = new FileInputStream(path);
			// 装载文件
			pro.load(input);
			input.close();
		} catch (Exception e) { }
		
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
