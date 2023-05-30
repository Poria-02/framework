package cn.upms.biz.service.impl;

import cn.common.core.constant.CacheConstants;
import cn.upms.api.entity.SysOauthClientDetails;
import cn.upms.biz.mapper.SysOauthClientDetailsMapper;
import cn.upms.biz.service.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {
    /**
     * 通过ID删除客户端
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId")
    public Boolean removeByClientId(String clientId) {
        return this.remove(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId));
    }

    /**
     * 根据客户端信息
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
    public Boolean updateClientById(SysOauthClientDetails clientDetails) {
        return this.updateById(clientDetails);
    }


    /**
     * 清除客户端缓存
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
    public void clearClientCache() {

    }

}
