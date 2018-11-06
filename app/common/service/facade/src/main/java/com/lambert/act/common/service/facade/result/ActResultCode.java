package com.lambert.act.common.service.facade.result;

import java.io.Serializable;

public class ActResultCode implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 481855398964178212L;

	/**
     * 返回�?
     */
    private int code = RESULT_CODE_SUCCESS_DEFAULT;
    public int getCode() {
        return code;
    }

    /**
     * 获取返回消息�?
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 消息�?
     */
    private String message = RESULT_REASON_SUCCESS_DEFAULT;

    private static final int RESULT_CODE_SUCCESS_DEFAULT = 0;
    private static final String RESULT_REASON_SUCCESS_DEFAULT = "success";

    private static final int RESULT_CODE_ILLEGAL_PARAMS_DEFAULT = -1;//参数错误
    private static final int RESULT_CODE_SERVER_EXCEPTION = -2;
    private static final int RESULT_CODE_STATUS_ERROR = -3;
    //�?要登�?
    private static final int RESULT_CODE_LOGIN_REQUIRED = -4;

    //权限不足
    private static final int RESULT_CODE_ACCESS_DENIE = -5;
    //数据重复
    private static final int RESULT_CODE_DUPLICATE_DATA = -6;
    //数据重复
    private static final int RESULT_CODE_NOT_FOUND_DATA = -7;
    //数据重复
    private static final int RESULT_CODE_CREAT_PROCESS_FAILE = -8;

    //权限不足,并且�?要页面重定向到没有权限的页面
    public static final int RESULT_CODE_DENIE_REDIRECT = -10101;
    /**
     * success
     */
    public static final ActResultCode SUCCESS = new ActResultCode(RESULT_CODE_SUCCESS_DEFAULT, RESULT_REASON_SUCCESS_DEFAULT);
    /**
     * 参数不对
     */
    public static final ActResultCode ILLEGAL_PARAMS = new ActResultCode(RESULT_CODE_ILLEGAL_PARAMS_DEFAULT, "参数错误");
       /**
        * 后端异常
        */
    public static final ActResultCode SERVER_EXCEPTION = new ActResultCode(RESULT_CODE_SERVER_EXCEPTION,"服务端异�?");

    /**
     * 状�?�异�?
     */
    public static final ActResultCode SATTUS_ERROR = new ActResultCode(RESULT_CODE_STATUS_ERROR, "状�?�异�?");

    /**
     * �?要登�?
     */
    public static final ActResultCode LOGIN_REQIRE = new ActResultCode(RESULT_CODE_LOGIN_REQUIRED, "�?要登�?");

    /**
     * 权限不足
     */
    public static final ActResultCode ACCESS_DENIE = new ActResultCode(RESULT_CODE_ACCESS_DENIE, "权限不足,禁止访问");
    /**
     * 数据已经存在
     */
    public static final ActResultCode DUPLICATE_DATA = new ActResultCode(RESULT_CODE_DUPLICATE_DATA, "数据已经存在，保存失�?");
    /**
     * 数据已经存在
     */
    public static final ActResultCode CREAT_PROCESS_FAILE = new ActResultCode(RESULT_CODE_CREAT_PROCESS_FAILE, "流程创建失败");
    /**
     *
     * @param code
     * @param message
     */
    public ActResultCode(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }


    /**
     *
     *
     * @param message
     * @return
     */
    public ActResultCode clone(String message){
        return new ActResultCode(this.code, message);
    }

    /**
     * return code >=0
     *
     * @return
     */
    public boolean isSuccess(){
       return this.code >= 0;
   }


    /**
     *
     * 为了前端json序列�?,此方法不应该被直接调�?
     *
     */
    @Deprecated
    public void setCode(int code) {
        this.code = code;
    }

    /**
     *
     * 为了前端Json序列�?,此方法不应该直接被调�?
     */
    @Deprecated
    public void setMessage(String message) {
        this.message = message;
    }

}
