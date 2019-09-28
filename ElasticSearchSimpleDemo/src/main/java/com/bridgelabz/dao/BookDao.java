package com.bridgelabz.dao;

import com.bridgelabz.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BookDao {

  private final String INDEX = "bookdata";
  private final String TYPE = "books";  
  private RestHighLevelClient restHighLevelClient;
  private ObjectMapper objectMapper;
  private List booksList = new ArrayList();

  public BookDao( ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
    this.objectMapper = objectMapper;
    this.restHighLevelClient = restHighLevelClient;
  }
  public Book insertBook(Book book){
	  book.setId(UUID.randomUUID().toString());
	  Map dataMap = objectMapper.convertValue(book, Map.class);
	  IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, book.getId())
	                .source(dataMap);
	  try {
	    @SuppressWarnings("deprecation")
		IndexResponse response = restHighLevelClient.index(indexRequest);
	  } catch(ElasticsearchException e) {
	    e.getDetailedMessage();
	  } catch (java.io.IOException ex){
	    ex.getLocalizedMessage();
	  }
	  return book;
	}
  public Map<String, Object> getBookById(String id){
	  GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
	  GetResponse getResponse = null;
	  try {
	    getResponse = restHighLevelClient.get(getRequest);
	  } catch (java.io.IOException e){
	    e.getLocalizedMessage();
	  }
	  Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
	  return sourceAsMap;
	}
}
