package com.hbs.keywords;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.Map;

public class YamlInformationProvider {

	Map<String, Object> userInfoMap;

	public YamlInformationProvider() {
	}

	
	public YamlInformationProvider(Map<String, Object> userInfoMap) {
		this.userInfoMap = userInfoMap;
	}
	
	public String getApplicationURL(String token){
		return getYamlValue(token+"."+"url");
	}
	
	public String getTitleName(String titleToken){
		return getYamlValue("titles"+"."+titleToken);
	}
}
