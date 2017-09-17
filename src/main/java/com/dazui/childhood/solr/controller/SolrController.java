package com.dazui.childhood.solr.controller;

import java.io.IOException;

import javax.management.Query;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
public class SolrController {
	
	private Logger logger = LoggerFactory.getLogger(SolrController.class);

	@Autowired
	private SolrClient client;
	
	@GetMapping("/testSolr")
	public String testSolr(HttpSession session) {
		logger.error("[truyayong] enter testSolr");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		query.addField("*");
		try {
			QueryResponse response = client.query(query);
			SolrDocumentList docs = response.getResults();
			client.commit();
			return "commit docs size : " + docs.size();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NIL";
	
	}
}
