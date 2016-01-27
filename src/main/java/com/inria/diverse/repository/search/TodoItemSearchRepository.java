package com.inria.diverse.repository.search;

import com.inria.diverse.domain.TodoItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TodoItem entity.
 */
public interface TodoItemSearchRepository extends ElasticsearchRepository<TodoItem, Long> {
}
