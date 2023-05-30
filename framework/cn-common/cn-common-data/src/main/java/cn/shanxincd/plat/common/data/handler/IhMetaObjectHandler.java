package cn.shanxincd.plat.common.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 注入公共字段
 */
@Slf4j
public class IhMetaObjectHandler implements MetaObjectHandler {

    //插入操作，注入租户，创建人，创建部门，创建时间
    @Override
    public void insertFill(MetaObject metaObject) {

		strictInsertFill(metaObject,"createBy",String.class,getCreateBy());
		strictInsertFill(metaObject,"createTime",Date.class,new Date());
		strictInsertFill(metaObject,"isDelete",Integer.class,0);

	}

    //插入操作，注入修改人，修改时间
    @Override
    public void updateFill(MetaObject metaObject) {

		//将需要填充的字段置空
		if(metaObject.hasSetter("updateTime")){
			metaObject.setValue("updateTime", null);
		}
		if(metaObject.hasSetter("updateBy")){
			metaObject.setValue("updateBy", null);
		}


		strictUpdateFill(metaObject,"updateBy",String.class,getCreateBy());
		strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }


    private String getCreateBy(){
//    	try{
//			if(SecurityUtils.getUser() != null){
//				if(StrUtil.isNotBlank(SecurityUtils.getStaffId())){
//					return SecurityUtils.getStaffId();
//				}
//				return SecurityUtils.getSId();
//			}
//		}catch (Exception e){
//			log.error("获取当前登陆用户失败");
//		}

		return "";
	}

}
