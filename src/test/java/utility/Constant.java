package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constant {
    public static final String DEFAULT_TEST_ENV = "qa";
    public static final String DEFAULT_BROWSER = "chrome";
    public static String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : DEFAULT_BROWSER;
    public static String testEnv;
    public static String frontBaseUrl;
    public static String apiBaseUrl;
    public static String ipInfoApiUrl;
    public static String ipInfoToken;
    public static Properties TestDataProperties;

    private static String getTestEnv() {
        String testPropertyValue = System.getProperty("testEnv");
        String testEnvValue = System.getenv("testEnv");

        // First priority will be from the command line then environment value
        if (testPropertyValue == null && testEnvValue == null) {
            return DEFAULT_TEST_ENV;
        } else if (testPropertyValue != null) {
            return testPropertyValue;
        } else if (testEnvValue != null) {
            return testEnvValue;
        } else {
            return DEFAULT_TEST_ENV;
        }
    }

    public static void setUpTestEnvData() throws IOException {
        testEnv = getTestEnv();
        String filePath = "src/test/resources/config/";
        TestDataProperties = new Properties();
        File envFile = new File(filePath + testEnv + ".properties");
        FileInputStream fileInputStream = new FileInputStream(envFile);
        TestDataProperties.load(fileInputStream);
        fileInputStream.close();

        ipInfoApiUrl = TestDataProperties.getProperty("ip_info_api_url");
        ipInfoToken = TestDataProperties.getProperty("ip_info_token");
        apiBaseUrl = TestDataProperties.getProperty("reqres_api_url");
        frontBaseUrl = TestDataProperties.getProperty("front_base_url");
    }

}
