package cn.shanxincd.plat.common.data.conver.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ConverReq {

	private String keyField;
	private String valueField;

	private String table;
	private Set<String> keys;

	public ConverReq() {

	}

	public ConverReq(String keyField, String valueField, String table, Set<String> keys) {
		this.keyField = keyField;
		this.valueField = valueField;
		this.table = table;
		this.keys = keys;
	}
}
