package perusahaangaram.scmgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static boolean suara;
    private static boolean musik;
    private boolean tPengaturan = false;
    private static MediaPlayer music;

    private ImageView tombolMain;
    private ImageView tombolPengaturan;
    private ImageView tombolPetunjuk;
    private ImageView tombolTentang;
private ImageView tombolKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        suara = true;
        musik = true;
        setContentView(R.layout.activity_main);
        ImageView tSuara = (ImageView) findViewById(R.id.suara);
        ImageView tMusik = (ImageView) findViewById(R.id.musik);
        music = MediaPlayer.create(MainActivity.this, R.raw.music);

        if (!tPengaturan) {
            tSuara.setVisibility(View.GONE);
            tMusik.setVisibility(View.GONE);
        }
        View decorView = getWindow().getDecorView();
        //hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        tombolMain = (ImageView) findViewById(R.id.tombolMasuk);
        tombolPengaturan = (ImageView) findViewById(R.id.tombolPengaturan);
        tombolPetunjuk = (ImageView) findViewById(R.id.tombolPetunjuk);
        tombolTentang = (ImageView) findViewById(R.id.tombolTentang);
tombolKeluar = (ImageView) findViewById(R.id.tombolKeluar);

        tombolMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MenuPermainan.class);
                startActivity(i);
            }
        });

        tombolPetunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, MenuPetunjuk.class);
                startActivity(i);
            }
        });

        tombolPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView tSuara = (ImageView) findViewById(R.id.suara);
                ImageView tMusik = (ImageView) findViewById(R.id.musik);

                if (!tPengaturan) {
                    tSuara.setVisibility(View.VISIBLE);
                    tMusik.setVisibility(View.VISIBLE);
                    tPengaturan = true;
                } else {
                    tSuara.setVisibility(View.GONE);
                    tMusik.setVisibility(View.GONE);
                    tPengaturan = false;
                }
            }
        });

        tombolTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MenuTentang.class);
                startActivity(i);
            }
        });
        tombolKeluar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Keluar();
        }
    });
    }


    @Override
    public void onBackPressed() {
        //pop up saat menekan tombol kembali
        Keluar();
    }

    @Override
    public void onStart() {
        super.onStart();
        music.start();
    }

    public void Keluar() {
        new AlertDialog.Builder(this)
                .setMessage("Yakin Ingin Keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        kode saat memilih ya
                        musik = false;
                        gantiNada("musik");
                        finish();
                    }
                }).setNegativeButton("Tidak", null)
                .show();

    }
    public static void gantiNada(String nada) {
        if (nada.equalsIgnoreCase("musik")) {
            if (musik)
                music.start();
            else
                music.release();

        } else if (nada.equalsIgnoreCase("suara")) {
//            if (suara)
//                //kode saat efek suara on
//            else
//                //kode saat efek suara off
        }
    }

    public void tombolUbahSuara() {
        //kode saat tombol suara ditekan (gambar speaker)
        ImageView s = (ImageView) findViewById(R.id.suara);
//        if (!this.suara) {
//            this.suara = true;
//            s.setImageResource(R.drawable.suara_on);
//        } else if (this.suara) {
//            this.suara = false;
//            s.setImageResource(R.drawable.suara_off);
//        }
//        gantiNada("suara");
    }

    public void tombolUbahMusik() {
        //kode saat tombol musik ditekan (gambar not balok)
        ImageView m = (ImageView) findViewById(R.id.musik);
//        if (!this.musik) {
//            this.musik = true;
//            m.setImageResource(R.drawable.musik_on);
//        } else if (this.musik) {
//            this.musik = false;
//            m.setImageResource(R.drawable.musik_off);
//        }
//        gantiNada("musik");
    }
}