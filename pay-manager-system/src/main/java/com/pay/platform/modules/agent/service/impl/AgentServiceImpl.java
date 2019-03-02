package com.pay.platform.modules.agent.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.pagehelper.PageInfo;
import com.pay.platform.common.enums.RoleCodeEnum;
import com.pay.platform.common.util.BuildNumberUtils;
import com.pay.platform.modules.merchant.dao.MerchantDao;
import com.pay.platform.modules.sysmgr.role.dao.RoleDao;
import com.pay.platform.modules.sysmgr.role.model.RoleModel;
import com.pay.platform.modules.sysmgr.user.dao.UserDao;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import com.pay.platform.modules.sysmgr.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pay.platform.modules.agent.model.AgentModel;
import com.pay.platform.modules.agent.service.AgentService;
import com.pay.platform.modules.agent.dao.AgentDao;
import sun.management.resources.agent;


/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Override
    public PageInfo<AgentModel> queryAgentList(AgentModel agent) {
        return new PageInfo(agentDao.queryAgentList(agent));
    }

    @Override
    public AgentModel queryAgentById(String id) {
        return agentDao.queryAgentById(id);
    }

    @Override
    public Integer addAgent(AgentModel agent) {

        //生产商家编号
        String agentNo = BuildNumberUtils.getMerchantNo();
        agent.setId(UUID.randomUUID().toString());
        agent.setAgentNo(agentNo);

        //1, 添加代理信息
        int count =  agentDao.addAgent(agent);

        //2, 为代理添加账号,并绑定代理ID
        UserModel userModel = new UserModel();
        userModel.setAccount(agent.getAgentNo());
        userModel.setPassword(agent.getAgentNo());      //初始化密码,跟账号一样,商家首次登陆后续配置,并修改提现密码
        userModel.setAgentId(agent.getId());
        userModel.setPhone(agent.getPhone());
        userModel.setNickname(agent.getAgentName());
        count += userService.addUser(userModel);

        //3, 为账号授予代理管理员角色
        String userId = userModel.getId();
        RoleModel roleModel = roleDao.queryRoleByRoleCode(RoleCodeEnum.ROLE_AGENT.getCode());
        count += userService.grantRole(userId, roleModel.getId().split(","));

        //4, 生成资金账户信息
        count += userDao.addAccountAmountInfo(userId, agent.getId() , null);

        return count;
    }

    @Override
    public Integer deleteAgent(String[] ids) {
        return agentDao.deleteAgent(ids);
    }

    @Override
    public Integer deleteAgentByLogic(String[] ids) {
        int count = 0;

        count += agentDao.deleteAgentByLogic(ids);
        count += userDao.deleteUserByAgentIdOfLogic(ids);

        return count;
    }

    @Override
    public Integer updateAgent(AgentModel agent) {
        return agentDao.updateAgent(agent);
    }

    @Override
    public List<Map<String, Object>> queryAgentIdAndNameList(String agentId) {
        return agentDao.queryAgentIdAndNameList(agentId);
    }

}