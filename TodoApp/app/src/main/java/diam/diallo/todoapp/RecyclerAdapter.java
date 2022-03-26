package diam.diallo.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    List<Todo> todos = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> tasksDate = new ArrayList<>();
    Context context;

//    public RecyclerAdapter(ArrayList<String> titles,ArrayList<String> tasksDate, Context context){
//        this.titles = titles;
//        this.tasksDate = tasksDate;
//        this.context = context;
//    }


    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {

        Todo currentTodo = todos.get(position);
        holder.title_edit.setText(currentTodo.getTodo());
        holder.taksDateEdit.setText(currentTodo.getDate());

//        holder.title_edit.setText(titles.get(position));
//        holder.taksDateEdit.setText(tasksDate.get(position));
        holder.delet_btn.setBackgroundResource(R.drawable.ic_delete);
        holder.updateBtn.setBackgroundResource(R.drawable.ic_create);
        holder.tasks_check.setChecked(false);
    }

    public void setTodos(List<Todo> todos){
        this.todos = todos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView title_edit;
        TextView taksDateEdit;
        CheckBox tasks_check;
        Button updateBtn;
        Button delet_btn;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            title_edit = itemView.findViewById(R.id.taskTitle_id);
            taksDateEdit = itemView.findViewById(R.id.dateId);
            tasks_check = itemView.findViewById(R.id.taskCheck_id);
            updateBtn = itemView.findViewById(R.id.updateId);
            delet_btn = itemView.findViewById(R.id.deletBtn_id);
        }
    }
}
