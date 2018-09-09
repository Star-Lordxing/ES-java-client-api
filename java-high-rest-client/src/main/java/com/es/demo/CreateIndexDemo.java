package com.es.demo;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class CreateIndexDemo {

    public static void main(String ags[]) {
        try (RestHighLevelClient client = InitClient.getClient()) {

            // 1.创建索引名
            CreateIndexRequest request = new CreateIndexRequest("book13");

            // 2.索引setting配置
            /*request.settings(Settings.builder().put("index.number_of_shards",5)
                    .put("index.number_of_replicas", 2) // 副本数
                    .put("analysis.analyzer.default.tokenizer","standard")
            );*/

            // 3.设置索引的mapping
            // 3.1方式一、直接给出json串
            /*      request.mapping("_doc",
                    "  {\n" +
                            "    \"_doc\": {\n" +
                            "      \"properties\": {\n" +
                            "        \"message\": {\n" +
                            "          \"type\": \"text\"\n" +
                            "        }\n" +
                            "      }\n" +
                            "    }\n" +
                            "  }",
                    XContentType.JSON);*/

            // 3.2方式二、给出封装成Map

            /*   Map<String, Object> jsonMap = new HashMap<>();
            Map<String, Object> message = new HashMap<>();
            message.put("type", "text");
            Map<String, Object> properties = new HashMap<>();
            properties.put("message", message);
            Map<String, Object> _doc = new HashMap<>();
            _doc.put("properties", properties);
            jsonMap.put("_doc", _doc);
            request.mapping("_doc", jsonMap);*/

            // 3.3方式三、使用XContentBuilder
           /* XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("_doc");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("message");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("message1");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping("_doc", builder);*/

            // 3.4方式四、使用XContentBuilder
            request.source("{\n" +
                    "    \"settings\" : {\n" +
                    "        \"number_of_shards\" : 1,\n" +
                    "        \"number_of_replicas\" : 0\n" +
                    "    },\n" +
                    "    \"mappings\" : {\n" +
                    "        \"_doc\" : {\n" +
                    "            \"properties\" : {\n" +
                    "                \"message\" : { \"type\" : \"text\" },\n" +
                    "                \"message1\" : { \"type\" : \"text\" }\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"aliases\" : {\n" +
                    "        \"lab2\" : {}\n" +
                    "    }\n" +
                    "}", XContentType.JSON);


            // 设置索引别名
            //request.alias(new Alias("lab1"));

            // 5.发送请求
            // 5.1同步方式
            CreateIndexResponse response = client.indices().create(request);

            // 处理响应
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();

            System.out.println("请求结果---------------");
            System.out.println("acknowledged:" + acknowledged);
            System.out.println("shardsAcknowledged:" + shardsAcknowledged);

            // 5.2 异步方式发送请求
           /* ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {

                @Override
                public void onResponse(CreateIndexResponse createIndexResponse) {
                    boolean acknowledged = createIndexResponse.isAcknowledged();
                    boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
                    System.out.println("请求结果---------------");
                    System.out.println("acknowledged:"+acknowledged);
                    System.out.println("shardsAcknowledged:"+shardsAcknowledged);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            };

            client.indices().createAsync(request, listener);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
