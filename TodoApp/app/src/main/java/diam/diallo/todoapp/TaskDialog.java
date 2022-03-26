package diam.diallo.todoapp;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDialog extends AppCompatDialogFragment {

    EditText edit_task, taskDate;
    CheckBox doneTask;
    TodoViewModel todoViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fields, null);
        alert.setView(view).setTitle("Tache a faire").setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Inserer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertTodo();
                todoViewModel = new ViewModelProvider.AndroidViewModelFactory(new Application()).create(TodoViewModel.class);
            }
        });
        taskDate = view.findViewById(R.id.taskDateId);
        doneTask = view.findViewById(R.id.doneId);
        edit_task = view.findViewById(R.id.task_describ_id);
        return alert.create();
    }

    public void insertTodo(){
        String taskText = edit_task.getText().toString().trim();
        String taskDateText = taskDate.getText().toString().trim();
        Boolean taskDone = false;
        if(doneTask.isChecked()){
            taskDone = true;
        }else{
            taskDone = false;
        }

        Boolean finalTaskDone = taskDone;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApisUrls.INSERTTODO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("error")){
                        Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                Todo todo = new Todo(taskText, taskDateText, finalTaskDone);
                todoViewModel.insertTodo(todo);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", taskDateText);
                params.put("todo", taskText);
                params.put("done", finalTaskDone.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
