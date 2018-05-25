package perusahaangaram.scmgame;


import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import perusahaangaram.scmgame.database.Contract;

public class MenuPermainan extends AppCompatActivity {
    private TextView countdowntimer;
    private Button countdownbutton;

    private CountDownTimer countDownTimer;
    private long timeLeftinMiliseconds = 300000; // 5 menit
    private boolean timeRunning;

    private boolean firstStart = true;
    private boolean isInputNama = true;
    private boolean allowedName = true;

    //objek di dalam dialog awal main
    private TextView pengguna, simpan, batal, error, teks1, teks2, teks3;
    private EditText inputNama;
    private RelativeLayout layoutDialog;

    //nama player
    private String nama;

    public void init() {

        teks1 = (TextView) findViewById(R.id.teks1);
        teks2 = (TextView) findViewById(R.id.teks2);
        teks3 = (TextView) findViewById(R.id.teks3);
        pengguna = (TextView) findViewById(R.id.pengguna);
        simpan = (TextView) findViewById(R.id.simpan);
        batal = (TextView) findViewById(R.id.batal);
        error = (TextView) findViewById(R.id.teksError);
        inputNama = (EditText) findViewById(R.id.inputNama);
        layoutDialog = (RelativeLayout) findViewById(R.id.dialog);

    }

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
        countdownbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startstop();
            }
        });
        hideS();

        init();
        if (firstStart) {
            layoutDialog.setVisibility(View.VISIBLE);
        } else {
            layoutDialog.setVisibility(View.GONE);
            startstop();
        }
        initOnClickCallback();
        error.setText("");
        inputNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int after) {
                error.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0) {
                    if (cekChar(editable.toString())) {
                        error.setText("nama tidak diperbolehkan");
                    }
                } else {

                }
            }

            private boolean cekChar(String s) {
                String bannedChar = "1234567890`~!@#$%^&*()_+-=[]\\{}|;':,./<>?\"";
                for (int i = 0; i < s.length(); i++) {
                    for (int j = 0; j < bannedChar.length(); j++) {
                        if (s.charAt(i) == bannedChar.charAt(j)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                return false;
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

    private void hideS() {
        View decorView = getWindow().getDecorView();
        //hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void initOnClickCallback() {

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputNama) {
                    if (inputNama.getText().toString().trim().equalsIgnoreCase("")) {
                        error.setText("Nama Harus Diisi");
                    } else {
                        nama = inputNama.getText().toString().trim();
                        dialog("next");
                        isInputNama = false;
                    }
                } else {
                    dialog("close");
                }
                hideS();
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void dialog(String kondisi) {
        switch (kondisi) {
            case "next":
                simpanNama();
                startTimer();
                pengguna.setText(nama);
                batal.setVisibility(View.GONE);
                inputNama.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                teks1.setText("Halo " + nama);
                teks2.setText("Capailah target pendapatan yang harus kalian dapatkan");
                teks3.setText("Selamat Bermain");
                simpan.setText("Lewati");
                break;
            case "close":
                firstStart = false;
                layoutDialog.setVisibility(View.GONE);
                break;
            default:
                ;
        }
    }

    private void simpanNama() {
        // nama koin coklat kakao bibit poly
        String nama = inputNama.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put(Contract.Pemain.KOLOM_NAMA, nama);

        Uri uri = getContentResolver().insert(Contract.Pemain.CONTENT_URI, values);
        if (uri == null) {
            Toast.makeText(this, "gagal menyimpan nama", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "berhasil menyimpan nama", Toast.LENGTH_SHORT).show();
        }
    }


}
