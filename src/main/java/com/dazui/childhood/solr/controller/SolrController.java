package com.dazui.childhood.solr.controller;

import java.io.IOException;

import javax.management.Query;
import javax.servlet.http.HttpSession;

import org.apache.solr.client.solrj.SolrClient;
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

import com.dazui.childhood.alioss.service.AliOssTokenService;
import com.dazui.childhood.config.SolrCotext;

@RestController
@RequestMapping("")
public class SolrController {
	
	private Logger logger = LoggerFactory.getLogger(SolrController.class);

	@Autowired
	private SolrClient client;
	
	@GetMapping("/testSolr")
	public String testSolr(HttpSession session) {
		logger.error("[truyayong] enter testSolr");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "iii");
		doc.addField("title", new String[]{"gaga"});
		try {
			client.add(doc);
			client.commit();
			return "commit";
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("[truyayong] exception testSolr", e);
			e.printStackTrace();
		}
		return "NIL";
	
	}
}
