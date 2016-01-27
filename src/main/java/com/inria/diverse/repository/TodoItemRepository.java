package com.inria.diverse.repository;

import com.inria.diverse.domain.TodoItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TodoItem entity.
 */
public interface TodoItemRepository extends JpaRepository<TodoItem,Long> {



    @Query("select todo from TodoItem as todo where todo.userToDo.login = :login")
    Page<TodoItem> findAllbyUser(String login, Pageable page);
}
