package io.bankbridge.handler;

import io.bankbridge.MockRemotes;
import io.bankbridge.model.BankModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.bankbridge.utils.JacksonUtils.getBanksFromJson;
import static spark.Spark.get;
import static spark.Spark.stop;

class BanksHandlersTest {

    @BeforeAll
    static void before() {
        MockRemotes.main(null);
    }

    @AfterAll
    static void after() {
        stop();
    }

    @Test
    void testHandlers() {
        //test V1
        BankHandler cached = new BanksCacheBased(Version.V1);
        cached.init();
        String finalActualV1 = cached.handle();
        get(cached.getPath(), (request, response) -> finalActualV1);

        //test valid json
        List<BankModel> banks = null;
        try {
            banks = getBanksFromJson(finalActualV1);
        } catch (IOException e) {
            Assertions.fail();
        }

        //test not empty
        Assertions.assertFalse(banks.isEmpty());

        //test existence of expected attributes
        for (BankModel bank : banks) {
            Assertions.assertNotNull(bank.bic);
            Assertions.assertNotNull(bank.name);
        }


        //test V2
        BankHandler remote = new BanksRemoteCalls(Version.V2);
        remote.init();
        String finalActualV2 = remote.handle();
        get(remote.getPath(), (request, response) -> finalActualV2);

        //test existence of expected attributes
        try {
            banks = getBanksFromJson(finalActualV2);
        } catch (IOException e) {
            Assertions.fail();
        }

        //test not empty
        Assertions.assertFalse(banks.isEmpty());

        //test existence of expected attributes
        for (BankModel bank : banks) {
            Assertions.assertNotNull(bank.bic);
            Assertions.assertNotNull(bank.name);
        }
    }

}