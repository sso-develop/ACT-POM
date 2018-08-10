package com.lambert.act.biz.act.repository.model;

public class HistoricVariableInstanceModel {
	String variableName;
	Object value;
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
