import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URLEncodedUtils;
import org.imsglobal.lti.launch.*;

import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

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

        String consumer_key = "B653FD74-86A5-4237-B566-FF3EBD0FD00EA";
        String secret = "B86864A5-0772-44C5-B151-E0A764889D4FA";
        String url = "https://workforceedu.wincrsystem.com/SSO/Connect";


        Map<String, String> params = new HashMap<String, String>();
        params.put("resource_link_id", URLEncoder.encode("testprecision.com","UTF-8"));
        params.put("content_item_return_url", URLEncoder.encode("testprecision.com","UTF-8"));
        params.put("custom_connection_test", URLEncoder.encode("true","UTF-8"));
        params.put("custom_person_name_middle", URLEncoder.encode("Alan","UTF-8"));
        params.put("custom_region_cd", URLEncoder.encode("SW","UTF-8"));
        params.put("launch_presentation_locale", URLEncoder.encode("en","UTF-8"));
        params.put("lis_person_contact_email_primary", URLEncoder.encode("john.doe@myemail.com","UTF-8"));
        params.put("lis_person_name_family", URLEncoder.encode("Doe","UTF-8"));
        params.put("lis_person_name_given", URLEncoder.encode("John","UTF-8"));
        params.put("lti_message_type", URLEncoder.encode("basic-lti-launch-request","UTF-8"));
        params.put("lti_version", URLEncoder.encode("LTI-1p0","UTF-8"));
        params.put("oauth_callback", URLEncoder.encode("about_blank","UTF-8"));
        params.put("oauth_consumer_key", URLEncoder.encode(consumer_key,"UTF-8"));
        params.put("oauth_signature_method", URLEncoder.encode("HMAC-SHA1","UTF-8"));
        params.put("oauth_version", URLEncoder.encode("1.0","UTF-8"));
        params.put("resource_link_id", URLEncoder.encode("WinLTITest","UTF-8"));
        params.put("roles", URLEncoder.encode("Learner","UTF-8"));
        params.put("user_id", URLEncoder.encode("03ee21d8-5495-4a0b-8def-c9f4094698de","UTF-8"));
        // these will be generated: oauth_nonce,oauth_signature,oauth_timestamp

        System.out.println("Input Params: " + params);


        Map signedParams = signer.signParameters(params, consumer_key, secret, url, "POST");
        System.out.println("Signed Params: " + signedParams);

        LtiVerificationResult res = verifier.verifyParameters(signedParams, url, "POST", secret);
        System.out.println("RESULT Success: " + res.getSuccess() + " Error: " + res.getError());
        if (res.getSuccess() != null) {

            //post.append(url).append("&");
//            String oauth_signature = (String) signedParams.remove("oauth_signature");
//            System.out.println("oauth_signature:"+oauth_signature);

            ArrayList<String> sortedList = new ArrayList<>();

            Iterator itr = signedParams.keySet().iterator();
            while (itr.hasNext()){
                sortedList.add((String) itr.next());
            }
            Collections.sort(sortedList);
            StringBuilder post = new StringBuilder();
            //post.append("POST&").append(url);
            for(String k: sortedList){
                System.out.println(k);
                post.append("&").append(k).append("=").append(signedParams.get(k));
            }

//            post.append("&").append("oauth_signature=").append(oauth_signature);
            String postEncoded = post.toString().replaceFirst("&","?");//URLEncoder.encode(post.toString(),"UTF-8");
            System.out.println("POSTing\n" + postEncoded);
            Response response =  Request.Post(url+postEncoded)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .execute();

            System.out.println(response.returnContent().asString());
        }


//    LtiVerificationResult result = verifier.verify(ltiLaunch, secret);
    }
}
