package com.example.bengisu.denemefragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView time,Gscor,Sscor;
    FloatingActionButton repeat,pause,resume;
    GameBoard gameFragment;
    ScorBoard scorFragment;
    FragmentManager fm;
    FragmentTransaction ft;
    CountDownTimer timer;
    boolean isPaused,tick;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.time);
        Gscor = findViewById(R.id.scor);
        Sscor = findViewById(R.id.Sscor);
        repeat= findViewById(R.id.repeat);
        pause = findViewById(R.id.pause);
        resume = findViewById(R.id.resume);

        fm = getSupportFragmentManager();
        ft=this.getSupportFragmentManager().beginTransaction();

        gameFragment= (GameBoard) fm.findFragmentById(R.id.gameBoard);
        scorFragment=(ScorBoard) fm.findFragmentById(R.id.scorBoard);

        Game();


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideFragment(scorFragment);
                ShowFragment(gameFragment);
                Gscor.setVisibility(View.VISIBLE);
                gameFragment.getFragmentManager().beginTransaction().detach(gameFragment).attach(gameFragment).commit();
                Game();
            }
        });
    }

    public void Game(){

        gameFragment.s=0;
      timer= new CountDownTimer(120000,1000) {

            public void onTick(long millisUntilFinished) {
                tick=true;
                HideFragment(scorFragment);
                ShowFragment(gameFragment);
                gameFragment.s = gameFragment.Scor();
               String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", (millisUntilFinished / 1000) / 60,
                        (millisUntilFinished / 1000) % 60);
                time.setText(""+timeLeftFormatted);
                Gscor.setText("" + gameFragment.s);
                               }

            public void onFinish() {
                tick=false;
                ShowFragment(scorFragment);
                HideFragment(gameFragment);
                isPaused=false;
                tick=false;
                Gscor.setVisibility(View.INVISIBLE);
                Sscor.setText("Your Scor is : "+Gscor.getText());

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

