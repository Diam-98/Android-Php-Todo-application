package diam.diallo.todoapp;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Utilisation d'une base de donnees locale permettant de stocker les taches en hors connexion

//l'entite accounts qui contient toutes notes enregistrees en hors connexion
@Entity(tableName = "todoNote")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public int todoId;
    public String todo;
    public String date;
    public boolean done;

    public Todo(String todo, String date, boolean done) {
        this.todo = todo;
        this.date = date;
        this.done = done;
    }

    public int getTodoId() {
        return todoId;
    }

    public String getTodo() {
        return todo;
    }

    public String getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }
}
