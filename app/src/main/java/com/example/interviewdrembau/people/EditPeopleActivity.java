package com.example.interviewdrembau.people;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.interviewdrembau.Constants;
import com.example.interviewdrembau.R;
import com.example.interviewdrembau.databinding.ActivityEditPeopleBinding;
import com.example.interviewdrembau.databinding.LayoutPeopleBinding;
import com.example.interviewdrembau.people.model.People;

public class EditPeopleActivity extends AppCompatActivity {
    private ActivityEditPeopleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_people);
        final LayoutPeopleBinding container = binding.container;

        int age = getIntent().getIntExtra(Constants.AGE, 0);
        String name = getIntent().getStringExtra(Constants.NAME);
        String uuid = getIntent().getStringExtra(Constants.UUID);

        final People people = new People(name, age);
        people.setId(uuid);

        container.setPeople(people);


        binding.btnEditPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container.tietAge.getText() != null && container.tietName.getText() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.NAME, container.tietName.getText().toString());
                    intent.putExtra(Constants.UUID, people.getId());
                    intent.putExtra(Constants.AGE, container.tietAge.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(EditPeopleActivity.this, "Please fill name and age", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
