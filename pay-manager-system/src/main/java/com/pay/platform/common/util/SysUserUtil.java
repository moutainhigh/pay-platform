package com.pay.platform.common.util;

import com.pay.platform.common.enums.RoleCodeEnum;
import com.pay.platform.modules.sysmgr.user.model.UserModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * User: zjt
 * DateTime: 2019/1/18 21:47
 * <p>
 * 系统用户相关工具操作
 */
public class SysUserUtil {

    /**
     * 判断是否为超级管理员
     *
     * @param userModel
     * @return
     */
    public static boolean isAdminRole(UserModel userModel) {

        boolean flag = false;

        if ("admin".equalsIgnoreCase(userModel.getAccount())) {

            for (GrantedAuthority grantedAuthorityList : userModel.getAuthorities()) {
                if (RoleCodeEnum.ROLE_ADMIN.getCode().equalsIgnoreCase(grantedAuthorityList.getAuthority())) {
                    flag = true;
                }
            }

        }

        return flag;

    }

    /**
     * 判断是否为代理管理员
     *
     * @param userModel
     * @return
     */
    public static boolean isAgentRole(UserModel userModel) {

        boolean flag = false;

        if (StringUtil.isNotEmpty(userModel.getAgentId())) {

            for (GrantedAuthority grantedAuthorityList : userModel.getAuthorities()) {
                if (RoleCodeEnum.ROLE_AGENT.getCode().equalsIgnoreCase(grantedAuthorityList.getAuthority())) {
                    flag = true;
                }
            }

        }

        return flag;

    }

    /**
     * 判断是否为商户管理员
     *
     * @param userModel
     * @return
     */
    public static boolean isMerchantRole(UserModel userModel) {

        boolean flag = false;

        if (StringUtil.isNotEmpty(userModel.getMerchantId())) {

            for (GrantedAuthority grantedAuthorityList : userModel.getAuthorities()) {
                if (RoleCodeEnum.ROLE_MERCHANT.getCode().equalsIgnoreCase(grantedAuthorityList.getAuthority())) {
                    flag = true;
                }
            }

        }

        return flag;

    }

    /**
     * 判断是否为超级管理员
     *
     * @param userModel
     * @return
     */
    public static String getRoleCode(UserModel userModel) {

        if (isAdminRole(userModel)) {
            return RoleCodeEnum.ROLE_ADMIN.getCode();
        } else if (isAgentRole(userModel)) {
            return RoleCodeEnum.ROLE_AGENT.getCode();
        }
        else if (isMerchantRole(userModel)) {
            return RoleCodeEnum.ROLE_MERCHANT.getCode();
        }

        return null;

    }

}