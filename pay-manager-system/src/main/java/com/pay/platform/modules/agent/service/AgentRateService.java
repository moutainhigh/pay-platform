package com.pay.platform.modules.agent.service;

import com.pay.platform.modules.agent.model.AgentRateListModel;
import com.pay.platform.modules.agent.model.AgentRateModel;
import java.util.List;

public interface AgentRateService {

    /**
     * 查询代理的费率列表
     * @param AgentId
     * @return
     */
    List<AgentRateListModel> queryAgentRateList(String AgentId);

    /**
     * 新增代理费率
     * @param model
     * @return
     */
    Integer addAgentRate(AgentRateModel model);


    /**
     * 删除
     * @param id
     * @return
     */
    Integer deletAgentRate(String id);

    double queryParentAgentRate(String agentId, String channelId);

}
