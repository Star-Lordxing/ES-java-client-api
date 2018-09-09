package com.es.demo;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

public class TestIndexApi {

    public static void main(String ags[]){
        try(RestHighLevelClient client = InitClient.getClient()){

            // 1.创建索引名
            CreateIndexRequest request = new CreateIndexRequest("book6");

            // 2.索引setting配置
            request.settings(Settings.builder().put("index.number_of_shards",5)
              .put("index.number_of_replicas", 2) // 副本数
            );

            // 3.设置索引的mapping
            request.mapping("_doc",
                    "  {\n" +
                            "    \"_doc\": {\n" +
                            "      \"properties\": {\n" +
                            "        \"message\": {\n" +
                            "          \"type\": \"text\"\n" +
                            "        }\n" +
                            "      }\n" +
                            "    }\n" +
                            "  }",
                    XContentType.JSON);

            // 设置索引别名
            request.alias(new Alias("lab1"));

            // 5.发送请求
            // 5.1同步方式
            CreateIndexResponse response = client.indices().create(request);

            // 6.处理响应
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();

            System.out.println("acknowledged:"+acknowledged);
            System.out.println("shardsAcknowledged:"+shardsAcknowledged);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
