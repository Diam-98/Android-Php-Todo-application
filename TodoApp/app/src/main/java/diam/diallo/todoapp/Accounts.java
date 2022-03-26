package diam.diallo.todoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class Accounts {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String email;
    public String password;

    public Accounts(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }
}
