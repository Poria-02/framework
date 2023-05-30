package cn.upms.biz.service;

import cn.upms.api.entity.SysOauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {
    /**
     * 通过ID删除客户端
     *
     * @param clientId 客户端ID
     */
    Boolean removeByClientId(String clientId);

    /**
     * 根据客户端信息
     *
     * @param clientDetails 客户端详情
     */
    Boolean updateClientById(SysOauthClientDetails clientDetails);

    void clearClientCache();
}
