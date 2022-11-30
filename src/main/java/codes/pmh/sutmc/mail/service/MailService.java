package codes.pmh.sutmc.mail.service;

import codes.pmh.sutmc.mail.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service("mailService")
public class MailService {
    @Value("${cloudflare.endpoint}")
    private String ENDPOINT_URL;

    @Value("${cloudflare.api_key}")
    private String API_KEY;

    @Value("${cloudflare.zone_id}")
    private String ZONE_ID;

    @Value("${cloudflare.account_id}")
    private String ACCOUNT_ID;

    private String request (String method, String path, Optional<String> body) {
        try {
            URL url = new URL(this.ENDPOINT_URL + path);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Authorization", "Bearer " + this.API_KEY);

            if (body.isPresent()) {

                byte[] input = body.get().getBytes(StandardCharsets.UTF_8);

                OutputStream os = con.getOutputStream();
                os.write(input, 0, input.length);
            }

            BufferedReader in = null;

            if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String resRaw = response.toString();
            System.out.println("Res: " + resRaw);

            return resRaw;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return "{\"success\":false}";
        }
    }

    public Optional<Boolean> checkAndVerify (String email) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            APIAddAddress rawBody = new APIAddAddress();
            rawBody.setEmail(email);

            String body = objectMapper.writeValueAsString(rawBody);

            String rawResponse = this.request("POST", "/accounts/" + this.ACCOUNT_ID + "/email/routing/addresses", Optional.of(body));

            APIResponse<APIAddAddressResponse> response = objectMapper.readValue(rawResponse, new TypeReference<APIResponse<APIAddAddressResponse>>() {});
            if (!response.getSuccess()) {
                System.out.println(response.getMessages()[0].getMessage());
                return Optional.empty();
            }

            return Optional.of(response.getResult().getStatus().equals("verified"));
        } catch (JsonProcessingException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> addEmail (String email) {
        ObjectMapper objectMapper = new ObjectMapper();
        Faker faker = new Faker();

        Name name = faker.name();
        String randomEmail = name.firstName().toLowerCase() + "." + name.lastName().toLowerCase();

        try {
            APIAddRule rawBody = new APIAddRule();

            APIAddRuleAction action = new APIAddRuleAction();
            action.setType("forward");
            action.setValue(new String[]{email});

            rawBody.setActions(new APIAddRuleAction[]{action});

            APIAddRuleMatcher matcher = new APIAddRuleMatcher();
            matcher.setType("literal");
            matcher.setField("to");
            matcher.setValue(randomEmail + "@shutupandtakemy.codes");

            rawBody.setMatchers(new APIAddRuleMatcher[]{matcher});

            rawBody.setEnabled(true);
            rawBody.setName("Send to " + email + "@shutupandtakemy.codes rule.");
            rawBody.setPriority(0);

            String body = objectMapper.writeValueAsString(rawBody);

            String rawResponse = this.request("POST", "/zones/" + this.ZONE_ID + "/email/routing/rules", Optional.of(body));

            APIResponse<APIAddRuleResponse> response = objectMapper.readValue(rawResponse, new TypeReference<APIResponse<APIAddRuleResponse>>() {});
            if (!response.getSuccess()) {
                System.out.println(response.getMessages()[0].getMessage());
                return Optional.empty();
            }

            return Optional.of(randomEmail);
        } catch (JsonProcessingException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }
}
