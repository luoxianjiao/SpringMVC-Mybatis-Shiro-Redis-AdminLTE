package cn.itcast.ssm.controller.converter;

import org.springframework.core.convert.converter.Converter;

public class CustomSpaceConverter implements Converter<String, String>{

	@Override
	public String convert(String source) {
		// TODO Auto-generated method stub
		try {
			String targetStr = source.replace(" ", "");
			return targetStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
