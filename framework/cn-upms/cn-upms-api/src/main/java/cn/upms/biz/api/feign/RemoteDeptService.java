package cn.upms.biz.api.feign;

import cn.upms.biz.api.entity.SysDept;
import cn.shanxincd.plat.common.core.constant.ServiceNameConstants;
import cn.shanxincd.plat.common.core.util.R;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "remoteDeptService",url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteDeptService {

	/**
	 * 添加机构
	 * @param sysDept
	 * @return
	 */
	@PostMapping("/dept")
	public R<SysDept> save(@Valid @RequestBody SysDept sysDept);

	/**
	 * 修改机构
	 * @param sysDept
	 * @return
	 */
	@PutMapping("/dept")
	public R update(@Valid @RequestBody SysDept sysDept);

	/**
	 * 通过ID查询
	 */
	@GetMapping("/dept/{id}")
	public R<SysDept> getById(@PathVariable("id") Integer id);

	/**
	 * 根据ID逻辑删除
	 */
	@DeleteMapping("/dept/{id}")
	public R removeById(@PathVariable("id") Integer id);
}
