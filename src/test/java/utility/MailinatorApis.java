package utility;

import com.jayway.jsonpath.JsonPath;
import com.steps.cucumber.BaseSteps;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MailinatorApis extends BaseSteps {
    public String getMessageId(String subjectName, String emailId) throws InterruptedException {
        String messageId = null;
        for (int i = 0; i < 10; i++) {
            Response inboxList = given()
                    .header("Authorization", Constant.MAILINATOR_API_TOKEN)
                    .when()
                    .get(Constant.MAILINATOR_API_URL + Constant.MAILINATOR_DOMAIN + "/inboxes?sort=descending");
            log("Inbox API Response: " + inboxList.asPrettyString());
            try {
                messageId = inboxList.path("msgs.find {it.subject == '" + subjectName + "' && it.to == '" + emailId + "' && it.seconds_ago < 6000 }.id");
            } catch (Exception e) {
                log("Inbox Api Exception Found: " + e);
            }

            if (messageId != null) {
                break;
            }
//            Thread.sleep(10000);
        }

        log("Message ID: " + messageId);
        return messageId;
    }

    public String getPasswordLink(String messageId) throws InterruptedException {
        String passwordLink = null;
        for (int i = 0; i < 5; i++) {
            try {
                Response getMessage = given()
                        .header("Authorization", Constant.MAILINATOR_API_TOKEN)
                        .when()
                        .get(Constant.MAILINATOR_API_URL + "private/messages/" + messageId + "/links");
                passwordLink = JsonPath.read(getMessage.asString(), "$.links[0]");
            } catch (Exception e) {
                log("Inbox Api Exception Found: " + e);
            }

            if (passwordLink != null) {
                break;
            }
            Thread.sleep(10000);
        }

        log("Password Link: " + passwordLink);
        return passwordLink;
    }

    public String getEmailBody(String messageId) throws InterruptedException {
        String emailBody = null;
        for (int i = 0; i < 5; i++) {
            try {
                Response getMessage = given()
                        .header("Authorization", Constant.MAILINATOR_API_TOKEN)
                        .when()
                        .get(Constant.MAILINATOR_API_URL + "private/messages/" + messageId);
                emailBody = JsonPath.read(getMessage.asString(), "$.parts[0].body");
            } catch (Exception e) {
                log("Email Api Exception Found: " + e);
            }

            if (emailBody != null) {
                break;
            }
            Thread.sleep(10000);
        }

        log("Email body: " + emailBody);
        return emailBody;
    }
}
