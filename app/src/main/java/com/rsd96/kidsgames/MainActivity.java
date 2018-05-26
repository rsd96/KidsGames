package com.rsd96.kidsgames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageView ivCard, ivMath;
    String userName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();



        ivCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, CardActivity.class);
                getUserName();
            }
        });

        ivMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, MathActivity.class);
                getUserName();
            }
        });
    }

    private void setupViews() {
        ivCard = findViewById(R.id.ivHomeCard);
        ivMath = findViewById(R.id.ivHomeMath);
    }

    private void getUserName() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enter username");
        final EditText input = new EditText(MainActivity.this);
        final Button btnStart = new Button(MainActivity.this);
        dialog.setPositiveButton("Start Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (input.getText().toString().isEmpty()) {
                    input.setError("Cannot be empty!");
                } else {
                    userName = input.getText().toString();
                    intent.putExtra("USER_NAME", userName);
                    startActivity(intent);
                }
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        btnStart.setLayoutParams(lp);
        final LinearLayout ll = new LinearLayout(MainActivity.this);
        ll.addView(input);
        ll.addView(btnStart);
        dialog.setView(ll);
        dialog.show();
    }
}
