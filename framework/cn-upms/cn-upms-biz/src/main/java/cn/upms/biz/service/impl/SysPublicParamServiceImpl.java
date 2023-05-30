package cn.upms.biz.service.impl;

import cn.upms.biz.mapper.SysPublicParamMapper;
import cn.upms.biz.api.entity.SysPublicParam;
import cn.upms.biz.service.SysPublicParamService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.shanxincd.plat.common.core.constant.CacheConstants;
import cn.shanxincd.plat.common.core.constant.enums.DictTypeEnum;
import cn.shanxincd.plat.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 公共参数配置
 */
@Service
@AllArgsConstructor
public class SysPublicParamServiceImpl extends ServiceImpl<SysPublicParamMapper, SysPublicParam> implements SysPublicParamService {
	@Override
	@Cacheable(value = CacheConstants.PARAMS_DETAILS, key = "#publicKey", unless = "#result == null ")
	public String getSysPublicParamKeyToValue(String publicKey) {
		SysPublicParam sysPublicParam = this.baseMapper.selectOne(Wrappers.<SysPublicParam>lambdaQuery().eq(SysPublicParam::getPublicKey, publicKey));
		if (sysPublicParam != null) {
			return sysPublicParam.getPublicValue();
		}

		return null;
	}

	/**
	 * 更新参数
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, key = "#sysPublicParam.publicKey")
	public R updateParam(SysPublicParam sysPublicParam) {
		SysPublicParam param = this.getById(sysPublicParam.getPublicId());
		//系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystem())) {
			return R.failed("系统内置参数不能删除");
		}

		return R.ok(this.updateById(sysPublicParam));
	}

	/**
	 * 删除参数
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
	public R removeParam(Long publicId) {
		SysPublicParam param = this.getById(publicId);
		//系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystem())) {
			return R.failed("系统内置参数不能删除");
		}

		return R.ok(this.removeById(publicId));
	}
}
