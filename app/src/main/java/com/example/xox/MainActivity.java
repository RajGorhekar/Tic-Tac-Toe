package com.example.xox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    int p1points;
    int p2points;
    int count,undopressed;

    Boolean p1turn = true;
    TextView tvp1;
    TextView tvp2;
    Button[][] buttons= new Button[3][3];
    Button x;
    GifImageView ty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvp1= findViewById(R.id.tvp1);
        tvp2= findViewById(R.id.tvp2);
        ty = findViewById(R.id.ty);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                String s= "b"+i+j;
                int resId = getResources().getIdentifier(s,"id",getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(150);
                resetgame();
            }
        });

        findViewById(R.id.undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undo();
            }
        });
        ty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ty.setVisibility(View.GONE);
            }
        });

    }

    private void undo() {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(200);
        if(count>0 && undopressed ==0) {
            x.setText("");
            p1turn = !p1turn;
            count--;
            undopressed = 1;
        }else if(undopressed == 1){
            Toast.makeText(this, "undoes only 1 step", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(200);
        if(!(((Button)v).getText().toString().equals(""))){
            return;
        }
        if(p1turn){
            ((Button)v).setText("x");
            undopressed=0;

        } else{
            ((Button)v).setText("0");
            undopressed=0;
        }
        count++;

        if(checkforwin()){
            if(p1turn){
                p1Wins();
            } else{
                p2Wins();
            }
        }else if(count == 9){
            draw();
        }else{
            p1turn = ! p1turn;
        }
        x = (Button)v;

    }



    Boolean checkforwin(){
        String[][] a = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if(a[i][0].equals(a[i][1]) && a[i][0].equals(a[i][2]) && !a[i][0].equals("")){
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (a[0][i].equals(a[1][i]) && a[0][i].equals(a[2][i]) && !a[0][i].equals("")) {
                return true;
            }
        }
        if (a[0][0].equals(a[1][1]) && a[0][0].equals(a[2][2])&& !a[0][0].equals("")) {
            return true;
        }
        if (a[0][2].equals(a[1][1]) && a[0][2].equals(a[2][0]) && !a[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void p1Wins() {
        p1points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
        ty.setVisibility(View.VISIBLE);
        new Handler(  ).postDelayed(new Runnable(){
            @Override
            public void run(){
                ty.setVisibility(View.GONE);
            }
        },3500);
    }

    private void p2Wins() {
        p2points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
        ty.setVisibility(View.VISIBLE);
        new Handler(  ).postDelayed(new Runnable(){
            @Override
            public void run(){
                ty.setVisibility(View.GONE);
            }
        },3500);
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }


    private void resetgame() {
        p1points =0;
        p2points =0;
        resetBoard();
        updatePointsText();
    }

    private void updatePointsText() {
        tvp1.setText("Player 1: " + p1points);
        tvp2.setText("Player 2: " + p2points);
    }
    void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        count = 0;
        p1turn = true;
    }




}
