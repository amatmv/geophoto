package org.udg.pds.todoandroid.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.udg.pds.todoandroid.R;
import org.udg.pds.todoandroid.TodoApp;
import org.udg.pds.todoandroid.activity.AddTask;
import org.udg.pds.todoandroid.entity.Task;
import org.udg.pds.todoandroid.rest.TodoApi;
import org.udg.pds.todoandroid.util.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imartin on 12/02/16.
 */
public class TaskList extends Fragment {

    TodoApi mTodoService;

    RecyclerView mRecyclerView;
    private TRAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.task_list, container, false);
    }

    @Override
    public void onStart() {

        super.onStart();
        mTodoService = ((TodoApp) this.getActivity().getApplication()).getAPI();

        mRecyclerView = getView().findViewById(R.id.task_recyclerview);
        mAdapter = new TRAdapter(this.getActivity().getApplication());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        Button b = getView().findViewById(R.id.b_add_task_rv);
        // This is the listener to the "Add Task" button
        b.setOnClickListener(view -> {
            // When we press the "Add Task" button, the AddTask activity is called, where
            // we can introduce the data of the new task
            Intent i = new Intent(TaskList.this.getContext(), AddTask.class);
            // We launch the activity with startActivityForResult because we want to know when
            // the launched activity has finished. In this case, when the AddTask activity has finished
            // we will update the list to show the new task.
            startActivityForResult(i, Global.RQ_ADD_TASK);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        this.updateTaskList();
    }

    public void showTaskList(List<Task> tl) {
        mAdapter.clear();
        for (Task t : tl) {
            mAdapter.add(t);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Global.RQ_ADD_TASK) {
            this.updateTaskList();
        }
    }

    public void updateTaskList() {

        Call<List<Task>> call = mTodoService.getTasks();

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    TaskList.this.showTaskList(response.body());
                } else {
                    Toast.makeText(TaskList.this.getContext(), "Error reading tasks", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView description;
        TextView dateLimit;
        View view;

        TaskViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            description = (TextView) itemView.findViewById(R.id.itemDescription);
            dateLimit = (TextView) itemView.findViewById(R.id.itemDateLimit);
        }
    }

    static class TRAdapter extends RecyclerView.Adapter<TaskList.TaskViewHolder> {

        List<Task> list = new ArrayList<>();
        Context context;

        public TRAdapter(Context context) {
            this.context = context;
        }

        @Override
        public TaskList.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
            TaskList.TaskViewHolder holder = new TaskList.TaskViewHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(TaskList.TaskViewHolder holder, final int position) {
            holder.description.setText(list.get(position).text);
            holder.dateLimit.setText(list.get(position).dateLimit.toString());

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, String.format("Hey, I'm item %1d", position), duration);
                    toast.show();
                }
            });

            holder.dateLimit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, "adios!", duration);
                    toast.show();
                }
            });

            animate(holder);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {

            super.onAttachedToRecyclerView(recyclerView);
        }

        // Insert a new item to the RecyclerView
        public void insert(int position, Task data) {
            list.add(position, data);
            notifyItemInserted(position);
        }

        // Remove a RecyclerView item containing the Data object
        public void remove(Task data) {
            int position = list.indexOf(data);
            list.remove(position);
            notifyItemRemoved(position);
        }

        public void animate(RecyclerView.ViewHolder viewHolder) {
            final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.anticipate_overshoot_interpolator);
            viewHolder.itemView.setAnimation(animAnticipateOvershoot);
        }

        public void add(Task t) {
            list.add(t);
            this.notifyItemInserted(list.size() - 1);
        }

        public void clear() {
            int size = list.size();
            list.clear();
            this.notifyItemRangeRemoved(0, size);
        }
    }
}
