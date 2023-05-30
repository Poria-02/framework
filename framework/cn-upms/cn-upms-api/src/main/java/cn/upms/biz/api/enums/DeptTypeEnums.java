package cn.upms.biz.api.enums;

public enum DeptTypeEnums {
	ORGANIZATION(1, "机构"),
	DEPART(2, "科室");

	private Integer code;
	private String value;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	DeptTypeEnums(Integer code, String value) {
		this.code  = code;
		this.value = value;
	}
}
