// TaskAdapter.java
package com.example.android.taskcreation.HomeActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskcreation.R;

import java.util.ArrayList;
import java.util.List;

public class HomeTaskAdapter extends RecyclerView.Adapter<HomeTaskAdapter.TaskHolder> {
    private List<HomeTask> homeTasks = new ArrayList<>();
    private OnTaskListener onTaskListener;

    public HomeTaskAdapter(OnTaskListener onTaskListener) {
        this.onTaskListener = onTaskListener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        HomeTask currentHomeTask = homeTasks.get(position);
        holder.textViewName.setText(currentHomeTask.getName());
        holder.textViewDetails.setText(currentHomeTask.getDetails());
        holder.textViewValidThru.setText(currentHomeTask.getDueDate());
    }

    @Override
    public int getItemCount() {
        return homeTasks.size();
    }

    public void setTasks(List<HomeTask> homeTasks) {
        this.homeTasks = homeTasks;
        notifyDataSetChanged();
    }

    public HomeTask getTaskAt(int position) {
        return homeTasks.get(position);
    }

    class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private TextView textViewDetails;
        private TextView textViewValidThru;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDetails = itemView.findViewById(R.id.text_view_details);
            textViewValidThru = itemView.findViewById(R.id.text_view_valid_thru);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }

    public interface OnTaskListener {
        void onTaskClick(int position);
    }
}
