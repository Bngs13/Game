package com.example.bengisu.denemefragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import java.util.Random;

public class GameBoard extends Fragment {

    Random r = new Random();
    int s=0;
    float x,y;
    FloatingActionButton pause,resume,onsound,offsound;
    TextView t[] = new TextView[10];
    TextView[] p=new TextView[3];
    ImageView bucket;
    View view2;
    ValueAnimator vaBucket,vaRain,vaB;
    ValueAnimator[] va=new ValueAnimator[10];
    MediaPlayer mp;
    String[] timeArray=new String[2];
    Vibrator vibrator;
    TextSwitcher textSwitcher;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_board, container, false);
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mp=MediaPlayer.create(view.getContext(),R.raw.click);

        bucket = view.findViewById(R.id.imageView);
        view2 = view.findViewById(R.id.limit);
        pause = view.findViewById(R.id.pause);
        resume = view.findViewById(R.id.resume);
        onsound = view.findViewById(R.id.onsound);
        offsound = view.findViewById(R.id.offsound);

        t[0] = view.findViewById(R.id.t0);
        t[1] = view.findViewById(R.id.t1);
        t[2] = view.findViewById(R.id.t2);
        t[3] = view.findViewById(R.id.t3);
        t[4] = view.findViewById(R.id.t4);
        t[5] = view.findViewById(R.id.t5);
        t[6] = view.findViewById(R.id.t6);
        t[7] = view.findViewById(R.id.t7);
        t[8] = view.findViewById(R.id.t8);
        t[9] = view.findViewById(R.id.t9);

        p[0] = view.findViewById(R.id.p0);
        p[1] = view.findViewById(R.id.p1);
        p[2] = view.findViewById(R.id.p2);

        bucket.setX(getActivity().getWindowManager().getDefaultDisplay().getWidth()/2);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float initialX=event.getX();
                if(initialX>getActivity().getWindowManager().getDefaultDisplay().getWidth()/2){

                vaB=Move(bucket.getX(),getActivity().getWindowManager().getDefaultDisplay().getWidth());

                }
                else{

                    vaB=Move(bucket.getX(),0);
                }
                vaB.start();
                Score();
                return false;
            }
        });

        for (int i=0;i<10;i++)va[i]=Rain(t[i],5000);
        for (int i=0;i<3;i++)Rain(p[i],7000);

        offsound.setVisibility(View.GONE);
        onsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                onsound.setVisibility(View.GONE);
                offsound.setVisibility(View.VISIBLE);
            }
        });

        offsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onsound.setVisibility(View.VISIBLE);
                offsound.setVisibility(View.GONE);
            }
        });

           resume.setVisibility(View.GONE);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bucket.setVisibility(View.GONE);
                vaB.pause();
                ((MainActivity)getActivity()).timer.cancel();
                resume.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);


            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bucket.setVisibility(View.VISIBLE);
                    vaB.resume();
                  timeArray=((MainActivity)getActivity()).time.getText().toString().split(":");
                     ((MainActivity)getActivity())
                            .Game(Integer.parseInt(timeArray[0])*60*1000+Integer.parseInt(timeArray[1])*1000
                            ,Score());
                  resume.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);
                }
            });

         return view;


    }

    public ValueAnimator Rain(final TextView rain, final int duration){

        Display mdispl =getActivity().getWindowManager().getDefaultDisplay();
        final float maxX = mdispl.getWidth();
        final float maxY = mdispl.getHeight();
        x=r.nextFloat()*maxX;
        y=r.nextFloat()*maxY*0.7f;
        rain.setX(x);
        vaRain=ValueAnimator.ofFloat(y,maxY);
        vaRain.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @SuppressLint("Range")
            @Override
           public void onAnimationUpdate(ValueAnimator valueAnimator) {

                rain.setTranslationY((float)valueAnimator.getAnimatedValue());

            }
        });
        vaRain.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rain.setTextSize(24);
                Rain(rain,duration);
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }

        });
        vaRain.setDuration(duration);
        vaRain.start();


return vaRain;
    }
    public ValueAnimator Move(float from , float to ){

        vaBucket=ValueAnimator.ofFloat(from,to);
        vaBucket.setDuration(2000);

        vaBucket.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bucket.setTranslationX((float)valueAnimator.getAnimatedValue());
            }
        });
        return vaBucket;

    }
    public int Score() {

        for (int i=0;i<10;i++){
            if ((t[i].getX() >= bucket.getX() &&t[i].getX()<=bucket.getX()+bucket.getWidth()) &&
                    (t[i].getY()>=bucket.getY() && t[i].getY()<=bucket.getY()+bucket.getHeight())){
                s++;
               if(onsound.getVisibility()==View.VISIBLE)mp.start();
                t[i].setTextSize(t[i].getTextSize()+40);
            }
        }
        for (int i=0;i<3;i++){
        if ((p[i].getX() >= bucket.getX() &&p[i].getX()<=bucket.getX()+bucket.getWidth()) &&
                (p[i].getY()>=bucket.getY() && p[i].getY()<=bucket.getY()+bucket.getHeight())) {
            switch (i) {
                case 0:
                    if (s >= 10) s = s - 10;
                    else s = 0;
                    vibrator.vibrate(250);
                    break;
                case 1:
                     s = s+5;
                    break;

                case 2:
                    if (s >= 20) s = s - 20;
                    else s = 0;
                    vibrator.vibrate(250);
                    break;
            }


        }

        }

        return s;
    }


}
