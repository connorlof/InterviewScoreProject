package com.loftydevelopment.interviewscoreproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private String name, score, date, time;
    private TextView tvName, tvScore, tvDate, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        String scoreData = intent.getExtras().getString("scoreData");

        parseScoreData(scoreData);

        setTextViews();

    }

    private void setTextViews(){

        tvName = findViewById(R.id.tvName);
        tvScore = findViewById(R.id.tvScore);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);

        tvName.setText(name);
        tvScore.setText(score);
        tvDate.setText(date);
        tvTime.setText(time);

    }

    private void parseScoreData(String scoreData){

        String[] splitScoreData = scoreData.split("\\s*-\\s*");

        name = splitScoreData[0];
        score = splitScoreData[1];

        String[] splitDateTime = splitScoreData[2].split("\\s+");
        date = splitDateTime[0];
        time = splitDateTime[1];

    }

}
