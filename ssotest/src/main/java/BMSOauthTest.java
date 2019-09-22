import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.imsglobal.lti.launch.*;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * test class to verify Single Sign On
 * https://www.imsglobal.org/specs/ltiv1p1/implementation-guide#toc-4
 * https://github.com/IMSGlobal/basiclti-util-java/
 * https://developers.google.com/api-client-library/java/google-api-java-client/samples
 * https://github.com/googleapis/google-oauth-java-client/tree/dev/google-oauth-client/src/test/java/com/google/api/client/auth/oauth
 */
public class BMSOauthTest {
    public static void main(String[] args) throws Exception {
        LtiVerifier verifier = new LtiOauthVerifier();
        LtiSigner signer = new LtiOauthSigner();

        String key = "B653FD74-86A5-4237-B566-FF3EBD0FD00EA";
        String secret = "B86864A5-0772-44C5-B151-E0A764889D4FA";
        String url = "https://workforceedu.wincrsystem.com/SSO/Connect";


        Map<String, String> params = new HashMap<String, String>();
        params.put("resource_link_id", URLEncoder.encode("https://testprecision.com", "UTF-8"));
        params.put("content_item_return_url", URLEncoder.encode("https://testprecision.com", "UTF-8"));
        params.put("lti_version", "LTI-1p0");

        System.out.println("Input Params: " + params);


        Map signedParams = signer.signParameters(params, key, secret, url, "POST");
        System.out.println("Signed Params: " + signedParams);
        LtiVerificationResult res = verifier.verifyParameters(signedParams, url, "POST", secret);
        System.out.println("RESULT Success: " + res.getSuccess() + " Error: " + res.getError());
        if (res.getSuccess() != null) {

            StringBuilder post = new StringBuilder();
            post.append(url).append("&");
            String oauth_signature = (String) signedParams.get("oauth_signature");
            //signedParams.remove("oauth_signature");
            for(Object k: signedParams.keySet()){
                System.out.println(k);
                post.append(k).append("=").append(signedParams.get(k)).append("&");
            }
            String postEncoded = URLEncoder.encode(post.toString(),"UTF-8");
            System.out.println("POSTing\n" + postEncoded);
            Request.Post(postEncoded)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .execute();

        }


//    LtiVerificationResult result = verifier.verify(ltiLaunch, secret);
    }
}
