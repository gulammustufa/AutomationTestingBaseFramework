package utility;

public class Constant {
    public static String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : "chrome";
    public static String testingEnv = System.getProperty("server") != null ? System.getProperty("server") : "demo";
    public static final String DEMO_ADMIN_URL = "https://www.your-demo-admin-url.com/";
    public static final String PRE_PROD_ADMIN_URL = "https://www.your-prod-admin-url.com/";
    public static final String LIVE_ADMIN_URL = "https://mpower.pidilite.com/";
    public static final String API_BASE_URL = "https://reqres.in/api";
    public static final String ipInfoUrl = "https://ipinfo.io/";
    // Add your ipinfo.io website token Ex. Bearer tokenAbcd
    public static final String ipInfoToken = "Bearer yourToken";

    public static String getAdminPanelUrl() {
        String adminUrl = switch (testingEnv) {
            case "demo" -> DEMO_ADMIN_URL;
            case "ppe" -> PRE_PROD_ADMIN_URL;
            case "live" -> LIVE_ADMIN_URL;
            default -> null;
        };
        return adminUrl;
    }
}
