package com.loftydevelopment.interviewscoreproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class ScoreGeneratorActivity extends AppCompatActivity {

    private ArrayList<String> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_generator);

        scoreList = generateSampleData();

    }

    public void viewScoreOnClick(View view){

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("scoreData", randomScore());
        startActivity(intent);

    }

    private String randomScore(){

        Random random = new Random();
        int randomIndex = random.nextInt(scoreList.size());

        return scoreList.get(randomIndex);

    }

    private ArrayList<String> generateSampleData(){

        ArrayList<String> sampleScoreList = new ArrayList();

        sampleScoreList.add("Ryan, 85%, 3/30/19, 05:25:12");
        sampleScoreList.add("Melissa, 90%, 9/21/19, 02:34:22");
        sampleScoreList.add("Sam, 98%, 10/1/19, 04:45:18");
        sampleScoreList.add("Connor, 100%, 3/26/19, 01:43:05");

        return sampleScoreList;

    }
}
