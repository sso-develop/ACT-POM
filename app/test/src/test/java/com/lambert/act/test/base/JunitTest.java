package com.lambert.act.test.base;

import java.io.FileNotFoundException;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/biz-shared.xml","classpath:spring/common-dal.xml"})
@ContextConfiguration(locations = {
//		"classpath:spring/biz-shared.xml",
//		"classpath:spring/biz-feedback.xml",
		"classpath:spring/common-dal.xml"
//		"classpath:spring/biz-service-impl.xml"})
public abstract class JunitTest {
	
	  static {  
	     /*   try {  
	            Log4jConfigurer.initLogging("classpath:config/log4j.properties");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  */
	    }

}
