package diam.diallo.todoapp;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


//creation de la base de donnees pour stocker les Todos
@Database(entities = Todo.class, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase todoDatabase;
    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context){
        if (todoDatabase == null){
           todoDatabase =  Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, "todo_datbase")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return  todoDatabase;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(todoDatabase).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private TodoDao todoDao;
        private PopulateDbAsyncTask(TodoDatabase database){
            todoDao = database.todoDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.insertTodo(new Todo("Sport", "2021-05-09", false));
            todoDao.insertTodo(new Todo("Ecole", "2021-05-09", false));
            todoDao.insertTodo(new Todo("Bureau", "2021-05-09", false));
            todoDao.insertTodo(new Todo("Beach", "2021-05-09", false));
            return null;
        }
    }
}
