package io.bankbridge.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BankModel {

    public static final String ID = "id";
    public static final String NAME = "name";

    @JsonAlias("id")
    public String bic;
    public String name;
    public String countryCode;
    public String auth;

}
