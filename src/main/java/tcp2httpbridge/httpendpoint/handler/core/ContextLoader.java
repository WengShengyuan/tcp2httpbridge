package tcp2httpbridge.httpendpoint.handler.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import tcp2httpbridge.common.utils.XmlUtil;

public class ContextLoader {
	
	
	private static Map<String,MyHandler> contextMap = new HashMap<String,MyHandler>();  
    public static String contextPath = "";  
    public static void load(){  
        try{  
        	InputStream in = ContextLoader.class.getClassLoader().getResourceAsStream("context.xml");
            Document doc = XmlUtil.load(in);  
            Element root = doc.getDocumentElement();  
              
            contextPath = XmlUtil.getAttribute(root,"context");  
            Element[] handlers = XmlUtil.getChildrenByName(root, "handler");  
            for(Element ele : handlers){  
                String handle_class = XmlUtil.getChildText(ele, "handler-class");  
                String url_pattern = XmlUtil.getChildText(ele, "url-pattern");  
                  
                Class<?> cls = Class.forName(handle_class);  
                Object newInstance = cls.newInstance();  
                if(newInstance instanceof MyHandler){  
                    contextMap.put(contextPath+url_pattern, (MyHandler)newInstance);  
                }  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     *  
     * @param key 
     * @return 
     */  
    public static MyHandler getHandler(String key){  
        return contextMap.get(key);  
    } 

}
