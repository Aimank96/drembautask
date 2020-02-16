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
import com.example.interviewdrembau.databinding.ActivityAddPersonBinding;
import com.example.interviewdrembau.databinding.LayoutPersonBinding;

public class AddPersonActivity extends AppCompatActivity {

    private ActivityAddPersonBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_person);
        final LayoutPersonBinding layoutPersonBinding = binding.container;


        binding.btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutPersonBinding.tietAge.getText() != null &&layoutPersonBinding.tietName.getText()!= null){
                    Intent intent = new Intent();
                    intent.putExtra(Constants.NAME,layoutPersonBinding.tietName.getText().toString());
                    intent.putExtra(Constants.AGE,layoutPersonBinding.tietAge.getText().toString());
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(AddPersonActivity.this,"Please fill name and age",Toast.LENGTH_SHORT).show();
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
