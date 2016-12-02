package com.mycompany.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyParser {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyParser.class);
    private Properties props = new Properties();

    public String getProperty(String key) {
        String value = null;
	try {
	    value = props.get(key).toString();
	} catch (Exception e) {
	    LOG.error("No property found for \"" + key + "\"");
	    throw e;
	}

	return value;
    }

    public List<String> getPropertyAsList(String key) {
	List<String> list = new ArrayList<>();
	for (String seed : getProperty(key).split(",")) {
	    list.add(seed);
	}

	LOG.info("Value" + String.valueOf(list));

	return list;
    }

    public void parsePropsFile(String propFileName) throws IOException {
	InputStream inputStream = new FileInputStream(new File(propFileName).getAbsolutePath());
	props.load(inputStream);
    }

    public Integer getPropertyAsInt(String key) {
	return Integer.valueOf(getProperty(key));
    }

    public Boolean getPropertyAsBoolean(String key) {
	return Boolean.valueOf(getProperty(key));
    }

    public double getPropertyAsDouble(String key) {
	return Double.valueOf(getProperty(key));
    }

}
