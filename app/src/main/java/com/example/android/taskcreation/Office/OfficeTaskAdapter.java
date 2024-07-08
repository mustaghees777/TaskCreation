package com.example.android.taskcreation.Office;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskcreation.R;

import java.util.List;

public class OfficeTaskAdapter extends RecyclerView.Adapter<OfficeTaskAdapter.TaskTwoViewHolder> {
    private List<OfficeTask> taskList;
    private OnTaskTwoClickListener listener;

    public OfficeTaskAdapter(List<OfficeTask> taskList, OnTaskTwoClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskTwoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskTwoViewHolder holder, int position) {
        OfficeTask task = taskList.get(position);
        holder.textViewTaskName.setText(task.getTaskName());
        holder.textViewTaskDetail.setText(task.getTaskDetail());
        holder.textViewDueDate.setText(task.getDueDate());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void removeTask(OfficeTask task) {
        int position = taskList.indexOf(task);
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    class TaskTwoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTaskName, textViewTaskDetail, textViewDueDate;

        public TaskTwoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.text_view_name);
            textViewTaskDetail = itemView.findViewById(R.id.text_view_details);
            textViewDueDate = itemView.findViewById(R.id.text_view_valid_thru);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                listener.onTaskTwoClick(taskList.get(position));
            }
        }
    }

    public interface OnTaskTwoClickListener {
        void onTaskTwoClick(OfficeTask task);
    }
}
