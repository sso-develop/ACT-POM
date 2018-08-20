package com.lambert.act.biz.act.repository;

import java.util.List;

import com.lambert.act.biz.act.repository.model.HistoricActivityInstanceModel;
import com.lambert.act.biz.act.repository.query.HistoricActivityInstanceQueryObj;
import com.lambert.act.common.uitl.result.DefaultResult;
import com.lambert.act.common.uitl.result.Pager;

public interface ActHistoryManager {
	
   public DefaultResult<Pager> findHistoricActivityInstanceByPager(HistoricActivityInstanceQueryObj queryObj);
   
   public DefaultResult<List<HistoricActivityInstanceModel>> findHistoricActivityInstance(HistoricActivityInstanceQueryObj queryObj);

}
