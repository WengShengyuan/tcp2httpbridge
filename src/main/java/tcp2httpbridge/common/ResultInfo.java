package tcp2httpbridge.common;

import java.io.Serializable;
import java.util.HashMap;

public class ResultInfo<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5403924761942985496L;

	private int stateId; // 状态
	private String errorMsg = ""; // 错误信息
	private HashMap<String, T> map = new HashMap<String, T>();
	

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
	
	public T get(String key){
		return map.get(key);
	}
	
	public T put(String key ,T t){
		return map.put(key, t);
	}

	public HashMap<String, T> getMap() {
		return map;
	}

}
