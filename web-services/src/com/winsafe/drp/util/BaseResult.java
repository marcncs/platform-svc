package com.winsafe.drp.util;

public class BaseResult {
	
	private boolean legal;
	private String message;
	private Object result;
	
	public BaseResult() {}
	
	public BaseResult(boolean legal, String msg, Object result) {
		this.legal = legal; 
		this.message = msg;
		this.result = result;
	}
	
	public BaseResult(boolean legal, String msg, Object result,Integer totalCount) {
		this.legal = legal;
		this.message = msg;
		this.result = result;
	}
	
	public BaseResult(boolean legal, String msg, Object result,String cpage,Integer totalCount) {
		this.legal = legal;
		this.message = msg;
		this.result = result;
	}
	public BaseResult(boolean legal, String msg) {
		this(legal, msg, null);
	}
	
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isNotLegal(){
		return !this.legal;
	}
	
	public BaseResult onErrorMessage(String message){
		this.legal = false;
		this.message = message;
		return this;
	}
	
	public BaseResult onInputEmptyMessage(String title){
		this.legal = false;
		this.message = "请输入" + title + "！";
		return this;
	}
	
	public BaseResult onSelectEmptyMessage(String title){
		this.legal = false;
		this.message = "请选择" + title + "！";
		return this;
	}
	
	public BaseResult onLengthErrorMessage(String title, int length){
		this.legal = false;
		this.message = "您输入的" + title + "过长，请确认" + title + "在" +  length + "字符之内！";
		return this;
	}
		
	public BaseResult onSuccessMessage(){
		this.legal = true;
		return this;
	}
	
	public BaseResult onSuccessMessage(String message){
		this.legal = true;
		this.message = message;
		return this;
	}
	
	public boolean isLegal() {
		return legal;
	}
	public void setLegal(boolean legal) {
		this.legal = legal;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
