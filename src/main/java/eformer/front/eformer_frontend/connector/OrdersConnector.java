package eformer.front.eformer_frontend.connector;

import eformer.front.eformer_frontend.model.Item;
import eformer.front.eformer_frontend.model.Order;
import eformer.front.eformer_frontend.model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersConnector extends RequestsGateway {
    private static final String baseUrl = "orders/";

    private static String getUrl(String target) {
        return baseUrl + target;
    }

    public static List<Order> proccessOrdersList(Object response) {
        var result = new ArrayList<Order>();

        for (var json: (JSONArray) response) {
            result.add(new Order((JSONObject) json));
        }

        return result;
    }

    public static Order getById(Integer id) {
        try {
            var response = (JSONObject) post(getUrl("getById"),
                    id
            );

            return new Order(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllByCustomer(User customer) {
        try {
            var response = post(getUrl("getAllByCustomer"),
                    customer);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllByEmployee(User employee) {
        try {
            var response = post(getUrl("getAllByEmployee"),
                    employee);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllByCustomerAndEmployee(User customer,
                                                          User employee) {
        try {
            var body = new JSONObject();

            body.put("customer", customer);
            body.put("employee", employee);

            var response = post(getUrl("getAllByCustomerAndEmployee"),
                    body);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllByStatus(String status) {
        try {
            var response = post(getUrl("getAllByStatus"),
                    status);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAll() {
        try {
            var response = post(getUrl("getAll"), null);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllBeforeDate(LocalDateTime date) {
        try {
            var response = post(getUrl("getAllBeforeDate"),
                    date);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllAfterDate(LocalDateTime date) {
        try {
            var response = post(getUrl("getAllAfterDate"),
                    date);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllBetween(LocalDateTime start,
                                            LocalDateTime end) {
        try {
            var dates = new JSONObject();

            dates.put("start", start);
            dates.put("end", end);

            var response = post(getUrl("getAllBetweenDates"),
                    dates);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Double getTotalSales() {
        try {
            return Double.parseDouble((String) post(getUrl("getTotalSales"),
                    null));
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Order> getAllPaid() {
        try {
            var response = post(getUrl("getAllPaid"),
                    null);

            return proccessOrdersList(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Integer getTotalSoldQuantity() {
        try {
            return (Integer) post(getUrl("getTotalSoldQuantity"),
                    null);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Double getTotalActualSales() {
        try {
            return Double.parseDouble((String) post(getUrl("getTotalActualSales"),
                    null));
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Double getTotalProfit() {
        try {
            return Double.parseDouble((String) post(getUrl("getTotalProfit"),
                    null));
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean confirm(Integer orderId, Double amountPaid) {
        try {
            var body = new JSONObject();

            body.put("orderId", orderId);
            body.put("amountPaid", amountPaid);

            post(getUrl("confirm"), body);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean cancel(Integer orderId) {
        try {
            post(getUrl("cancel"), orderId);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static Order update(Integer orderId, JSONObject items) {
        try {
            items.put("orderId", orderId);
            var response = (JSONObject) post(getUrl("update"), items);

            return new Order(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Order create(Integer customerId,
                               JSONObject items,
                               String note) {
        try {
            var body = new JSONObject();

            body.put("items", items);
            body.put("customerId", customerId);
            body.put("note", note);

            var response = (JSONObject) post(getUrl("create"), body);

            return new Order(response);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static List<Item> getItems(Integer orderId) {
        try {
            var response = post(getUrl("getOrderItems"), orderId);

            return ItemsConnector.proccessItemsList(response);
        } catch (Exception ignored) {
            return null;
        }
    }
}
