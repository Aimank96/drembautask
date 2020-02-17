package com.example.interviewdrembau.people;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.interviewdrembau.Constants;
import com.example.interviewdrembau.R;
import com.example.interviewdrembau.databinding.ActivityPeopleBinding;
import com.example.interviewdrembau.people.model.People;
import com.example.interviewdrembau.people.viewmodel.FavouritePeopleViewModel;
import com.example.interviewdrembau.person.AddPersonActivity;

import java.util.List;
import java.util.Random;

public class PeopleActivity extends AppCompatActivity {

    private static final String TAG = PeopleActivity.class.getSimpleName();
    private ActivityPeopleBinding binding;
    private FavouritePeopleViewModel favouritePeopleViewModel;

    private final int ADD_PEOPLE_REQUEST_CODE = 1001;
    private final int EDIT_PEOPLE_REQUEST_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_people);

        setSupportActionBar(binding.toolbar);

        favouritePeopleViewModel = new ViewModelProvider(this).get(FavouritePeopleViewModel.class);
        final PeopleAdapter peopleAdapter = new PeopleAdapter();

        favouritePeopleViewModel.getListOfPersonMutableLiveData().observe(this, new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                Log.d(TAG, "onChanged: " + people.size());
                favouritePeopleViewModel.setPeople(people);
                peopleAdapter.setPeopleList(people);
                peopleAdapter.notifyDataSetChanged();

                Log.d(TAG, "onChanged: list" + people.size());
            }
        });

        binding.rvFavoritePeople.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavoritePeople.setAdapter(peopleAdapter);

        favouritePeopleViewModel.getPeopleList();

        peopleAdapter.setCallBack(new PeopleAdapter.FavoritePeopleAdapterCallback() {
            @Override
            public void onDelete(String id) {
                favouritePeopleViewModel.deletePeople(id);
            }

            @Override
            public void onEdit(String uuid, String name, int age) {
                Intent intent = new Intent(PeopleActivity.this, EditPeopleActivity.class);
                intent.putExtra(Constants.UUID, uuid);
                intent.putExtra(Constants.NAME, name);
                intent.putExtra(Constants.AGE, age);
                startActivityForResult(intent, EDIT_PEOPLE_REQUEST_CODE);
            }
        });

        binding.btnAddDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PeopleActivity.this, AddPersonActivity.class);
                startActivityForResult(intent, ADD_PEOPLE_REQUEST_CODE);

            }
        });

        binding.btnGenerate50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                String[] stringArray = getResources().getStringArray(R.array.generated_name);
                for (String name : stringArray) {
                    int age = random.nextInt(100);

                    People people = new People(name, age);
                    favouritePeopleViewModel.addPeople(people);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                    favouritePeopleViewModel.deleteAll();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PEOPLE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra(Constants.NAME);
            int age;
            try {
                age = Integer.valueOf(data.getStringExtra(Constants.AGE));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                age = 0;
            }
            People people = new People(name, age);
            favouritePeopleViewModel.addPeople(people);
        } else
        if (requestCode == EDIT_PEOPLE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra(Constants.NAME);
            String personId = data.getStringExtra(Constants.UUID);
            int age;
            try {
                age = Integer.valueOf(data.getStringExtra(Constants.AGE));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                age = 0;
            }
            People people = new People(name, age);
            people.setId(personId);
            favouritePeopleViewModel.editPeople(people);
        }
    }
}
