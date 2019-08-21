package io.bankbridge.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import io.bankbridge.utils.HttpClientUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.bankbridge.utils.JacksonUtils.jacksonDataBind;

public class BanksRemoteCalls extends BankHandler {


    /**
     * Regular expression PATTERN to extract BIC from string.
     */
    private static final Pattern PATTERN = Pattern.compile("\"bic\":\"(\\d*?)\"");

    /**
     * Alternative string for missing bic.
     */
    private static final String NOT_AVAILABLE = "Not Available";

    private static Map<String, String> config;

    /**
     * @see BankHandler#BankHandler(Version)
     */
    public BanksRemoteCalls(Version version) {
        super(version);
    }

    /**
     * @see Handleable#init()
     */
    public void init() {
        //get config (urls for banks) from resource
        try {
            config = new ObjectMapper()
                    .readValue(Thread.currentThread().getContextClassLoader().getResource(version.getResourceName()),
                            new TypeReference<HashMap<String, String>>() {
                            });
        } catch (IOException e) {
            throw new RuntimeException("Error while processing request", e.getCause());
        }
    }

    /**
     * @see Handleable#handle()
     */
    public String handle() {
        List<Map<String, String>> result = new ArrayList<>();

        //parse config to get bank names and corresponding urls from which to extract bic numbers
        for (Map.Entry<String, String> entry : config.entrySet()) {
            // key is bank name
            // value is URL (see test io.bankbridge.MockRemotes)

            Map<String, String> map = new HashMap<>();
            map.put(BankModel.ID, getBic(entry.getValue()));
            map.put(BankModel.NAME, entry.getKey());
            result.add(map);
        }

        return jacksonDataBind(result);
    }

    /**
     * Connects to the specified URL (MockRemotes) using http client to retrieve HTTP response
     * then parses it to get the BIC number.
     *
     * @param url the url to connect to to retrieve the BIC, as a String.
     * @return the BIC number or 'Not Available', as a String.
     */
    private static String getBic(String url) {
        String response = HttpClientUtils.getHttpResponse(url);
        //extract bic
        Matcher matcher = PATTERN.matcher(response);
        return matcher.find() ? matcher.group(1) : NOT_AVAILABLE;
    }


}
