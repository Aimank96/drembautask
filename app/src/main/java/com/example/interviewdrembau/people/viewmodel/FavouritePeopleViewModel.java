package com.example.interviewdrembau.people.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.interviewdrembau.people.model.People;
import com.example.interviewdrembau.people.respitory.PeopleRespiratory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavouritePeopleViewModel extends AndroidViewModel {
    private final FirebaseAuth mAuth;
    private PeopleRespiratory peopleRespiratory;
    private FirebaseFirestore db;
    private List<People> peopleList = new ArrayList<>();


    public FavouritePeopleViewModel(@NonNull Application application) {
        super(application);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();
        CollectionReference collection = db.collection("users/" + user.getUid() + "/favourite_person");

        peopleRespiratory = new PeopleRespiratory(application, collection);


    }

    public MutableLiveData<List<People>> getListOfPersonMutableLiveData() {
        return peopleRespiratory.getListOfPeopleMutableLiveData();
    }

    public void addPeople(People people) {
        peopleRespiratory.addPeople(people);
    }

    public void getPeopleList() {
        peopleRespiratory.getPeopleList();
    }

    public void deletePeople(String id) {
        peopleRespiratory.deletePeople(id);
    }

    public void editPeople(People people) {
        peopleRespiratory.editPeople(people);
    }


    public void setPeople(List<People> peopleList) {
        this.peopleList = peopleList;
    }

    public void deleteAll() {
        for (int i = 0; i < peopleList.size(); i++) {
            peopleRespiratory.deletePeople(peopleList.get(i).getId());
        }
    }
}
