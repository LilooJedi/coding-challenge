package io.bankbridge.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A utility class to manipulate http requests and responses.
 */
public class HttpClientUtils {

    /**
     * Returns the http get response for the specified url.
     * Future improvement: make asynchronous.
     *
     * @param url the url to get the response from, as a String.
     * @return the response, as a String.
     */
    public static String getHttpResponse(String url) {
        try {
            // get connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            //get response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return response.toString();

            } catch (IOException e) {
                throw new RuntimeException("Error while processing request", e.getCause());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error while processing request", e.getCause());
        }
    }

}
