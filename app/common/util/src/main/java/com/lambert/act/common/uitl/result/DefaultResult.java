package com.lambert.act.common.uitl.result;

import java.io.Serializable;

public class DefaultResult<T> implements Serializable {

	 /** serialId */
	private static final long serialVersionUID = -3035296152778492506L;

	 /**
     * 是否流程
     */
    private boolean           flow;

	 /**
     * 返回的数据对象
     */
    private T                 value;

    /**
     * 自定义错误信息
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
     * 此方法不应该被调用,此方法只是为了能够正常json序列化,请走构造函数
     *
     */
    @Deprecated
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 获取返回码
     *
     * @return
     */
    public DefaultResultCode getResultCode() {
        return resultCode;
    }

    @Deprecated
    /**
     * 此方法不应该被调用.此方法只是为了能够正常json序列化.请走构造函数
     *
     */
    public void setResultCode(DefaultResultCode resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 通过value和code进行构造
     * @param value
     * @param resultCode
     */
    public DefaultResult(T value, DefaultResultCode resultCode) {
        super();
        this.value = value;
        this.resultCode = resultCode;
    }

    /**
     *
     * 返回码
     */
    private DefaultResultCode resultCode;

    /**
     * 只用value进行构造,code总是为success
     * @param valueObject
     */
    public DefaultResult(T valueObject) {
        this(valueObject, DefaultResultCode.SUCCESS);
    }

    /**
     * 只用code进行构造,value为null
     * @param resultCode
     */
    public DefaultResult(DefaultResultCode resultCode) {
        this(null, resultCode);
    }

    public DefaultResult(DefaultResultCode resultCode, String customErrMsg) {
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
