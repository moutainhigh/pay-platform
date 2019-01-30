package com.pay.platform.modules.agent.dao;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.agent.model.AgentModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface AgentDao {

    /**
     * 分页查询代理列表
     *
     * @param agent
     * @return
     */
    List<AgentModel> queryAgentList(AgentModel agent);

    /**
     * 根据id查询代理信息
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

    List<Map<String,Object>> queryAgentIdAndNameList(@Param("agentId") String agentId);

}