package com.pay.platform.modules.agent.service.impl;

import com.pay.platform.modules.agent.dao.AgentRateDao;
import com.pay.platform.modules.agent.model.AgentRateListModel;
import com.pay.platform.modules.agent.model.AgentRateModel;
import com.pay.platform.modules.agent.service.AgentRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/2/5 14:15
 */
@Service
public class AgentRateServiceImpl implements AgentRateService{

    @Autowired
    private AgentRateDao agentRateDao;

    @Override
    public List<AgentRateListModel> queryAgentRateList(String AgentId) {
        return agentRateDao.queryAgentRateList(AgentId);
    }

    @Override
    public Integer addAgentRate(AgentRateModel model) {
        return agentRateDao.addAgentRate(model);
    }

    @Override
    public Integer deletAgentRate(String id) {
        return agentRateDao.deletAgentRate(id);
    }

}