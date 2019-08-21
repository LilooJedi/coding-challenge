package io.bankbridge.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A utility class to manipulate jackson objects.
 */
public class JacksonUtils {

    /**
     * Returns the json string representation of the input result using Jackson.
     *
     * @param result the result to format in json, as a List<Map<String, String>>.
     * @return the json representation, as a String.
     */
    public static String jacksonDataBind(List<Map<String, String>> result) {
        try {
            return new ObjectMapper().writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while processing request", e.getCause());
        }
    }

    /**
     * Returns the list of bankModel as described in the specified json String.
     *
     * @param json the json to parse, as a String.
     * @return the list of bank models, as a List<BankModel>.
     * @throws IOException input output exception
     */
    public static List<BankModel> getBanksFromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, new TypeReference<List<BankModel>>() {
        });
    }

}
