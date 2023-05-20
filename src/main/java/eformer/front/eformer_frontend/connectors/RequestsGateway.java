package eformer.front.eformer_frontend.connectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestsGateway {
    private final static String urlBase = "http://localhost:8080/api/v1/";

    private static String token = null;

    public static Object executePostRequest(
            String target,
            Object body,
            String token
    ) {
        try {
            URL url = new URL(urlBase + target);

            var connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept","application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            if (body != null) {
                try (var out = connection.getOutputStream()) {
                    byte[] input = body.toString().getBytes(StandardCharsets.UTF_8);
                    out.write(input, 0, input.length);
                }
            }

            var response = new StringBuilder();

            try (var reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(),
                            StandardCharsets.UTF_8))
            ) {
                String responseLine;

                while ((responseLine = reader.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            return new JSONObject(response.toString());
        } catch (Exception e) {
            return e;
        }
    }

    public static Object executeGetRequest(
            String target,
            JSONObject params
    ) {
        try {
            if (params != null) {
                StringBuilder targetBuilder = new StringBuilder(target).append('?');
                for (var param: params.keySet()) {
                    targetBuilder.append(param).append('=').append(params.get(param)).append('&');
                }

                target = targetBuilder.toString();
            }

            URL url = new URL(urlBase + target);

            var connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            var response = new StringBuilder();

            try (var reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(),
                            StandardCharsets.UTF_8))
            ) {
                String responseLine;

                while ((responseLine = reader.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            try {
                return new JSONObject(response.toString());
            } catch (JSONException e) {
                return new JSONArray(response.toString());
            }
        } catch (Exception e) {
            return e;
        }
    }

    public Object post(String target, JSONObject body) {
        return executePostRequest(target, body, token);
    }

    public void authenticate(String username, String password) {
        var body = new JSONObject();

        body.put("username", username);
        body.put("password", password);

        var response = (JSONObject) post("auth/authenticate", body);
        token = (String) response.get("token");
    }

    public void register(JSONObject body) {
        var response = (JSONObject) post("auth/register", body);
        token = (String) response.get("token");
    }

    public void logout() {
        token = null;
    }
}
