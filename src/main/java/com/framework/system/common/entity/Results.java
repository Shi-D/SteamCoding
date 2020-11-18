package com.framework.system.common.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* [{"result":true,"message":"success"}]
 *  true --> resultValue
 *  "success" --> messageValue
 *  "result" --> resultKey
 *  "message" --> messageKey
 *  [{}] --> result
 *  {} --> temp
 */
public class Results {
	private Object resultValue = true;
	private Object messageValue = "";
	private String resultKey = "result";
	private String messageKey = "message";
	private List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	private Map<String, Object> temp = new HashMap<String, Object>();
	//最后把所有结果放入到result中
	private void addResult() {
		temp.put(this.resultKey, this.resultValue);
		temp.put(this.messageKey, this.messageValue);
		result.add(temp);
	}

	public Results() {
	}

	public Results(boolean resultValue, Object messageValue) {
		this.messageValue = messageValue;
		this.resultValue = resultValue;
	}
	//改变value
	public void addAllValue(boolean resultValue, Object messageValue) {
		this.messageValue = messageValue;
		this.resultValue = resultValue;
	}
	//改变key
	public void changeAllKey(String resultKey,String messageKey){
		this.resultKey = resultKey;
		this.messageKey = messageKey;
	}
	public Object getResultValue() {
		return resultValue;
	}

	public void setResultValue(Object resultValue) {
		this.resultValue = resultValue;
	}

	public void setResultValue(boolean resultValue) {
		this.resultValue = resultValue;
	}

	public Object getMessageValue() {
		return messageValue;
	}

	public void setMessageValue(Object messageValue) {
		this.messageValue = messageValue;
	}

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public List<Map<String, Object>> getResult() {
		addResult();
		return result;
	}

}
