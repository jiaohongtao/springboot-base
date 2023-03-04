package com.hong.test.company.esDir;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Author: yjj
 * @Date: 2022/06/29/10:58
 * @Description:
 */
@Slf4j
@Configuration
public class ElasticsearchConfiguration {

    @Autowired
    EsConfig esConfig;

    // String nodes = "10.1.2.15:9200,10.1.2.16:9200,10.1.2.17:9200,10.1.2.18:9200";//集群节点
    // String nodes = "fe80::48f4:ab1e:b73a:88a8_9200";//集群节点
    /*String nodes = "http://fe80::48f4:ab1e:b73a:88a8_9200,http://fe80::5054:ff:fea9:eef_9200,http://fe80::2bce:e422:a01:2621_9200" +
            ",http://fe80::33e4:c7fc:b93e:e32e_9200";*/
    /*String nodes = "fe80::48f4:ab1e:b73a:88a8_9200,fe80::5054:ff:fea9:eef_9200,fe80::2bce:e422:a01:2621_9200" +
            ",fe80::33e4:c7fc:b93e:e32e_9200";*/
    String nodes = "fe80::48f4:ab1e:b73a:88a8_9200,fe80::5054:ff:fea9:eef_9200,fe80::2bce:e422:a01:2621_9200" +
            ",fe80::33e4:c7fc:b93e:e32e_9200";
    /*String nodes = "tcp://fe80::48f4:ab1e:b73a:88a8_9200,tcp://fe80::5054:ff:fea9:eef_9200,tcp://fe80::2bce:e422:a01:2621_9200" +
            ",tcp://fe80::33e4:c7fc:b93e:e32e_9200";*/

    @Bean(destroyMethod = "close", name = "client")
    public RestHighLevelClient initRestClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esConfig.getUsername(), esConfig.getPassword()));


        HttpHost[] httpHosts = Arrays.stream(nodes.split(",")).map(x -> {
            String[] hostInfo = x.split("_");
            return new HttpHost(hostInfo[0], Integer.parseInt(hostInfo[1]));
        }).toArray(HttpHost[]::new);

        // HttpHost httpHost = new HttpHost(esConfig.getHost(), esConfig.getPort());
        RestClientBuilder builder = RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                )
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(esConfig.getConnTimeout())
                        .setSocketTimeout(esConfig.getSocketTimeout())
                        .setConnectionRequestTimeout(esConfig.getConnectionRequestTimeout()));
        return new RestHighLevelClient(builder);
    }

    // 注册 rest高级客户端
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esConfig.getUsername(), esConfig.getPassword()));

        HttpHost[] httpHosts = Arrays.stream(nodes.split(",")).map(x -> {
            String[] hostInfo = x.split("_");
            return new HttpHost(hostInfo[0], Integer.parseInt(hostInfo[1]));
        }).toArray(HttpHost[]::new);

        // HttpHost httpHost = new HttpHost(esConfig.getHost(), esConfig.getPort(), "http");
        return new RestHighLevelClient(
                RestClient.builder(httpHosts).setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );
    }
}

