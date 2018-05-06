package perusahaangaram.scmgame;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuPermainan extends AppCompatActivity {
    private TextView countdowntimer;
    private Button countdownbutton;

    private CountDownTimer countDownTimer;
    private long timeLeftinMiliseconds = 300000; // 5 menit
    private boolean timeRunning;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_permainan);
        View decorView = getWindow().getDecorView();
        //hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        countdowntimer = findViewById(R.id.countdowntimer);
        countdownbutton = findViewById(R.id.countdownbutton);
        startTimer();
        countdownbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startstop();
            }
        });
    }

    public void startstop() {
        if (timeRunning) {
            stopTimer();
        } else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftinMiliseconds, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftinMiliseconds = l;
                updatetimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        countdownbutton.setText("pause");
        timeRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        countdownbutton.setText("Mulai");
        timeRunning = false;
    }

    public void updatetimer() {
        int minutes = (int) timeLeftinMiliseconds / 60000;
        int seconds = (int) timeLeftinMiliseconds % 60000 / 1000;

        String timeLeftText;

        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countdowntimer.setText(timeLeftText);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void menuAwal(View view) {
        finish();
    }

}
