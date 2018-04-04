package com.dogethertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText input_repo,input_name;
    Button btn_sbmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btn_sbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Intent i = new Intent(MainActivity.this, Issues.class);
                    i.putExtra("user", input_name.getText().toString());
                    i.putExtra("repo", input_repo.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    private void init() {
        input_repo=findViewById(R.id.input_repo);
        input_name=findViewById(R.id.input_name);
        btn_sbmit=findViewById(R.id.submit_btn_next);
    }
    public boolean validate() {
        boolean valid = true;
        String name = input_name.getText().toString().trim();
        String repo = input_repo.getText().toString().trim();
        if (name.isEmpty()) {
            input_name.setError("enter a valid user name");
            valid = false;
        } else {
            input_name.setError(null);
        }
        if (repo.isEmpty()) {
            input_repo.setError("enter a valid repository");
            valid = false;
        } else {
            input_repo.setError(null);
        }
        return valid;
    }
}
