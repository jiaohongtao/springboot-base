package com.hong.test.company.esDir;

import com.alibaba.fastjson.JSONObject;
import com.hong.test.company.Person;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/02/23
 */
public class TestEsClient2 {

    public static void main(String[] args) {
        RestHighLevelClient client = buildClient();
        try {

            // insert(client);
            // create(client);
            // addIndex(client);
            // indexCreate(client);
            // getIndexInfo(client);
            // insertDataToIndex(client);
            // updateDataFromIndex(client);
            // getDataById(client);
            deleteDataById(client);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭ES客户端对象
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void deleteDataById(RestHighLevelClient client) throws IOException {
        // 2、定义请求对象
        DeleteRequest request = new DeleteRequest("usertest");
        request.id("1000");
        // 3、发送请求到ES
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        // 4、处理响应结果
        System.out.println("删除是否成功：" + response.getResult());
        // 5、关闭ES客户端对象
        client.close();
    }

    static void getDataById(RestHighLevelClient client) throws IOException {
        // 2、定义请求对象
        GetRequest request = new GetRequest("usertest");
        request.id("1000");
        // 3、发送请求到ES
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 4、处理响应结果
        System.out.println("查询结果：" + response.getSourceAsString());
        // 5、关闭ES客户端对象
        client.close();
    }


    static void updateDataFromIndex(RestHighLevelClient client) throws IOException {
        // 2、定义请求对象
        Person user = new Person("jiao", 15);
        UpdateRequest request = new UpdateRequest();
        request.index("usertest").id("1000");
        // 拓展：局部更新也可以这样写：request.doc(XContentType.JSON, "name", "李四", "age", 25);，其中"name"和"age"是User对象中的字段名称，而"小美"和20是对应的字段值
        request.doc(JSONObject.toJSONString(user), XContentType.JSON);
        // 3、发送请求到ES
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        // 4、处理响应结果
        System.out.println("数据更新结果：" + response.getResult());
        // 5、关闭ES客户端对象
        client.close();
    }


    static void insertDataToIndex(RestHighLevelClient client) throws IOException {
        // 2、创建请求对象
        Person user = new Person("jiao2", 113);
        // 定义请求对象
        IndexRequest request = new IndexRequest("usertest");
        // 设置文档id
        request.id("10001");
        // 将json格式字符串放在请求中
        request.source(JSONObject.toJSONString(user), XContentType.JSON);
        // 3、发送请求到ES
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        // 4、处理响应结果
        System.out.println("数据插入结果：" + response.getResult());
        // 5、关闭ES客户端对象
        client.close();
    }


    static void getIndexInfo(RestHighLevelClient client) throws IOException {
        // 定义索引名称
        GetIndexRequest request = new GetIndexRequest("usertest");
        // 发送请求到ES
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        // 处理响应结果
        System.out.println("aliases：" + response.getAliases());
        System.out.println("mappings：" + response.getMappings());
        System.out.println("settings：" + response.getSettings());
        // 关闭ES客户端对象
        client.close();
    }


    /**
     * 放到索引管理
     *
     * @param client
     * @return
     * @throws IOException
     */
    public static boolean indexCreate(RestHighLevelClient client) throws IOException {
        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest indexRequest = new CreateIndexRequest("goodstest");
        // 2、设置索引的settings
        // 3、设置索引的mappings
        String mapping = "{\n" +
                "    \"properties\":{\n" +
                "        \"brandName\":{\n" +
                "            \"type\":\"keyword\"\n" +
                "        },\n" +
                "        \"categoryName\":{\n" +
                "            \"type\":\"keyword\"\n" +
                "        },\n" +
                "        \"createTime\":{\n" +
                "            \"type\":\"date\",\n" +
                "            \"format\":\"yyyy-MM-dd HH:mm:ss\"\n" +
                "        },\n" +
                "        \"id\":{\n" +
                "            \"type\":\"long\"\n" +
                "        },\n" +
                "        \"price\":{\n" +
                "            \"type\":\"double\"\n" +
                "        },\n" +
                "        \"saleNum\":{\n" +
                "            \"type\":\"integer\"\n" +
                "        },\n" +
                "        \"status\":{\n" +
                "            \"type\":\"integer\"\n" +
                "        },\n" +
                "        \"stock\":{\n" +
                "            \"type\":\"integer\"\n" +
                "        }" +
                "    }\n" +
                "}";
        System.out.println(mapping);
        // 4、 设置索引的别名
        // 5、 发送请求
        // 5.1 同步方式发送请求
        IndicesClient indicesClient = client.indices();
        indexRequest.mapping(mapping, XContentType.JSON);

        // 请求服务器
        CreateIndexResponse response = indicesClient.create(indexRequest, RequestOptions.DEFAULT);

        return response.isAcknowledged();
    }


    public static void addIndex(RestHighLevelClient client) throws IOException {
        /**
         * GET /user
         * 作用：可以看到aliases、mappings、settings
         * 说明：我们下面使用的是直接定死的结构，而SpringBoot中使用的是注解的方式来创建索引结构，最终的原理都是下面的代码
         * {
         *     "user": {
         *         "aliases": {
         *             "user.aliases": {}
         *         },
         *         "mappings": {
         *             "properties": {
         *                 "age": {
         *                     "type": "integer"
         *                 },
         *                 "name": {
         *                     "type": "text",
         *                     "fields": {
         *                         "keyword": {
         *                             "type": "keyword"
         *                         }
         *                     }
         *                 },
         *                 "sex": {
         *                     "type": "keyword"
         *                 }
         *             }
         *         },
         *         "settings": {
         *             "index": {
         *                 "creation_date": "1649243890532",
         *                 "number_of_shards": "9",
         *                 "number_of_replicas": "2",
         *                 "uuid": "EPChtL_vQj2gHJbO5VTHqg",
         *                 "version": {
         *                     "created": "7060099"
         *                 },
         *                 "provided_name": "user"
         *             }
         *         }
         *     }
         * }
         */
        // 创建ES客户端对象
        // RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        // 定义索引名称
        CreateIndexRequest request = new CreateIndexRequest("usertest");
        // 添加aliases，对比上述结构来理解
        String aliaseStr = "{\"user.aliases\":{}}";
        Map<String, Object> aliases = JSONObject.parseObject(aliaseStr, Map.class);
        // 添加mappings，对比上述结构来理解
        String mappingStr = "{\"properties\":{\"name\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\"}}},\"sex\":{\"type\":\"keyword\"},\"age\":{\"type\":\"integer\"}}}";
        Map<String, Object> mappings = JSONObject.parseObject(mappingStr, Map.class);
        // 添加settings，对比上述结构来理解
        String settingStr = "{\"index\":{\"number_of_shards\":\"9\",\"number_of_replicas\":\"2\"}}";
        Map<String, Object> settings = JSONObject.parseObject(settingStr, Map.class);

        // 添加数据
        request.aliases(aliases);
        request.mapping(mappings);
        request.settings(settings);

        // 发送请求到ES
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        // 处理响应结果
        System.out.println("添加索引是否成功：" + response.isAcknowledged());

    }


    public static RestHighLevelClient buildClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "123456"));
        HttpHost httpHost = new HttpHost("10.1.2.15", 9200, "http");

        String ipStr = "10.1.2.16:9200,10.1.2.15:9200,10.1.2.17:9200,10.1.2.18:9200";
        ipStr = "fe80::48f4:ab1e:b73a:88a8_9200";
        ipStr = "fd15:4ba5:5a2b:1001:20c:29ff:fe7d:4b10_9200";
        ipStr = "fe80::2bce:e422:a01:2621_9200,[fd15:4ba5:5a2b:1001:20c:29ff:fe7d:4b11]_9200,fe80::48f4:ab1e:b73a:88a8_9200";
        //fe80::48f4:ab1e:b73a:88a8
        //fd15:4ba5:5a2b:1001:20c:29ff:fe7d:4b11
        //fe80::2bce:e422:a01:2621
        //fe80::33e4:c7fc:b93e:e32e
        HttpHost[] httpHosts = Arrays.stream(ipStr.split(",")).map(x -> {
            String[] hostInfo = x.split("_");
            return new HttpHost(hostInfo[0], Integer.parseInt(hostInfo[1]));
        }).toArray(HttpHost[]::new);
        return new RestHighLevelClient(RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );
    }
}
