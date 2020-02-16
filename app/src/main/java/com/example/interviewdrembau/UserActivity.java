package com.example.interviewdrembau;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.interviewdrembau.databinding.ActivityUserBinding;
import com.example.interviewdrembau.person.AddPersonActivity;
import com.example.interviewdrembau.person.EditPersonActivity;
import com.example.interviewdrembau.person.FavoritePersonAdapter;
import com.example.interviewdrembau.person.model.Person;
import com.example.interviewdrembau.person.viewmodel.UserViewModel;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Random;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;


    private UserViewModel userViewModel;
    private String TAG = UserActivity.class.getSimpleName();


    private RecyclerView rvFavouritePerson;
    private FavoritePersonAdapter favoritePersonAdapter;

    private final int ADD_PERSON_REQUEST_CODE = 1001;
    private final int EDIT_PERSON_REQUEST_CODE = 1002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        setSupportActionBar(binding.toolbar);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getListOfPersonMutableLiveData().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> personList) {
                    favoritePersonAdapter.setPersonList(personList);
                    favoritePersonAdapter.notifyDataSetChanged();

                Log.d(TAG, "onChanged: list" +personList.size());


            }
        });


        userViewModel.getPersonList();


        rvFavouritePerson = binding.rvFavoritePerson;
        favoritePersonAdapter = new FavoritePersonAdapter();
        binding.rvFavoritePerson.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavoritePerson.setAdapter(favoritePersonAdapter);

        favoritePersonAdapter.setCallBack(new FavoritePersonAdapter.FavoritePersonAdapterCallback() {
            @Override
            public void onDelete(String uuid) {
                userViewModel.deletePerson(uuid);
            }

            @Override
            public void onEdit(String uuid, String name, int age) {
                Intent intent = new Intent(UserActivity.this, EditPersonActivity.class);
                intent.putExtra(Constants.UUID, uuid);
                intent.putExtra(Constants.NAME, name);
                intent.putExtra(Constants.AGE, age);
                startActivityForResult(intent, EDIT_PERSON_REQUEST_CODE);
            }


        });



        binding.btnAddDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, AddPersonActivity.class);
                startActivityForResult(intent, ADD_PERSON_REQUEST_CODE);
            }
        });

        binding.btnGenerate50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();

                String[] stringArray = getResources().getStringArray(R.array.generated_name);
                for (String name : stringArray) {
                    int age = random.nextInt(100);

                    Person person = new Person(name, age);
                    userViewModel.addPerson(person);
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
                userViewModel.deleteAll();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PERSON_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra(Constants.NAME);
            int age;
            try {
                age = Integer.valueOf(data.getStringExtra(Constants.AGE));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                age = 0;
            }
            Person person = new Person(name, age);
            userViewModel.addPerson(person);
        } else if (requestCode == EDIT_PERSON_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra(Constants.NAME);
            String personId = data.getStringExtra(Constants.UUID);
            int age;
            try {
                age = Integer.valueOf(data.getStringExtra(Constants.AGE));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                age = 0;
            }
            Person person = new Person(name, age);
            person.setId(personId);
            userViewModel.editPerson(person);        }
    }
}
