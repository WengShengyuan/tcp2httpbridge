package tcp2httpbridge.httpendpoint.handler.core;

import java.util.logging.Handler;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public abstract class MyHandler {
	
	public void service(MyRequest request, MyResponse response) {  
        request.initRequestHeader();  
        request.initRequestParam(); 
      
        if(request.getMethod().equals(MyRequest.GET)){  
            doGet(request,response);  
        }else if(request.getMethod().equals(MyRequest.POST)){  
            request.initRequestBody();  
            doPost(request,response);  
        } 
    }  
  
    public abstract void doGet(MyRequest request, MyResponse response);  
  
    public abstract void doPost(MyRequest request, MyResponse response);  
}
