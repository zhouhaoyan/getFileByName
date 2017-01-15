package com.infohold;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by zhouahoyan on 2017/1/12.
 * 功能：读取配置文件
 */


public class LoadProperties {
    private static Logger logger=Logger.getLogger("test");
    private static final String FILE_NAME="app.properties";
    private static Properties properties;


    /**
     * 从classpath路径下加载配置参数
     */
    public static Properties loadProperties() {

        InputStream in = null;
        try {
            in = LoadProperties.class.getClassLoader()
                    .getResourceAsStream(FILE_NAME);
            if (null != in) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        in, "utf-8"));
                properties = new Properties();
                try {
                    properties.load(bf);
                } catch (IOException e) {
                    throw e;
                }
            } else {
                System.out.println(FILE_NAME + "属性文件未能在classpath指定的目录下 "+LoadProperties.class.getClassLoader().getResource("").getPath()+" 找到!");
                logger.info(FILE_NAME + "属性文件未能在classpath指定的目录下 "+LoadProperties.class.getClassLoader().getResource("").getPath()+" 找到!");
            }
        } catch (IOException e) {
            logger.error(""+e.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return properties;
    }

}
