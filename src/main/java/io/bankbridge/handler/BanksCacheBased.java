package io.bankbridge.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bankbridge.model.BankModel;
import io.bankbridge.model.BankModelList;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.bankbridge.utils.JacksonUtils.jacksonDataBind;

public class BanksCacheBased extends BankHandler {


    /**
     * An alias for the cache manager.
     */
    private static final String ALIAS = "banks";

    private static CacheManager cacheManager;

    /**
     * @see BankHandler#BankHandler(Version)
     */
    public BanksCacheBased(Version version) {
        super(version);
    }

    /**
     * @see Handleable#init()
     */
    public void init() {
        //create cache
        cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().withCache(ALIAS, CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
        Cache cache = cacheManager.getCache(ALIAS, String.class, String.class);

        //load banks in cache from resource json file.
        try {
            BankModelList models = new ObjectMapper()
                    .readValue(Thread.currentThread().getContextClassLoader().getResource(version.getResourceName()),
                            BankModelList.class);

            for (BankModel model : models.banks) {
                cache.put(model.bic, model.name);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error while processing request", e.getCause());
        }
    }

    /**
     * @see Handleable#handle()
     */
    public String handle() {
        List<Map<String, String>> result = new ArrayList<>();

        //get model from cache
        cacheManager.getCache(ALIAS, String.class, String.class).forEach(entry -> {
            Map<String, String> map = new HashMap<>();
            map.put(BankModel.ID, entry.getKey());
            map.put(BankModel.NAME, entry.getValue());
            result.add(map);
        });

        return jacksonDataBind(result);
    }

}
