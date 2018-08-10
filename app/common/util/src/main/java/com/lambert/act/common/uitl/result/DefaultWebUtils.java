package com.lambert.act.common.uitl.result;

import org.springframework.ui.ModelMap;

public class DefaultWebUtils {
	 /**
     * @param <T>
     * @param code
     */
    public static <T> void putResult2ModelMap(DefaultResult<T> result,ResultModel resultModel) {
    	
    	
    	resultModel.init(result.getValue(),result.isSuccess(),result.getMessage(),result.getResultCode().getCode());
    	
    /*	model.addAttribute("data", result.getValue());
    	model.addAttribute("success", result.isSuccess());
    	model.addAttribute("Msg", result.getMessage());*/
    }
    /**
     * 
     * 
     * @param <T>
     * @param result
     * @param model
     */
    public static <T> ModelMap putResult2ModelMap(DefaultResult<T> result) {
    	ModelMap map = new ModelMap();
    	map.put("data", result.getValue());
    	map.put("success", result.isSuccess());
    	map.put("code", result.getResultCode().getCode());
    	map.put("msg", result.getMessage());
    	return map;
    }
}
