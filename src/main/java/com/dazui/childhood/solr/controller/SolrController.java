package com.dazui.childhood.solr.controller;

import java.io.IOException;

import javax.management.Query;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dazui.childhood.alioss.service.AliOssTokenService;

@RestController
@RequestMapping("")
public class SolrController {
	
	private Logger logger = LoggerFactory.getLogger(SolrController.class);

	@Autowired
	private SolrClient client;
	
	@GetMapping("/testSolr")
	public String testSolr(HttpSession session) {
		logger.error("[truyayong] enter testSolr");
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.add("q", "comPanyName:yy");
		params.add("start", "0");
		params.add("rows", "10");
		try {
			QueryResponse query = client.query(params);
			SolrDocumentList results = query.getResults();
			logger.error("[truyayong] results size : " + results.size());
			return results.toString();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("[truyayong] SolrServerException");
			e.printStackTrace();
		}
		return "NIL";
	
	}
}
