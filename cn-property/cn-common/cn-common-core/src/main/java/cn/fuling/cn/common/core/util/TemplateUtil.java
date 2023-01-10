package cn.fuling.cn.common.core.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtil {

    private static final String REGEX = "\\$\\{(.+?)\\}";


    /**
     * 根据参数集合替换${}内的模版变量<br>
     * 模版为空时返回null
     * 参数集合为空时,直接返回模版
     * 参数集合中不包含的模版变量替换成空字符串
     * @param template 模版
     * @param param 参数集
     * @return
     */
    public static String process(String template,Map<String,String> param){

        if(StrUtil.isBlank(template)){
            return null;
        }

        if(param == null || CollectionUtils.isEmpty(param)){
            return template;
        }

        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            String name = matcher.group(1);
            String value = param.get(name);
            if (value == null) {
                value = "";
            }
            matcher.appendReplacement(sb,value);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

	/**
	 * 根据参数集合替换${}内的模版变量<br>
	 * 模版为空时返回null
	 * 参数集合为空时,直接返回模版
	 * 参数集合中不包含的模版变量替换成空字符串
	 * @param template 模版
	 * @param regex 模版
	 * @param param 参数集
	 * @return
	 */
	public static String process(String template,String regex,Map<String,String> param){

		if(StrUtil.isBlank(template)){
			return null;
		}

		if(param == null || CollectionUtils.isEmpty(param)){
			return template;
		}

		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(template);
		while (matcher.find()) {
			String name = matcher.group(1);
			String value = param.get(name);
			if (value == null) {
				value = "";
			}
			matcher.appendReplacement(sb,value);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
