package com.inria.diverse.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.inria.diverse.domain.TodoItem;
import com.inria.diverse.domain.User;
import com.inria.diverse.repository.TodoItemRepository;
import com.inria.diverse.repository.UserRepository;
import com.inria.diverse.repository.search.TodoItemSearchRepository;
import com.inria.diverse.security.SecurityUtils;
import com.inria.diverse.web.rest.util.HeaderUtil;
import com.inria.diverse.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TodoItem.
 */
@RestController
@RequestMapping("/api")
public class TodoItemResource {

    private final Logger log = LoggerFactory.getLogger(TodoItemResource.class);

    @Inject
    private TodoItemRepository todoItemRepository;
    @Inject
    private UserRepository userRepository;

    @Inject
    private TodoItemSearchRepository todoItemSearchRepository;

    /**
     * POST  /todoItems -> Create a new todoItem.
     */
    @RequestMapping(value = "/todoItems",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TodoItem> createTodoItem(@Valid @RequestBody TodoItem todoItem) throws URISyntaxException {
        log.debug("REST request to save TodoItem : {}", todoItem);
        User u = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        todoItem.setUserToDo(u);

        if (todoItem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("todoItem", "idexists", "A new todoItem cannot already have an ID")).body(null);
        }
//        todoItem.setUserToDo(        SecurityUtils.);

        TodoItem result = todoItemRepository.save(todoItem);
        todoItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/todoItems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("todoItem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /todoItems -> Updates an existing todoItem.
     */
    @RequestMapping(value = "/todoItems",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TodoItem> updateTodoItem(@Valid @RequestBody TodoItem todoItem) throws URISyntaxException {
        log.debug("REST request to update TodoItem : {}", todoItem);
        if (todoItem.getId() == null) {
            return createTodoItem(todoItem);
        }
        TodoItem result = todoItemRepository.save(todoItem);
        todoItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("todoItem", todoItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /todoItems -> get all the todoItems.
     */
    @RequestMapping(value = "/todoItems",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TodoItem>> getAllTodoItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TodoItems");



        Page<TodoItem> page = todoItemRepository.findAllbyUser(SecurityUtils.getCurrentUserLogin(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/todoItems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /todoItems/:id -> get the "id" todoItem.
     */
    @RequestMapping(value = "/todoItems/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TodoItem> getTodoItem(@PathVariable Long id) {
        log.debug("REST request to get TodoItem : {}", id);
        TodoItem todoItem = todoItemRepository.findOne(id);
        return Optional.ofNullable(todoItem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /todoItems/:id -> delete the "id" todoItem.
     */
    @RequestMapping(value = "/todoItems/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        log.debug("REST request to delete TodoItem : {}", id);
        todoItemRepository.delete(id);
        todoItemSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("todoItem", id.toString())).build();
    }

    /**
     * SEARCH  /_search/todoItems/:query -> search for the todoItem corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/todoItems/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TodoItem> searchTodoItems(@PathVariable String query) {
        log.debug("REST request to search TodoItems for query {}", query);
        return StreamSupport
            .stream(todoItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
