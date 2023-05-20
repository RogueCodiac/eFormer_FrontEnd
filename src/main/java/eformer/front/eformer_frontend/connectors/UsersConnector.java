package eformer.front.eformer_frontend.connectors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsersConnector extends RequestsGateway {
    private static final String baseUrl = "users/";

    private static String getUrl(String target) {
        return baseUrl + target;
    }

    public static List<User> proccessUsersList(Object response) {
        var result = new ArrayList<User>();

        for (var json: (JSONArray) response) {
            result.add(new User((JSONObject) json));
        }

        return result;
    }

    public static List<User> getAllAfter(LocalDateTime date) {
        try {
            var response = post(
                    getUrl("getAllAfter"),
                    date
            );

            return proccessUsersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<User> getAllBefore(LocalDateTime date) {
        try {
            var response = post(
                    getUrl("getAllBefore"),
                    date
            );

            return proccessUsersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static User create(User user) {
        try {
            var response = (JSONObject) post(
                    getUrl("create"),
                    user
            );

            return new User(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static User create(JSONObject user) {
        return create(new User(user));
    }

    public static User update(JSONObject user) {
        try {
            var response = (JSONObject) post(
                    getUrl("update"),
                    user
            );

            return new User(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<User> getByAdLevel(Integer adLevel) {
        try {
            var response = post(
                    getUrl("getByAdLevel"),
                    adLevel
            );

            return proccessUsersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<User> getAll() {
        try {
            var response = post(
                    getUrl("getAll"),
                    null
            );

            return proccessUsersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }
}
