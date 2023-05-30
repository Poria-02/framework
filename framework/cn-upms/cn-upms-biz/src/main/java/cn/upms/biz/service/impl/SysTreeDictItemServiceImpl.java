package cn.upms.biz.service.impl;

import cn.upms.biz.mapper.SysTreeDictItemMapper;
import cn.upms.biz.service.SysTreeDictService;
import cn.upms.biz.api.entity.SysTreeDict;
import cn.upms.biz.api.entity.SysTreeDictItem;
import cn.upms.biz.service.SysTreeDictItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.shanxincd.plat.common.core.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * (SysTreeDictItem)表服务实现类
 *
 * @author makejava
 * @since 2020-07-03 09:24:56
 */
@Service
public class SysTreeDictItemServiceImpl extends ServiceImpl<SysTreeDictItemMapper, SysTreeDictItem> implements SysTreeDictItemService {

	@Autowired
	private SysTreeDictService treeDictService;

	/**
	 * 查询一级科室
	 *
	 * @param value
	 * @return
	 */
	@Override
	public SysTreeDictItem getParentDictItem(String dictCode,String value) {

		Optional<SysTreeDict> optional = treeDictService.findByCode(dictCode);
		Assert.isTrue(optional.isPresent(),"字典不存在{}",dictCode);
		String dictId = optional.get().getId();
		SysTreeDictItem sysTreeDictItem = this.getOne(new LambdaQueryWrapper<SysTreeDictItem>()
				.eq(SysTreeDictItem::getDictId,dictId).eq(SysTreeDictItem::getValue,value));
		Assert.notNull(sysTreeDictItem,"字典项不存在");

		//查询上级科室
		SysTreeDictItem parentItem = this.getOne(new LambdaQueryWrapper<SysTreeDictItem>()
				.eq(SysTreeDictItem::getDictId,dictId).eq(SysTreeDictItem::getId,sysTreeDictItem.getPid()));

		Assert.notNull(parentItem,"上级字典科室不存在");
		return parentItem;
	}


}