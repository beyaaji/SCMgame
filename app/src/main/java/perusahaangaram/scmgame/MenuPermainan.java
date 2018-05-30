package perusahaangaram.scmgame;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import perusahaangaram.scmgame.database.Contract;
import perusahaangaram.scmgame.objek.tempat;

public class MenuPermainan extends AppCompatActivity {
    private TextView countdowntimer;
    private Button countdownbutton;

    private CountDownTimer countDownTimer;
    private long timeLeftinMiliseconds = 600000; // 10 menit (1000milisx60= 60000 x 10 = 600000
    private boolean timeRunning;

    private boolean firstStart = true;
    private boolean isInputNama = true;
    private boolean allowedName = true;

    //objek di dalam dialog awal main
    private TextView pengguna, simpan, batal, error, teks1, teks2, teks3;
    private EditText inputNama;
    private RelativeLayout layoutDialog;

    private ImageView ikonStore[] = new ImageView[5];
    private ImageView ikonRumah[] = new ImageView[7];
    private ImageView notif_IkonStore[] = new ImageView[5];
    private ImageView notif_IkonRumah[] = new ImageView[7];
    private TextView koin;

    private int[] resStore = {
            R.id.S_1, R.id.S_2, R.id.S_3, R.id.S_4, R.id.S_5
    };
    private int[] resNotifStore = {
            R.id.N_S_1, R.id.N_S_2, R.id.N_S_3, R.id.N_S_4, R.id.N_S_5
    };
    private int[] resRumah = {
            R.id.R_1, R.id.R_2, R.id.R_3, R.id.R_4, R.id.R_5, R.id.R_6, R.id.R_7
    };
    private int[] resNotifRumah = {
            R.id.N_R_1, R.id.N_R_2, R.id.N_R_3, R.id.N_R_4, R.id.N_R_5, R.id.N_R_6, R.id.N_R_7
    };

    private tempat toko[] = new tempat[5];
    private tempat rumah[] = new tempat[7];


    //nama player
    private String nama;
    private int jumlahToko = 4;
    private int jumlahRumah = 6;
    private int JumlahKoin = 0;

    private boolean klik;

    public void init() {
        klik = false;
        teks1 = (TextView) findViewById(R.id.teks1);
        teks2 = (TextView) findViewById(R.id.teks2);
        teks3 = (TextView) findViewById(R.id.teks3);
        pengguna = (TextView) findViewById(R.id.pengguna);
        simpan = (TextView) findViewById(R.id.simpan);
        batal = (TextView) findViewById(R.id.batal);
        error = (TextView) findViewById(R.id.teksError);
        inputNama = (EditText) findViewById(R.id.inputNama);
        layoutDialog = (RelativeLayout) findViewById(R.id.dialog);
        koin = (TextView) findViewById(R.id.skorpemain);


        for (int i = 0; i < 5; i++) {
            ikonStore[i] = findViewById(resStore[i]);
            notif_IkonStore[i] = findViewById(resNotifStore[i]);
            toko[i] = new tempat(this, ikonStore[i], notif_IkonStore[i], tempat.tiperumah.toko, i);

            final int finalI = i;
            ikonStore[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toko[finalI].tindakan();
                    if (jumlahToko > -1) {
                        run(jumlahToko);
                    } else {
                        run(jumlahRumah);
                    }
                    JumlahKoin += 5;
                    koin.setText("" + JumlahKoin);
                }
            });
        }
        for (int i = 0; i < 7; i++) {
            ikonRumah[i] = findViewById(resRumah[i]);
            notif_IkonRumah[i] = findViewById(resNotifRumah[i]);
            rumah[i] = new tempat(this, ikonRumah[i], notif_IkonRumah[i], tempat.tiperumah.rumah, i);

            final int finalI = i;
            ikonRumah[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rumah[finalI].tindakan();
                    if (jumlahRumah > -1) {
                        run(jumlahRumah);
                    }
                    JumlahKoin += 10;
                    koin.setText("" + JumlahKoin);
                }
            });
        }
    }

    private void run(int i) {
        if (jumlahToko < 0) {
            rumah[i].mulai(i);
            jumlahRumah--;
        } else {
            toko[i].mulai(i);
            jumlahToko--;
        }
        Log.v("Rumah", "" + jumlahRumah);
        Log.v("Toko", "" + jumlahToko);
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
                Intent i = new Intent(getApplicationContext(), MenuPetunjuk.class);
                startActivity(i);
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
                startTimer();
                run(jumlahToko);
                koin.setText("" + JumlahKoin);
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
