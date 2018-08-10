package com.lambert.act.biz.act.repository;

import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActHistoryManager {
	
   public DefaultResult<Pager> findHistoricActivityInstance();

}
