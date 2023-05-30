package cn.upms.api.feign;

import cn.common.core.constant.SecurityConstants;
import cn.common.core.constant.ServiceNameConstants;
import cn.common.core.utils.R;
import cn.upms.api.entity.SysDictItem;
import cn.upms.api.vo.TreeDictItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "remoteDictService", url = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteDictService {


    @GetMapping("/treedict/item/parent/{dictCode}/{value}")
    R<TreeDictItemVo> getParentDictItem(@PathVariable("dictCode") String dictCode,
                                        @PathVariable("value") String value,
                                        @RequestHeader(SecurityConstants.FROM) String from);


    @GetMapping("/treedict/item/list/{code}")
    R<List<TreeDictItemVo>> dictItems(@PathVariable("code") String code);

    @GetMapping("/dict/item/{dictCode}/{value}")
    R<SysDictItem> findDictItem(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value, @RequestHeader(SecurityConstants.FROM) String from);
}
