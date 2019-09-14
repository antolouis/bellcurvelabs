import com.google.api.client.auth.oauth.*;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * test class to verify Single Sign On
 * https://www.imsglobal.org/specs/ltiv1p1/implementation-guide#toc-4
 * https://developers.google.com/api-client-library/java/google-api-java-client/samples
 * https://github.com/googleapis/google-oauth-java-client/tree/dev/google-oauth-client/src/test/java/com/google/api/client/auth/oauth
 *
 */
public class SSOTest {
    public static void main(String[] args) {

        OAuthParameters parameters = new OAuthParameters();
        parameters.consumerKey = "testck";
        parameters.verifier = "verifier";
        parameters.nonce = "nouncetest";
        parameters.signatureMethod = "HMAC-SHA1";
        parameters.timestamp = String.valueOf(Calendar.getInstance(Locale.US).getTime().getTime());
        parameters.token = "token";
        parameters.signature = "OTfTeiNjKsNeqBtYhUPIiJO9pC4=";
    }
}
