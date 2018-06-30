package perusahaangaram.scmgame.objek;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import perusahaangaram.scmgame.R;

public class tempat {

    public static enum tiperumah {
        rumah, toko
    }

    public static enum notifikasi {
        tidakada, notifrumah, notiftoko
    }

    private Context konteks;
    private ImageView tempat;
    private ImageView notif;

    private boolean butuhklik;
    private int rumah;
    private int idbangunan;
    private int idAksi;

    private tiperumah tipe;

    public tempat(Context konteks, ImageView bangunan, ImageView notif, tiperumah tipe, int id) {
        this.konteks = konteks;
        this.notif = notif;
        this.tipe = tipe;

        tempat = bangunan;
        idbangunan = id;
    }

    public void tindakan() {
        gantiImageNotif(notifikasi.tidakada);
    }

    public void mulai() {

    }

    public void gantiImageNotif(notifikasi u) {
        switch (u) {
            case tidakada:
                notif.setImageResource(R.color.transparan);
                break;
            case notiftoko:
                notif.setImageResource(R.drawable.saltmentah);
                break;
            case notifrumah:
                notif.setImageResource(R.drawable.saltjadi);
                break;
        }
    }
}