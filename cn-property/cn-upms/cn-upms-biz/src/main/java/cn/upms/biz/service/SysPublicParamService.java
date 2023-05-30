package cn.upms.biz.service;

import cn.common.core.utils.R;
import cn.upms.api.entity.SysPublicParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 公共参数配置
 */
public interface SysPublicParamService extends IService<SysPublicParam> {

    /**
     * 通过key查询公共参数指定值
     */
    String getSysPublicParamKeyToValue(String publicKey);

    /**
     * 更新参数
     */
    R updateParam(SysPublicParam sysPublicParam);

    /**
     * 删除参数
     */
    R removeParam(Long publicId);
}
