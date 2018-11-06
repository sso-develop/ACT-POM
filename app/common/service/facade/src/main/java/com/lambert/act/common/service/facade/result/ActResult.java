package com.lambert.act.common.service.facade.result;

import java.io.Serializable;

public class ActResult<T> implements Serializable {

	 /** serialId */
	private static final long serialVersionUID = -3035296152778492506L;

	 /**
     * 是否流程
     */
    private boolean           flow;

	 /**
     * 返回的数据对�?
     */
    private T                 value;

    /**
     * 自定义错误信�?
     */
    private String            customErrMsg;

    /**
     * 获取数据
     *
     * @return
     */
    public T getValue() {
        return value;
    }

    /**
     * 此方法不应该被调�?,此方法只是为了能够正常json序列�?,请走构�?�函�?
     *
     */
    @Deprecated
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 获取返回�?
     *
     * @return
     */
    public ActResultCode getResultCode() {
        return resultCode;
    }

    @Deprecated
    /**
     * 此方法不应该被调�?.此方法只是为了能够正常json序列�?.请走构�?�函�?
     *
     */
    public void setResultCode(ActResultCode resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 通过value和code进行构�??
     * @param value
     * @param resultCode
     */
    public ActResult(T value, ActResultCode resultCode) {
        super();
        this.value = value;
        this.resultCode = resultCode;
    }

    /**
     *
     * 返回�?
     */
    private ActResultCode resultCode;

    /**
     * 只用value进行构�??,code总是为success
     * @param valueObject
     */
    public ActResult(T valueObject) {
        this(valueObject, ActResultCode.SUCCESS);
    }

    /**
     * 只用code进行构�??,value为null
     * @param resultCode
     */
    public ActResult(ActResultCode resultCode) {
        this(null, resultCode);
    }

    public ActResult(ActResultCode resultCode, String customErrMsg) {
        this(null, resultCode);
        this.customErrMsg = customErrMsg;
    }

    /**
     * 返回code.iscess
     *
     * @return
     */
    public boolean isSuccess() {
        return this.resultCode.isSuccess();
    }

    public String getMessage() {
        if (this.customErrMsg != null)
            return this.customErrMsg;
        return this.resultCode.getMessage();
    }

    public boolean isFlow() {
        return flow;
    }

    public void setFlow(boolean flow) {
        this.flow = flow;
    }

    public void setCustomErrMsg(String customErrMsg) {
        this.customErrMsg = customErrMsg;
    }


}
