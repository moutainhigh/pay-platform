package com.pay.platform.modules.codeTrader.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import com.pay.platform.modules.codeTrader.model.CodeTraderModel;
import org.apache.ibatis.annotations.Param;

/**
 * User: zjt
 * DateTime: 2016/10/7 20:15
 */
public interface CodeTraderService {

    /**
     * 分页查询码商
     *
     * @param codeTrader
     * @return
     */
    PageInfo<CodeTraderModel> queryCodeTraderList(CodeTraderModel codeTrader);

    /**
     * 根据id查询码商
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
    Integer addCodeTrader(String account , String password ,CodeTraderModel codeTrader);

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

    PageInfo<Map>  queryAllCodeTraderByMerchantId(String merchantId);

    int addMerchantCodeTrader(String codeTraderId, String merchantId);

    int deleteMerchantCodeTrader(String codeTraderId, String merchantId);

    List<String> queryMerchantIdCodeTraderId(String codeTraderId);

}