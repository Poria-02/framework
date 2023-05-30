package cn.upms.biz.controller;



import cn.shanxincd.plat.common.core.constant.CacheConstants;
import cn.shanxincd.plat.common.core.util.R;
import cn.upms.biz.api.entity.SysTag;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.upms.biz.service.SysTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import cn.shanxincd.plat.common.log.annotation.SysLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

/**
 * 标签分类(SysTag)表控制层
 *
 * @author makejava
 * @since 2020-08-27 14:35:04
 */
@RestController
@Tag(name="标签分类管理")
@RequestMapping("/tag")
public class SysTagController {
    
    @Resource
    private SysTagService sysTagService;

    
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public R selectAll(Page<SysTag> page, SysTag sysTag) {
        return R.ok(sysTagService.page(page, new QueryWrapper<>(sysTag)));
    }

	@Operation(summary = "列表查询", description = "列表查询")
	@GetMapping("/list")
	public R<List<SysTag>> list() {
		return R.ok(sysTagService.list());
	}
    
    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{id}")
    public R<SysTag> selectOne(@PathVariable String id) {
        return R.ok(sysTagService.getById(id));
    }

   
    @PostMapping
    @SysLog("新增标签分类" )
    @Operation(summary = "新增标签分类", description = "新增标签分类，权限标识 admin_tag_add")
    @PreAuthorize("@pms.hasPermission('admin_tag_add')" )
    public R<SysTag> insert(@RequestBody @Validated SysTag sysTag) {
        return R.ok(sysTagService.saveTag(sysTag));
    }

   
    @PutMapping
    @SysLog("修改标签分类" )
    @Operation(summary = "修改标签分类", description = "修改标签分类，权限标识 admin_tag_edit")
	@CacheEvict(value = CacheConstants.TAG_ITEMS, allEntries = true)
	@PreAuthorize("@pms.hasPermission('admin_tag_edit')" )
    public R update(@RequestBody @Validated SysTag sysTag) {
        sysTagService.updateById(sysTag);
        return R.ok();
    }

    
    @DeleteMapping("{id}")
    @SysLog("通过id删除标签分类" )
	@CacheEvict(value = CacheConstants.TAG_ITEMS, allEntries = true)
	@Operation(summary = "通过ID删除标签分类", description = "通过ID删除标签分类,权限标识admin_tag_del ")
    @PreAuthorize("@pms.hasPermission('admin_tag_del')" )
    public R delete(@PathVariable String id) {
        sysTagService.removeById(id);
        return R.ok();
    }

}