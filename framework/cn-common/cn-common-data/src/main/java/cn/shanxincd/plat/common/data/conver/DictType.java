package cn.shanxincd.plat.common.data.conver;

public enum DictType {

	ITEM (1,"普通列表字典"),
	TREE (2,"分级字典"),

;
	DictType(int code,String value){

		this.code = code;
		this.value = value;
	}

	private int code ;

	private String value;

}
