package com.lambert.act.common.uitl.result;

import java.io.Serializable;

public class ResultModel implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2643592251749830282L;
	
	Object data;
	boolean success;
	String msg;
	int code;
	

	public ResultModel() {
		super();
	}

	public ResultModel(Object data, boolean success, String msg, int code) {
		super();
		this.data = data;
		this.success = success;
		this.msg = msg;
		this.code = code;
	}
	
	public void init(Object data, boolean success, String msg, int code) {
		this.data = data;
		this.success = success;
		this.msg = msg;
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
	
	
}
