package com.example.bengisu.denemefragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView time,Gscore,Sscore,extra;
    FloatingActionButton repeat,pause,resume;
    GameBoard gameFragment;
    ScoreBoard scoreFragment;
    FragmentManager fm;
    FragmentTransaction ft;
    CountDownTimer timer;
    Intent intent;
    String name;
    ViewFlipper flipper;
    Animation in,out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.time);
        Gscore = findViewById(R.id.score);
        Sscore = findViewById(R.id.Sscore);
        repeat= findViewById(R.id.repeat);
        pause = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);

        flipper= findViewById(R.id.flipper);
        in= AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        out=AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);


        intent=getIntent();
        name=intent.getStringExtra(StartActivity.EXTRA_MESSAGE);

        fm = getSupportFragmentManager();
      //  ft=this.getSupportFragmentManager().beginTransaction();
        ft=fm.beginTransaction();
        gameFragment= (GameBoard) fm.findFragmentById(R.id.gameBoard);
        scoreFragment=(ScoreBoard) fm.findFragmentById(R.id.scoreBoard);

        Game(60000,0);


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideFragment(scoreFragment);
                ShowFragment(gameFragment);
                Gscore.setVisibility(View.VISIBLE);
                gameFragment.getFragmentManager().beginTransaction().detach(gameFragment).attach(gameFragment).commit();
                Game(60000,0);
                flipper.showNext();
            }
        });
    }

public void Game(long m,int s){

    gameFragment.s=s;
    timer= new CountDownTimer(m,1000) {

        public void onTick(long millisUntilFinished) {

            HideFragment(scoreFragment);
            ShowFragment(gameFragment);
            gameFragment.s = gameFragment.Score();
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",
                    (millisUntilFinished / 1000) / 60,
                    (millisUntilFinished / 1000) % 60);
            time.setText(""+timeLeftFormatted);
            Gscore.setText(""+gameFragment.s);
            if(millisUntilFinished<=30000){
                time.setTextColor(Color.BLACK);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    time.setTextColor(Color.WHITE);
                }
            },500);

            }
            else time.setTextColor(Color.BLACK);
                    }

        public void onFinish() {

            ShowFragment(scoreFragment);
            HideFragment(gameFragment);
            flipper.showNext();
            Gscore.setVisibility(View.INVISIBLE);
            Sscore.setText(name+"'s Score : "+Gscore.getText());

        }

    };

    timer.start();

}


    public void HideFragment(Fragment fragment){

        ft.hide(fragment);
        ft.commitNow();

    }

    public void ShowFragment(Fragment fragment){

        ft.show(fragment);
        ft.commitNow();
    }

}

