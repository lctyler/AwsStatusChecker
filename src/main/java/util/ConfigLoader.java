package util;

import java.io.IOException;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import checker.AwsStatusCheckerConfig;

public class ConfigLoader
{
	
	
	public static AwsStatusCheckerConfig loadStatusCheckerConfigFromFile(String path) throws JsonParseException, JsonMappingException, IOException
	{
		return loadStatusCheckerConfig(IOUtils.toString(ConfigLoader.class.getClassLoader().getResourceAsStream(path)));
	}
	
	
	public static AwsStatusCheckerConfig loadStatusCheckerConfig(String json) throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readValue(json, AwsStatusCheckerConfig.class);
	}
}
