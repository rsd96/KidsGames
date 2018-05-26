package com.rsd96.kidsgames;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ramshad on 5/10/18.
 */

public class CardActivity extends AppCompatActivity {

    TextView tvTimer, tvScore;
    CountDownTimer timer;
    String userName;
    int tryCount = 0;
    int matchCount = 0;
    int score = 0;
    GridView gridView;
    ArrayList<Card> cardList = new ArrayList<>();
    Random rand;
    CardsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        userName = getIntent().getExtras().getString("USER_NAME");
        rand = new Random();
        rand.setSeed(123456789);
        adapter = new CardsAdapter(this, cardList);
        setupViews();
        setupCandidateCards();
        showCards();
        startTimer();
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showCards() {
        for ( Card x : cardList)
            x.setShowCard(true);

        adapter.notifyDataSetChanged();
        new CountDownTimer(1000, 100) {

            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                for ( Card x : cardList)
                    x.setShowCard(false);

                adapter.notifyDataSetChanged();
            }
        }.start();
    }

    private void setupCandidateCards() {

        int max = 6;
        int min = 1;
        int diff = max - min;
        for (int i = 0; i < 12; i++) {
            int id = rand.nextInt(diff + 1);
            id += min;
            if (!isPaired(id)) {
                Card card = new Card();
                card.setCardImage(this.getResources().getDrawable(this.getResources().getIdentifier("card_"+id, "drawable", this.getPackageName())));
                card.setId(id);
                card.showCard = false;
                Log.d("Card", String.valueOf(id));
                cardList.add(card);
            } else {
                i--;
            }
        }
    }

    private boolean isPaired(int id) {
        int counter = 0;
        for (Card x : cardList) {
            if (x.id == id) {
                counter++;
                if (counter >= 2)
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    private void setScore(int score) {
        tvScore.setText("Score : " + score);
    }

    public void compareCards(ArrayList<Integer> selectedCards) {
        tryCount++;
        if (cardList.get(selectedCards.get(0)).id == cardList.get(selectedCards.get(1)).id) {
            cardList.get(selectedCards.get(0)).showCard = true;
            cardList.get(selectedCards.get(1)).showCard = true;
            matchCount++;
            score += 10;
        } else {
            score-=2;
        }
        setScore(score);

        if (matchCount == 6) {
            gameOver();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 500);
    }

    private void setupViews() {
        tvTimer = findViewById(R.id.tvCardTimer);
        gridView = findViewById(R.id.gvCards);
        tvScore = findViewById(R.id.tvCardScore);
    }

    private void gameOver() {
        stopTimer();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Game Over!");

        if (score < 20)
            dialog.setMessage(getResources().getString(R.string.card_less_than_20_message, userName, score, tryCount));
        else if (score < 40)
            dialog.setMessage(getResources().getString(R.string.card_less_than_40_message, userName, score, tryCount));
        else
            dialog.setMessage(getResources().getString(R.string.card_more_than_40_message, userName, score, tryCount));

        dialog.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(CardActivity.this, MainActivity.class));
                finish();
            }
        });

        dialog.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(CardActivity.this, CardActivity.class);
                intent.putExtra("USER_NAME", userName);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();
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
}
