package diam.diallo.todoapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> todos;

    public TodoRepository(Application application){
        TodoDatabase todoDatabase = TodoDatabase.getInstance(application);
        todoDao = todoDatabase.todoDao();
        todos = todoDao.getAllTodo();
    }

    public void insertTodo(Todo todo){
        new InsertTodoAsyncTask(todoDao).execute(todo);
    }

    public void updateTodo(Todo todo){
        new UpdateTodoAsyncTask(todoDao).execute(todo);
    }

    public void deletetodo(Todo todo){
        new DeleteTodoAsyncTask(todoDao).execute(todo);
    }

    public LiveData<List<Todo>> getAllTodo(){
        return todos;
    }

    private static class InsertTodoAsyncTask extends AsyncTask<Todo, Void, Void>{

        private TodoDao todoDao;

        private InsertTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.insertTodo(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends AsyncTask<Todo, Void, Void>{

        private TodoDao todoDao;

        private UpdateTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.updateTodo(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends AsyncTask<Todo, Void, Void>{

        private TodoDao todoDao;

        private DeleteTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.deleteTodo(todos[0]);
            return null;
        }
    }
}
