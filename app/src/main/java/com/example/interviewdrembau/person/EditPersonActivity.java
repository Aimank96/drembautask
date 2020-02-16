package com.example.interviewdrembau.person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.interviewdrembau.Constants;
import com.example.interviewdrembau.R;
import com.example.interviewdrembau.databinding.ActivityEditPersonBinding;
import com.example.interviewdrembau.databinding.LayoutPersonBinding;
import com.example.interviewdrembau.person.model.Person;

public class EditPersonActivity extends AppCompatActivity {
    private ActivityEditPersonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_person);
        final LayoutPersonBinding container = binding.container;

        int age = getIntent().getIntExtra(Constants.AGE, 0);
        String name = getIntent().getStringExtra(Constants.NAME);
        String uuid = getIntent().getStringExtra(Constants.UUID);

        final Person person = new Person(name, age);
        person.setId(uuid);

        container.setPerson(person);

        binding.btnEditPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container.tietAge.getText() != null && container.tietName.getText() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.NAME, container.tietName.getText().toString());
                    intent.putExtra(Constants.UUID, person.getId());
                    intent.putExtra(Constants.AGE, container.tietAge.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(EditPersonActivity.this, "Please fill name and age", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
