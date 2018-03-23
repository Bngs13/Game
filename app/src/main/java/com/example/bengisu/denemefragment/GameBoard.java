package com.example.bengisu.denemefragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class GameBoard extends Fragment {

    Random r = new Random();
    int s=0;
    int m;
    float x,y;
    FloatingActionButton pause,resume;
    TextView t[] = new TextView[10];
    ImageView bucket;
    View view2;
    ValueAnimator vaBucket,vaRain,vaB;
    ValueAnimator[] va=new ValueAnimator[10];

   // MediaPlayer ring=MediaPlayer.create(getContext(),R.raw.click);

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_board, container, false);

        bucket = view.findViewById(R.id.imageView);
        view2 = view.findViewById(R.id.limit);
        pause = view.findViewById(R.id.pause);
        resume = view.findViewById(R.id.resume);
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


        vaB = Move();


         //   for (int j = 0; j < 10; j++){
         //  while (!GameBoard.this.isHidden()){
               // m=r.nextInt(10);
                for (int i=0;i<10;i++)va[i]=Rain(t[i]);
        for (int i=0;i<r.nextInt(10);i++) va[i].cancel();


            resume.setVisibility(View.INVISIBLE);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaB.pause();
                ((MainActivity)getActivity()).timer.cancel();
                for (int i=0;i<m;i++)va[i].pause();
                resume.setVisibility(View.VISIBLE);
                pause.setVisibility(View.INVISIBLE);
            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vaB.resume();
                    for (int i = 0; i < m; i++) va[i].resume();
                    resume.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                }
            });

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

              vaB.reverse();
                    return false;
                }
            });

        vaB.start();

            return view;


    }

    public ValueAnimator Rain(final TextView rain){

        Display mdispl =getActivity().getWindowManager().getDefaultDisplay();
        final float maxX = mdispl.getWidth();
        final float maxY = mdispl.getHeight();
        x=r.nextFloat()*maxX;
        y=r.nextFloat()*maxY*0.7f;
        rain.setX(x);
        vaRain=ValueAnimator.ofFloat(y,maxY);
        vaRain.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
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
          //      for(int i=0;i<r.nextInt(10);i++)
                   // Rain(t[i]);
                Rain(rain);
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }

        });
        vaRain.setDuration(5000);
        vaRain.start();


return vaRain;
    }
    public ValueAnimator Move(){

        vaBucket=ValueAnimator.ofFloat(0,getActivity().getWindowManager().getDefaultDisplay().getWidth());
        vaBucket.setDuration(4000);

        vaBucket.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bucket.setTranslationX((float)valueAnimator.getAnimatedValue());
            }
        });

        vaBucket.setRepeatCount(ValueAnimator.RESTART);
        return vaBucket;

    }
    public int Scor() {

        for (int i=0;i<10;i++){
            if ((t[i].getX() >= bucket.getX() &&t[i].getX()<=bucket.getX()+bucket.getWidth()) &&
                    (t[i].getY()>=bucket.getY() && t[i].getY()<=bucket.getY()+bucket.getHeight())){
                s++;
                //ring.start();
                t[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP,t[i].getTextSize()+40);

            }

        }

        return s;
    }


}
