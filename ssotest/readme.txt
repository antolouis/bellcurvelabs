
https://www.imsglobal.org/specs/ltiv1p1/implementation-guide#toc-4
https://github.com/IMSGlobal/basiclti-util-java/


I looked more deeply into our library, and it is doing something like this:

1. Creates the base string: “POST&”

2. Appends the encoded URL and appends “&”:

“POST&https%3A%2F%2Fwedu.wincrsystem.com%2FSSO%2FConnect&”

3. Sorts the parameters and encodes them like this:

“content_item_return_url=http%3A%2F%2Flocalhost%3A63776%2F&custom_connection_test=true&custom_person_name_middle=Alan&custom_region_cd=SW&launch_presentation_locale=en&lis_person_contact_email_primary=john.doe%40myEmail.com&lis_person_name_family=Doe&lis_person_name_given=John&lti_message_type=basic-lti-launch-request&lti_version=LTI-1p0&oauth_callback=about%3Ablank&oauth_consumer_key=F108C356-C85F-4E91-951B-FAF709129392&oauth_nonce=b2d74a8295d04726ba32ad00f8806e63&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1565643067&oauth_version=1.0&resource_link_id=WinLTITest&roles=Learner&user_id=03ee21d8-5495-4a0b-8def-c9f4094698de”

4. Encodes the parameters string from step 3 as a whole (to normalize the “&” characters I believe) and then appends to the signature string. It results in something like this:

“POST&https%3A%2F%2Fwedu.wincrsystem.com%2FSSO%2FConnect&content_item_return_url%3Dhttp%253A%252F%252Flocalhost%253A63776%252F%26custom_connection_test%3Dtrue%26custom_person_name_middle%3DAlan%26custom_region_cd%3DSW%26launch_presentation_locale%3Den%26lis_person_contact_email_primary%3Djohn.doe%2540myEmail.com%26lis_person_name_family%3DDoe%26lis_person_name_given%3DJohn%26lti_message_type%3Dbasic-lti-launch-request%26lti_version%3DLTI-1p0%26oauth_callback%3Dabout%253Ablank%26oauth_consumer_key%efbbbdbd-c6d9-4fd4-a7e3-b745ae87067a%26oauth_nonce%3Db2d74a8295d04726ba32ad00f8806e63%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1565643067%26oauth_version%3D1.0%26resource_link_id%3DWinLTITest%26roles%3DLearner%26user_id%3D03ee21d8-5495-4a0b-8def-c9f4094698de”

5. It then appends “&” to the shared secret.

6. Converts that and the signature string to ASCII Bytes and uses them to generate the HMAC-SHA1 hash.

7. Converts the result of that to a base 64 string and that becomes the signature, something like: “aGfAcPKi8kELFrGP8hue9scefBk=”
