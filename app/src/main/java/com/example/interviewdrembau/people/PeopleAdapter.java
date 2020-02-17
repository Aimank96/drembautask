package com.example.interviewdrembau.people;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interviewdrembau.databinding.ItemPeopleBinding;
import com.example.interviewdrembau.people.model.People;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<People> peopleList = new ArrayList<>();
    private FavoritePeopleAdapterCallback callBack;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPeopleBinding binding = ItemPeopleBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(peopleList.get(holder.getAdapterPosition()));
    }


    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
    }

    public void setCallBack(FavoritePeopleAdapterCallback callBack) {
        this.callBack = callBack;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPeopleBinding binding;

        public ViewHolder(ItemPeopleBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final People people = peopleList.get(getAdapterPosition());
                    MaterialAlertDialogBuilder alertDialogBuilder;
                    alertDialogBuilder = new MaterialAlertDialogBuilder(v.getContext());

                    alertDialogBuilder.setTitle("Please choose action");
                    alertDialogBuilder.setMessage("for user id\n" + people.getId() +
                            "\n\nand user name\n" + people.getName());

                    alertDialogBuilder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callBack.onDelete(people.getId());
                        }
                    });

                    alertDialogBuilder.setNegativeButton("edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callBack.onEdit(people.getId(), people.getName(), people.getAge());
                        }
                    });

                    alertDialogBuilder.show();
                }
            });
        }

        public void bind(People people) {
            binding.setPeople(people);
            binding.executePendingBindings();
        }
    }

    public interface FavoritePeopleAdapterCallback {
        void onDelete(String uuid);

        void onEdit(String uuid, String name, int age);
    }
}
