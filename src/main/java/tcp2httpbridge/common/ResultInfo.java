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
	private HashMap<String, byte[]> map = new HashMap<String, byte[]>();
	

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
		return map.get(key);
	}
	
	public byte[] put(String key ,byte[] t){
		return map.put(key, t);
	}

	public HashMap<String, byte[]> getMap() {
		return map;
	}

}
