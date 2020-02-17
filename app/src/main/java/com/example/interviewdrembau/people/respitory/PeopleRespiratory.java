package com.example.interviewdrembau.people.respitory;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.interviewdrembau.people.model.People;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeopleRespiratory {
    private final Application application;
    private CollectionReference collection;
    private final String TAG = PeopleRespiratory.class.getSimpleName();
    private MutableLiveData<List<People>> listOfPeopleMutableLiveData = new MutableLiveData<>();


    public PeopleRespiratory(Application application, CollectionReference collection) {
        this.application = application;
        this.collection = collection;
    }

    public void addPeople(People people) {
        collection.document(people.getId()).set(people).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
//                getPeopleList();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MutableLiveData<List<People>> getListOfPeopleMutableLiveData() {
        return listOfPeopleMutableLiveData;
    }

    public void getPeopleList() {
//        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<People> peopleList = new ArrayList<>();
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, "onComplete " + document.getId() + " => " + document.getData());
//                        try {
//                            JSONObject jsonObject = new JSONObject(document.getData().toString());
//                            People people = new Gson().fromJson(jsonObject.toString(), People.class);
//                            peopleList.add(people);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    Log.w(TAG, "Error getting documents.", task.getException());
//                }

        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<People> peopleList = new ArrayList<>();

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                    for (int i = 0; i < documents.size(); i++) {
                        Log.d(TAG, "onEvent: " + documents.get(i).getData());

                        try {
                            JSONObject jsonObject = new JSONObject(documents.get(i).getData().toString());
                            People people = new Gson().fromJson(jsonObject.toString(), People.class);
                            peopleList.add(people);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    }
//                    Log.d(TAG, "Current data: " +);
                } else {
                    Log.d(TAG, "Current data: null");
                }

                listOfPeopleMutableLiveData.postValue(peopleList);
            }
        });
    }


    public void deletePeople(String id) {
        Log.d(TAG, "deletePeople: " + collection.document(id).getPath());
        collection.document(id).delete();
    }

    public void editPeople(People people) {
        collection.document(people.getId()).set(people);
    }

}
