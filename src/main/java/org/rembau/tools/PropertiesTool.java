package org.rembau.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesTool {
    private final static Logger logger = LoggerFactory.getLogger(PropertiesTool.class);

    public static Properties getParams(String file){
		 Properties params = new Properties();
	        try {
	        	params.load(new FileInputStream(file));
	        } catch (IOException e) {
                System.out.println(e.getMessage());
                logger.error("", e);
            }
		return params;
	}
	public static void main(String[] args) {

	}

}
