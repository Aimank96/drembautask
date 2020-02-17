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
import com.example.interviewdrembau.databinding.ActivityAddPeopleBinding;
import com.example.interviewdrembau.databinding.LayoutPeopleBinding;

public class AddPeopleActivity extends AppCompatActivity {
    private ActivityAddPeopleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_people);
        final LayoutPeopleBinding layoutPeopleBinding = binding.container;

        binding.btnAddPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPeopleBinding.tietAge.getText() != null && layoutPeopleBinding.tietName.getText() != null) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.NAME, layoutPeopleBinding.tietName.getText().toString());
                    intent.putExtra(Constants.AGE, layoutPeopleBinding.tietAge.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddPeopleActivity.this, "Please fill name and age", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }
}
