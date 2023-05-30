package cn.shanxincd.plat.common.data.conver;

import cn.hutool.core.util.StrUtil;
import cn.shanxincd.plat.common.core.util.R;
import cn.shanxincd.plat.common.data.conver.dao.ConverDao;
import cn.shanxincd.plat.common.data.conver.model.ConverReq;
import cn.shanxincd.plat.common.data.conver.model.ConverResult;
import cn.shanxincd.plat.common.data.mybatis.MybatisPlusConfiguration;


import cn.shanxincd.plat.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Hidden
@RestController
@AutoConfigureAfter(MybatisPlusConfiguration.class)
public class ConvertController {

    @Autowired(required = false)
    private ConverDao converDao;

    @Inner
    @PostMapping("/inner/converData")
    public R<Map> convertDate(@RequestBody ConverReq req){
        List<ConverResult> list = converDao.convertData(req.getKeys(),req.getTable(),req.getKeyField(),req.getValueField());
		if (CollectionUtils.isEmpty(list)) {
			return R.ok();
		}

		Map map =  list.stream().filter(t-> StrUtil.isNotBlank(t.getValue())).collect(Collectors.toMap(ConverResult::getKey, ConverResult::getValue,(k1, k2)->k1));
    	return R.ok(map);
    }
}
