package com.relatosdepapel.relatosdepapel_buscador_ms.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.relatosdepapel.relatosdepapel_buscador_ms.repository")
public class ElasticsearchConfig {

  @Value("${elasticsearch.host}")
  private String clusterEndpoint;
  @Value("${elasticsearch.credentials.user}")
  private String username;
  @Value("${elasticsearch.credentials.password}")
  private String password;

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() {

    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY
            ,new UsernamePasswordCredentials(username, password)
    );

    return new ElasticsearchRestTemplate(
        new RestHighLevelClient(RestClient.builder(new HttpHost(clusterEndpoint, 443, "https"))
            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
              @Override
              public HttpAsyncClientBuilder customizeHttpClient(
                  HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
              }
            })));
  }
  
  @Bean
  public RestHighLevelClient restHighLevelClient() {
      final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
      credentialsProvider.setCredentials(AuthScope.ANY,
          new UsernamePasswordCredentials(username, password));

      RestClientBuilder builder = RestClient.builder(
          new HttpHost(clusterEndpoint, 443, "https"))
          .setHttpClientConfigCallback(httpClientBuilder ->
              httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

      return new RestHighLevelClient(builder);
  }

}
