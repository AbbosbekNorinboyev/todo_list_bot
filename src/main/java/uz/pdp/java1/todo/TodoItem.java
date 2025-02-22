package uz.pdp.java1.todo;

import uz.pdp.java1.enums.TodoItemType;

import java.util.Date;

public class TodoItem {
    private String id;
    private String title;
    private String content;
    private Date createdDate;
    private Long userId;
    private TodoItemType type;

    public TodoItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TodoItemType getType() {
        return type;
    }

    public void setType(TodoItemType type) {
        this.type = type;
    }
}
