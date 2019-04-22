package restApiAutomation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Common {


    private static Properties pro = new Properties();

    public static String getPropertyValue(String propertyKey) throws IOException {
        String propertyValue= null;
        try{
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("application.properties");
            pro.load(stream);
            propertyValue =  pro.getProperty(propertyKey);
        } catch(Exception e){
            e.getMessage();
        }
        return propertyValue;
    }
    public static String getBaseUrl() {
        String baseUrl = null;

        try {
            baseUrl = restApiAutomation.Common.getPropertyValue("baseUrl");
        }catch (Exception e) {
            e.getMessage();
        }
       return baseUrl;
    }

}
