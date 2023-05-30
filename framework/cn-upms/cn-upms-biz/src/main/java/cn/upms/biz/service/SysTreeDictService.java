package cn.upms.biz.service;

import cn.upms.biz.api.entity.SysTreeDict;
import cn.upms.biz.api.entity.SysTreeDictItem;
import cn.upms.biz.api.enums.DictionaryType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.upms.biz.api.vo.DictQueryModel;

import java.util.List;
import java.util.Optional;

/**
 * (SysTreeDict)表服务接口
 *
 * @author makejava
 * @since 2020-07-03 09:24:52
 */
 public interface SysTreeDictService extends IService<SysTreeDict> {

	/**
	 * 通过ID查询
	 * @param id
	 * @return
	 */
	 Optional<SysTreeDict> findById(String id);

	/**
	 * 通过ID查询用户字典
	 * @param id
	 * @return
	 */
	 Optional<SysTreeDict> findUserDictById(String id);


	/**
	 * 通过Code查询
	 * @param code
	 * @return
	 */
	 Optional<SysTreeDict> findByCode(String code);

	 IPage<SysTreeDict> page(DictQueryModel queryModel);

	 List<DictionaryType> getAllType();

	SysTreeDictItem saveItem(SysTreeDictItem item);

	List<SysTreeDictItem> findItemsByPid(String dictId, String pid);

	SysTreeDictItem updateItem(SysTreeDictItem item);

	Optional<SysTreeDictItem> findItem(String id);

	List<SysTreeDictItem> findItems(String dictId, String name);
}