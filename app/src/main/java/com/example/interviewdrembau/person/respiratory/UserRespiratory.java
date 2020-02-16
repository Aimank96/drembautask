package com.example.interviewdrembau.person.respiratory;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.interviewdrembau.person.model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.PriorityUtilities;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserRespiratory {
    private final Application application;
    private final DatabaseReference myRef;
    private MutableLiveData<List<Person>> listOfPersonMutableLiveData = new MutableLiveData<>();

    private final String TAG = UserRespiratory.class.getSimpleName();

    public UserRespiratory(Application application, DatabaseReference myRef) {
        this.application = application;
        this.myRef = myRef;
    }

    public MutableLiveData<List<Person>> getListOfPersonMutableLiveData() {
        return listOfPersonMutableLiveData;
    }

    public void getPersonList(DatabaseReference myRef) {
        Log.d(TAG, "getPersonList: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Person> personList = new ArrayList<>();
                if (dataSnapshot.getValue() != null) {
                    Log.d(TAG, "onDataChange: " + dataSnapshot.getValue().toString());

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        try {
                            Log.d(TAG, "onDataChange: " + postSnapshot.getValue().toString());
                            JSONObject jsonObject = new JSONObject(postSnapshot.getValue().toString());
                            Person person = new Gson().fromJson(jsonObject.toString(), Person.class);
                            personList.add(person);
                            Log.d(TAG, "onDataChange: " + jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    Log.d(TAG, "Data change Value is: null ");

                }

                listOfPersonMutableLiveData.postValue(personList);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                error.toException().printStackTrace();
            }
        });


    }

    public void addPerson(Person person) {
        Log.d(TAG, "addPerson: " + myRef);
        myRef.child(person.getId()).setValue(person);
    }

    public void deletePerson(String uuid){
        myRef.child(uuid).removeValue();
    }

    public void editPerson(Person person) {
        myRef.child(person.getId()).setValue(person);
    }

    public void deleteAll() {
        myRef.removeValue();
    }
}
