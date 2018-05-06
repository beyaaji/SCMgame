package perusahaangaram.scmgame;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


public class MenuPetunjuk extends Activity {

    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_petunjuk);
        View decorView = getWindow().getDecorView();
        //hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {
        finish();


    }

    public void menuAwal(View view) {
        finish();
    }
    public void next(View view) {
        ganti(true);
    }

    public void prev(View view) {
        ganti(false);
    }

    public void ganti(boolean b) {
        if (b) {
            if (!(index == 2)) {
                index++;
                gantiPetunjuk();
            }
        } else if (!b) {
            if (!(index == 0)) {
                index--;
                gantiPetunjuk();
            }
        }
    }

    public void gantiPetunjuk() {
        ImageView gambarPetunjuk = findViewById(R.id.gambarPetunjuk);
        switch (index) {
            case 0:
                gambarPetunjuk.setImageResource(R.drawable.petunjuk1);
                break;
            case 1:
                gambarPetunjuk.setImageResource(R.drawable.petunjuk2);
                break;
            default:
                //nothing
        }
    }
}