package com.example.interviewdrembau.person;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interviewdrembau.databinding.ItemPersonBinding;
import com.example.interviewdrembau.person.model.Person;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class FavoritePersonAdapter extends RecyclerView.Adapter<FavoritePersonAdapter.ViewHolder> {
    private List<Person> personList = new ArrayList<>();
    private FavoritePersonAdapterCallback callBack;

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPersonBinding itemBinding = ItemPersonBinding.inflate(layoutInflater, parent, false);

        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(personList.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public void setCallBack(FavoritePersonAdapterCallback callBack) {
        this.callBack = callBack;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPersonBinding binding;

        public ViewHolder(@NonNull ItemPersonBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Person person = personList.get(getAdapterPosition());
                    MaterialAlertDialogBuilder alertDialogBuilder;
                    alertDialogBuilder = new MaterialAlertDialogBuilder(v.getContext());

                    alertDialogBuilder.setTitle("Please choose action");
                    alertDialogBuilder.setMessage("for user id\n" + person.getId() +
                            "\n\nand user name\n" + person.getName());

                    alertDialogBuilder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callBack.onDelete(person.getId());
                        }
                    });

                    alertDialogBuilder.setNegativeButton("edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callBack.onEdit(person.getId(),person.getName(),person.getAge());
                        }
                    });

                    alertDialogBuilder.show();
                }
            });

        }

        public void bind(Person person) {
            binding.setPerson(person);
            binding.executePendingBindings();
        }
    }

    public interface FavoritePersonAdapterCallback{
        void onDelete(String uuid);

        void onEdit(String uuid,String name,int age);
    }
}
