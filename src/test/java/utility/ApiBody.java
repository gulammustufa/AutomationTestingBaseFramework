package utility;

public class ApiBody {
    public static String getCreateUserBody(String email, String password) {
        return String.format("""
                {
                    "email": "%1$s",
                    "password": "%2$s"
                }
            """.stripIndent(), email, password).stripIndent();
    }
}
