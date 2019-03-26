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

        tvName = (TextView) findViewById(R.id.tvName);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);

        tvName.setText(name);
        tvScore.setText(score);
        tvDate.setText(date);
        tvTime.setText(time);

    }

    private void parseScoreData(String scoreData){

        String[] splitScoreData = scoreData.split("\\s*,\\s*");

        name = splitScoreData[0];
        score = splitScoreData[1];
        date = splitScoreData[2];
        time = splitScoreData[3];

    }

}
