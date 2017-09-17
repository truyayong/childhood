package com.dazui.childhood.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages={"com.dazui.childhood"})
public class SolrCotext {

	static final String SOLR_HOST = "http://localhost:8983/solr/test";
	
	@Bean
	public HttpSolrServer solrServer() {
		return new HttpSolrServer(SOLR_HOST);
	}
}
