package com.hong.test.company.esDir;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author: yjj
 * @Date: 2022/06/29/11:03
 * @Description:
 */
@Slf4j
@Service
public class EsService {

    @Autowired
    @Qualifier("restHighLevelClient")
    public RestHighLevelClient client;

    String indexName = "cs001";

    public boolean createUserIndex(String index) throws IOException {

        //创建索引(建表)
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
        );
        createIndexRequest.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"city\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"sex\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }

    //删除索引(删表)
    public Boolean deleteUserIndex(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return deleteIndexResponse.isAcknowledged();
    }

    //删除索引(删表)
    public GetIndexResponse getUserIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);

        getIndexRequest = new GetIndexRequest("book");
        return client.indices().get(getIndexRequest, RequestOptions.DEFAULT);
    }

    //创建文档(插入数据)
    public Boolean createUserDocument(UserDocument document) throws Exception {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        document.setId(uuid);
        document.setName("测试" + uuid);
        IndexRequest indexRequest = new IndexRequest(indexName)
                .id(document.getId())
                .source(JSON.toJSONString(document), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        RestStatus status = indexResponse.status();
        return status == RestStatus.CREATED || status == RestStatus.OK;
    }

    //批量创建文档
    public Boolean bulkCreateUserDocument(List<UserDocument> documents) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (UserDocument document : documents) {
            String id = UUID.randomUUID().toString();
            document.setId(id);
            IndexRequest indexRequest = new IndexRequest(indexName)
                    .id(id)
                    .source(JSON.toJSONString(document), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status().equals(RestStatus.OK);
    }

    //查看文档
    public UserDocument getUserDocument(String id) throws IOException {
        GetRequest getRequest1 = new GetRequest("book", "Jrn1qIEBjeGVJ444N5KB");
        GetResponse getResponse1 = client.get(getRequest1, RequestOptions.DEFAULT);
        GetRequest getRequest = new GetRequest(indexName, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        UserDocument result = new UserDocument();
        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            result = JSON.parseObject(sourceAsString, UserDocument.class);
        } else {
            log.error("没有找到该 id 的文档");
        }
        return result;
    }

    //更新文档
    public Boolean updateUserDocument(UserDocument document) throws Exception {
        UserDocument resultDocument = getUserDocument(document.getId());
        UpdateRequest updateRequest = new UpdateRequest(indexName, resultDocument.getId());
        updateRequest.doc(JSON.toJSONString(document), XContentType.JSON);
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.status().equals(RestStatus.OK);
    }

    //删除文档
    public String deleteUserDocument(String id) throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
        DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
        return response.getResult().name();
    }

    //搜索操作
    /*public List<UserDocument> searchUserByCity(String city) throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(UUID.randomUUID().toString());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("city", city);
        searchSourceBuilder.query(termQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(searchResponse);
    }*/

    //聚合搜索
    /*public List<UserCityDTO> aggregationsSearchUser() throws Exception {
        SearchRequest searchRequest = new SearchRequest(UUID.randomUUID().toString());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_city")
                .field("city")
                .subAggregation(AggregationBuilders
                        .avg("average_age")
                        .field("age"));
        searchSourceBuilder.aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        Terms byCityAggregation = aggregations.get("by_city");
        List<UserCityDTO> userCityList = new ArrayList<>();
        for (Terms.Bucket buck : byCityAggregation.getBuckets()) {
            UserCityDTO userCityDTO = new UserCityDTO();
            userCityDTO.setCity(buck.getKeyAsString());
            userCityDTO.setCount(buck.getDocCount());
            // 获取子聚合
            Avg averageBalance = buck.getAggregations().get("average_age");
            userCityDTO.setAvgAge(averageBalance.getValue());
            userCityList.add(userCityDTO);
        }
        return userCityList;
    }*/
}

