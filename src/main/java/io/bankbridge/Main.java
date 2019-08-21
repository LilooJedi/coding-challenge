package io.bankbridge;

import io.bankbridge.handler.BankHandler;
import io.bankbridge.handler.BanksCacheBased;
import io.bankbridge.handler.BanksRemoteCalls;
import io.bankbridge.handler.Version;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    public static void main(String[] args) {

        port(8080);

        List<BankHandler> handlers = new ArrayList<>(Version.values().length);
        handlers.add(new BanksCacheBased(Version.V1));
        handlers.add(new BanksRemoteCalls(Version.V2));

        for (BankHandler handler : handlers) {
            handler.init();
            get(handler.getPath(), (request, response) -> handler.handle());
        }
    }
}