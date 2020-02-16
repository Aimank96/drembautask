package com.example.interviewdrembau.person.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.interviewdrembau.person.model.Person;
import com.example.interviewdrembau.person.respiratory.UserRespiratory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    private MutableLiveData<List<Person>> listOfPersonMutableLiveData = new MutableLiveData<>();
    private UserRespiratory userRespiratory;
    private final String TAG = UserViewModel.class.getSimpleName();

    public UserViewModel(@NonNull Application application) {
        super(application);


        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        myRef = database.getReference("/users/" + user.getUid() + "/favourite_person");

        userRespiratory = new UserRespiratory(application, myRef);
        listOfPersonMutableLiveData = userRespiratory.getListOfPersonMutableLiveData();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG, "onAuthStateChanged: " + firebaseAuth.getUid());
            }
        });

    }

    public MutableLiveData<List<Person>> getListOfPersonMutableLiveData() {
        return listOfPersonMutableLiveData;
    }

    public void getPersonList() {
        userRespiratory.getPersonList(myRef);

    }

    public void addPerson(Person person) {
        userRespiratory.addPerson(person);
    }

    public void deletePerson(String uuid) {
        userRespiratory.deletePerson(uuid);
    }

    public void editPerson(Person person){
        userRespiratory.editPerson(person);

    }

    public void deleteAll() {
        userRespiratory.deleteAll();
    }

}
