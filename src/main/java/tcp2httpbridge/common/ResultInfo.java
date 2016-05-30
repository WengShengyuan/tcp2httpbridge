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
	private String content;
	

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
