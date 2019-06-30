package com.pay.platform.modules.agent.dao;

import com.pay.platform.modules.agent.model.AgentRateListModel;
import com.pay.platform.modules.agent.model.AgentRateModel;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * User:
 * DateTime: 2019/2/5 14:16
 */
@MapperScan
public interface AgentRateDao {

    /**
     * 查询代理的费率列表
     *
     * @param AgentId
     * @return
     */
    List<AgentRateListModel> queryAgentRateList(@Param("agentId") String AgentId);

    /**
     * 新增代理费率
     *
     * @param model
     * @return
     */
    Integer addAgentRate(AgentRateModel model);


    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer deletAgentRate(String id);

    double queryParentAgentRate(@Param("agentId") String agentId, @Param("channelId") String channelId);
}