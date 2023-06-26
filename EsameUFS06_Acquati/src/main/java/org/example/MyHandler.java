package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


class MyHandler implements HttpHandler {
    private List<Hotel> hotelList;

    public MyHandler() {
        buildHotelList();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String response = "";

        if (query != null) {
            if (query.equals("cmd=all")) {
                response = getAllHotelsHTML();
            } else if (query.equals("cmd=all_sorted")) {
                response = getAllHotelsSortedHTML();
            } else if (query.equals("cmd=more_expensive_suite")) {
                response = getMoreExpensiveSuiteHTML();
            }
        }

        if (response.isEmpty()) {
            response = "Invalid command. Command: all, all_sorted, more_expensive";
        }

        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getAllHotelsHTML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><h1>All Hotels</h1>");
        sb.append("<table style=\"border-collapse: collapse;\">");
        sb.append("<tr><th style=\"border: 1px solid black; padding: 10px;\">ID</th><th style=\"border: 1px solid black; padding: 10px;\">Description</th><th style=\"border: 1px solid black; padding: 10px;\">Name</th><th style=\"border: 1px solid black; padding: 10px;\">Suite</th><th style=\"border: 1px solid black; padding: 10px;\">Price</th></tr>");
        for (Hotel hotel : hotelList) {
            sb.append("<tr>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getId()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getDescription()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getName()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.isSuite() ? "Yes" : "No").append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getPrice()).append("</td>")
                    .append("</tr>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }

    private String getAllHotelsSortedHTML() {
        List<Hotel> sortedList = new ArrayList<>(hotelList);
        sortedList.sort(Comparator.comparingDouble(Hotel::getPrice));
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><h1>All Hotels Sorted by Price</h1>");
        sb.append("<table style=\"border-collapse: collapse;\">");
        sb.append("<tr><th style=\"border: 1px solid black; padding: 10px;\">ID</th><th style=\"border: 1px solid black; padding: 10px;\">Description</th><th style=\"border: 1px solid black; padding: 10px;\">Name</th><th style=\"border: 1px solid black; padding: 10px;\">Suite</th><th style=\"border: 1px solid black; padding: 10px;\">Price</th></tr>");
        for (Hotel hotel : sortedList) {
            sb.append("<tr>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getId()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getDescription()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getName()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.isSuite() ? "Yes" : "No").append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(hotel.getPrice()).append("</td>")
                    .append("</tr>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }

    private String getMoreExpensiveSuiteHTML() {
        Hotel expensiveSuite = null;
        for (Hotel hotel : hotelList) {
            if (hotel.isSuite()) {
                if (expensiveSuite == null || hotel.getPrice() > expensiveSuite.getPrice()) {
                    expensiveSuite = hotel;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        if (expensiveSuite != null) {
            sb.append("<h1>More Expensive Suite</h1>")
                    .append("<table style=\"border-collapse: collapse;\">")
                    .append("<tr><th style=\"border: 1px solid black; padding: 10px;\">ID</th><th style=\"border: 1px solid black; padding: 10px;\">Description</th><th style=\"border: 1px solid black; padding: 10px;\">Name</th><th style=\"border: 1px solid black; padding: 10px;\">Suite</th><th style=\"border: 1px solid black; padding: 10px;\">Price</th></tr>")
                    .append("<tr>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(expensiveSuite.getId()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(expensiveSuite.getDescription()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(expensiveSuite.getName()).append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(expensiveSuite.isSuite() ? "Yes" : "No").append("</td>")
                    .append("<td style=\"border: 1px solid black; padding: 10px;\">").append(expensiveSuite.getPrice()).append("</td>")
                    .append("</tr>")
                    .append("</table>");
        } else {
            sb.append("<h1>No suites available</h1>");
        }
        sb.append("</body></html>");
        return sb.toString();
    }


    private void buildHotelList() {
        hotelList = new ArrayList<>();
        hotelList.add(new Hotel(1, "Hotel al centro di milano...", "Armani hotel", true, 500.94));
        hotelList.add(new Hotel(2, "Hotel 4 stelle nelle colline toscane...", "Hills Florence", false, 200.50));
        hotelList.add(new Hotel(3, "Hotel sulle montagne svizzere...", "Lugano finest", true, 1500.00));
        hotelList.add(new Hotel(4, "Motel a ore nella brianza", "Vimercate Motel", false, 60.00));
        hotelList.add(new Hotel(5, "Hotel affacciato sul mare di Sestri Levante", "Villa Rosa", false, 100.00));
        hotelList.add(new Hotel(6, "Hotel in centro a roma a 200 metri dal colosseo", "Colosseum hotel", true, 2000.00));
    }
}
