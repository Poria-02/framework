package cn.shanxincd.plat.common.data.mybatis;

import cn.hutool.core.util.StrUtil;
import cn.shanxincd.plat.common.core.constant.CommonConstants;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 *
 * @author L.cm
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.debug("mybatis plus start insert fill ....");
		LocalDateTime now = LocalDateTime.now();

		// 审计字段自动填充
		fillValIfNullByName("createTime", now, metaObject, false);
		fillValIfNullByName("updateTime", now, metaObject, false);
		fillValIfNullByName("createBy", getUserName(), metaObject, false);
		fillValIfNullByName("updateBy", getUserName(), metaObject, false);

		// 删除标记自动填充
		fillValIfNullByName("delFlag", CommonConstants.STATUS_NORMAL, metaObject, false);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.debug("mybatis plus start update fill ....");
		fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
		fillValIfNullByName("updateBy", getUserName(), metaObject, true);
	}

	/**
	 * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
	 * @param fieldName 属性名
	 * @param fieldVal 属性值
	 * @param metaObject MetaObject
	 * @param isCover 是否覆盖原有值,避免更新操作手动入参
	 */
	private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
		// 0. 如果填充值为空
		if (fieldVal == null) {
			return;
		}
		// 1. 没有 get 方法
		if (!metaObject.hasSetter(fieldName)) {
			return;
		}
		// 2. 如果用户有手动设置的值
		Object userSetValue = metaObject.getValue(fieldName);
		String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
		if (StrUtil.isNotBlank(setValueStr) && !isCover) {
			return;
		}
		// 3. field 类型相同时设置
		Class<?> getterType = metaObject.getGetterType(fieldName);
		if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
			metaObject.setValue(fieldName, fieldVal);
		}
	}

	/**
	 * 获取 spring security 当前的用户名
	 * @return 当前用户名
	 */
	private String getUserName() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (Optional.ofNullable(authentication).isPresent()) {
//			return authentication.getName();
//		}
		return null;
	}

}
