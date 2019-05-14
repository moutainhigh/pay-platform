package com.pay.platform.modules.codeTrader.dao;

import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.codeTrader.model.CodeTraderModel;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:16
 */
@MapperScan
public interface CodeTraderDao {

    /**
     * 分页查询码商列表
     *
     * @param codeTrader
     * @return
     */
    List<CodeTraderModel> queryCodeTraderList(CodeTraderModel codeTrader);

    /**
     * 根据id查询码商信息
     *
     * @param id
     * @return
     */
    CodeTraderModel queryCodeTraderById(String id);

    /**
     * 新增码商
     *
     * @param codeTrader
     * @return
     */
    Integer addCodeTrader(CodeTraderModel codeTrader);

    /**
     * 删除码商
     *
     * @param ids
     * @return
     */
    Integer deleteCodeTrader(String[] ids);

    /**
     * 逻辑删除码商
     *
     * @param ids
     * @return
     */
    Integer deleteCodeTraderByLogic(String[] ids);

    /**
     * 修改码商
     *
     * @param codeTrader
     * @return
     */
    Integer updateCodeTrader(CodeTraderModel codeTrader);

}