package com.rsd96.kidsgames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.ViewAnimator;

import java.util.Random;

/**
 * Created by Ramshad on 5/10/18.
 */

public class MathActivity extends AppCompatActivity{

    TextView tvTimer, tvScore, tvNum1, tvNum2, tvRighWrong;
    ImageView ivAnimal;
    String userName;
    EditText etResult;
    Button btnNext;
    Random rand = new Random();
    int num1, num2, score = 0;
    int challengeCount = 1;
    CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);


        setupViews();
        userName = getIntent().getExtras().getString("USER_NAME");
        startTimer();
        generateNumbers();
        setScore(score);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int res = Integer.parseInt(etResult.getText().toString());
                    if (res == (num1 + num2)) {
                        score += 5;
                        ivAnimal.setBackgroundDrawable(ContextCompat.getDrawable(MathActivity.this, R.drawable.monkey));
                        tvRighWrong.setText("Correct!");
                        showRightWrong();
                    } else {
                        score --;
                        ivAnimal.setBackgroundDrawable(ContextCompat.getDrawable(MathActivity.this, R.drawable.dog));
                        tvRighWrong.setText("Wrong!");
                        showRightWrong();
                    }
                    challengeCount++;
                    if (challengeCount == 20) {
                        gameOver();
                    } else {
                        generateNumbers();
                        setScore(score);
                    }

                } catch (NumberFormatException e) {
                   Toast.makeText(MathActivity.this, "Invalid Answer!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    private void showRightWrong() {

        ivAnimal.setVisibility(View.VISIBLE);
        tvRighWrong.setVisibility(View.VISIBLE);
        ViewAnimator
                .animate(ivAnimal)
                .fadeIn()
                .duration(500)
                .andAnimate(tvRighWrong)
                .fadeIn()
                .duration(500)
                .thenAnimate(ivAnimal)
                .fadeOut()
                .duration(500)
                .andAnimate(tvRighWrong)
                .fadeOut()
                .duration(500)
                .start();

    }

    private void gameOver() {
        stopTimer();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Game Over!");

        if (score < 35)
            dialog.setMessage(getResources().getString(R.string.math_less_than_35_message, userName, score, challengeCount));
        else if (score < 75)
            dialog.setMessage(getResources().getString(R.string.math_less_than_75_message, userName, score, challengeCount));
        else
            dialog.setMessage(getResources().getString(R.string.math_more_than_75_message, userName, score, challengeCount));

        dialog.setPositiveButton("Quit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MathActivity.this, MainActivity.class));
                finish();
            }
        });

        dialog.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(MathActivity.this, MathActivity.class);
                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
    }

    private void setScore(int score) {
        tvScore.setText("Score : " + score);
    }

    private void generateNumbers() {
        etResult.setText("");
        num1 = rand.nextInt(100) + 1;
        num2 = rand.nextInt(100) + 1;
        tvNum1.setText(String.valueOf(num1));
        tvNum2.setText(String.valueOf(num2));
    }

    private void stopTimer() {
        timer.cancel();
    }


    private void startTimer() {
        timer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int min = Math.round((millisUntilFinished / 1000) / 60);
                int secs = Math.round((millisUntilFinished/1000) - (min*60));
                tvTimer.setText("" + min + " : " + secs);

            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    private void setupViews() {
        tvTimer = findViewById(R.id.tvMathTimer);
        tvScore = findViewById(R.id.tvMathScore);
        tvNum1 = findViewById(R.id.tvMathNum1);
        tvNum2 = findViewById(R.id.tvMathNum2);
        etResult = findViewById(R.id.etMathAnswer);
        btnNext = findViewById(R.id.btnMathNext);
        ivAnimal = findViewById(R.id.ivMathAnimal);
        tvRighWrong = findViewById(R.id.tvMathRightAnswer);
    }


}
