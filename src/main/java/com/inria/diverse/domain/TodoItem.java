package com.inria.diverse.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TodoItem.
 */
@Entity
@Table(name = "todo_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "todoitem")
public class TodoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "done")
    private Boolean done;

    @ManyToOne
    User userToDo;

    public User getUserToDo() {
        return userToDo;
    }

    public void setUserToDo(User userToDo) {
        this.userToDo = userToDo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

     
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TodoItem todoItem = (TodoItem) o;
        if(todoItem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, todoItem.id);
    }

     
    public int hashCode() {
        return Objects.hashCode(id);
    }

     
    public String toString() {
        return "TodoItem{" +
            "id=" + id +
            ", content='" + content + "'" +
            ", endDate='" + endDate + "'" +
            ", done='" + done + "'" +
            '}';
    }
}
