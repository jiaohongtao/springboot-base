package com.hong.test.company.esDir;

import com.alibaba.fastjson.JSON;
import com.hong.test.company.Person;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/02/23
 */
public class TestEsClient {

    public static void main(String[] args) {

        RestHighLevelClient client = buildClient();

        try {
            search(client);

            // insert(client);
            // create(client);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void search(RestHighLevelClient client) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));

        SearchRequest searchRequest = new SearchRequest();
        // searchRequest.indices("social-*");
        searchRequest.indices("cs*");
        searchRequest.source(searchSourceBuilder);
        /*GetIndexRequest getIndexRequest = new GetIndexRequest();
        GetIndexResponse getIndexResponse = client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(getIndexResponse);*/
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
    }

    public static void insert(RestHighLevelClient client) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "dd df ff@22");
        map.put("age", 22);

        String index = "test001";
        String type = "test001";
        BulkRequest request = new BulkRequest();
        //这里必须每次都使用new IndexRequest(index,type),不然只会插入最后一条记录（这样插入不会覆盖已经存在的Id，也就是不能更新）
        //request.add(new IndexRequest(index,type).opType("create").id(map.remove("id").toString()).source(map));
        IndexRequest indexRequest = new IndexRequest(index, type, String.valueOf(map.remove("id")))
                .id(index.concat("-").concat(String.valueOf(new SecureRandom().nextInt(100))))
                .source(map, XContentType.JSON);
        request.add(indexRequest);
        client.bulk(request, RequestOptions.DEFAULT);

    }

    public static void insertBatch(RestHighLevelClient client) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        int cnt = 10;
        for (int i = 0; i < cnt; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "dd df ff" + i);
            map.put("age", 16 + i);
            list.add(map);
        }

        String index = "test001";
        String type = "test001";
        BulkRequest request = new BulkRequest();
        /*for (Map<String, Object> map : list) {
            //这里必须每次都使用new IndexRequest(index,type),不然只会插入最后一条记录（这样插入不会覆盖已经存在的Id，也就是不能更新）
            //request.add(new IndexRequest(index,type).opType("create").id(map.remove("id").toString()).source(map));
            request.add(new IndexRequest(index, type, String.valueOf(map.remove("id"))).source(map, XContentType.JSON));
            client.bulk(request, RequestOptions.DEFAULT);
        }*/

    }

    public static boolean create(RestHighLevelClient client) throws IOException {
        String index = "test001";
        Person person = new Person("jiao", 12);
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        IndexRequest indexRequest = new IndexRequest(index)
                .id(person.getName())
                .source(JSON.toJSONString(person), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        RestStatus status = indexResponse.status();
        return status == RestStatus.CREATED || status == RestStatus.OK;
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
