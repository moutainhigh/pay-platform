package com.pay.platform.modules.agent.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.agent.model.AgentModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface AgentService {

    /**
     * 分页查询代理
     *
     * @param agent
     * @return
     */
    PageInfo<AgentModel> queryAgentList(AgentModel agent);

    /**
     * 根据id查询代理
     *
     * @param id
     * @return
     */
    AgentModel queryAgentById(String id);

    /**
     * 新增代理
     *
     * @param agent
     * @return
     */
    Integer addAgent(AgentModel agent);

    /**
     * 删除代理
     *
     * @param ids
     * @return
     */
    Integer deleteAgent(String[] ids);

    /**
     * 逻辑删除代理
     *
     * @param ids
     * @return
     */
    Integer deleteAgentByLogic(String[] ids);

    /**
     * 修改代理
     *
     * @param agent
     * @return
     */
    Integer updateAgent(AgentModel agent);

    List<Map<String,Object>> queryAgentIdAndNameList(String agentId);

}