package com.es.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class InitClient {

    public static RestHighLevelClient getClient(){
        System.out.println("开始初始化连接");
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("47.52.199.51", 9200, "http")));
        return client;
    }

}

