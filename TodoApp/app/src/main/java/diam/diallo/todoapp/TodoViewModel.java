package diam.diallo.todoapp;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository todoRepository;
    private LiveData<List<Todo>> todos;

    public TodoViewModel(@NonNull Application application) {
        super(application);

        todoRepository = new TodoRepository(application);
        todos = todoRepository.getAllTodo();
    }

    public void insertTodo(Todo todo){
        todoRepository.insertTodo(todo);
    }

    public void updateTodo(Todo todo){
        todoRepository.updateTodo(todo);
    }

    public void deleteTodo(Todo todo){
        todoRepository.deletetodo(todo);
    }

    public LiveData<List<Todo>> getAllTodos(){
        return todos;
    }
}
