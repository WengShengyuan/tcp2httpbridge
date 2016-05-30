package tcp2httpbridge.common;

import java.io.Serializable;
import java.util.HashMap;

public class ResultInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5403924761942985496L;

	private int stateId; // 状态
	private String errorMsg = ""; // 错误信息
	private HashMap<String, String> map = new HashMap<String, String>();
	

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public byte[] get(String key){
		return map.get(key).getBytes();
	}
	
	public String put(String key ,byte[] t){
		return map.put(key, t.toString());
	}

	public HashMap<String, String> getMap() {
		return map;
	}

}
