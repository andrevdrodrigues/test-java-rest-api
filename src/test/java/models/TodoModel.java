package models;

import lombok.Getter;
import utils.Support;

@Getter
public class TodoModel<T> extends Support {

    private T userId;
    private T id;
    private T title;
    private T completed;

    public TodoModel userId(Integer userId) {
        this.userId = (T) userId;
        return this;
    }

    public TodoModel id(Integer id) {
        this.id = (T) id;
        return this;
    }

    public TodoModel title(String title) {
        this.title = (T) title;
        return this;
    }

    public TodoModel completed(Boolean completed) {
        this.completed = (T) completed;
        return this;
    }
}
