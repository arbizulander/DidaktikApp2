package com.example.ik_2dm3.didaktikapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class Elementuakbilatuargazkian_2 extends AppCompatActivity {

    //conexion BD
    private MyOpenHelper db;
    private Context cont = this;

    private MediaPlayer mp;
    private Button areaClick;

    private ImageButton btnNextGame, btnPreviousGame;
    static final int REQ_BTN = 0;
    private int pag_anterior;
    static final int REQ_BTNATRAS = 12;

    BroadcastReceiver miBroadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.i("TAG", "Screen ON");
            }
            else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.i("TAG", "Screen OFF");
                mp.stop();

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementuak_bilatu_argazkian_2);
        setTitle("Elementuak bilatu argazkian");
        registerReceiver(miBroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(miBroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        btnNextGame = findViewById(R.id.btnNextGame);
        btnPreviousGame = findViewById(R.id.btnPreviousGame);

        btnPreviousGame.setEnabled(false);
        btnPreviousGame.setVisibility(View.INVISIBLE);

        btnNextGame.setEnabled(false);
        btnNextGame.setVisibility(View.INVISIBLE);

        Log.d("mytag", "ESTOY EN EL JUEGO 2");

        pag_anterior = getIntent().getIntExtra("pag_anterior", 0);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.a1_3);

        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                areaClick.setEnabled(true);
                //mp.release();
            }
        });

        areaClick = (Button) findViewById(R.id.areaClick);
        areaClick.setEnabled(false);
        areaClick.setBackgroundColor(Color.TRANSPARENT);
        areaClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Oso ondo!!", Toast.LENGTH_SHORT);
                toast.show();
                MediaPlayer mp;
                mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        switch (pag_anterior){
                            case 0:
                                int i = 2;
                                db=new MyOpenHelper(cont);
                                db.ActualizarJuego_Id(i);
                                db.close();

                                Log.d("mytag","AL ACABAR EL JUEGO FINALIDO Y VUELVO AL LISTADO PARA CARGAR SIGUIENTE...");
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result",1);
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                                break;
                            case 1:
                                break;
                        }
                    }
                });
                areaClick.setBackgroundResource(R.drawable.button_bg_round);
                areaClick.setEnabled(false);
                Log.d("mytag", "LE HAS DADO Y SE REVELA SE SUPONE JODER");
            }});

        CargarSegunPag_anterior(pag_anterior);

    }


    public void CargarSegunPag_anterior(int u){
        switch(u){
            case 0:

                break;
            case 1:
                btnPreviousGame.setEnabled(true);
                btnPreviousGame.setVisibility(View.VISIBLE);
                btnPreviousGame.setOnClickListener(v -> {
                    mp.stop();
                    Intent i = new Intent(Elementuakbilatuargazkian_2.this,Aukeratuargazkiegokia_1.class);
                    i.putExtra("pag_anterior",1);
                    startActivityForResult(i, REQ_BTN);
                    finish();
                });

                btnNextGame.setEnabled(true);
                btnNextGame.setVisibility(View.VISIBLE);
                btnNextGame.setOnClickListener(v -> {
                    mp.stop();
                    Intent i = new Intent(Elementuakbilatuargazkian_2.this,Argazkiaaztertu_3.class);
                    i.putExtra("pag_anterior",1);
                    startActivityForResult(i, REQ_BTN);
                    finish();
                });
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi botón al pulsar ir a atrás

                if (pag_anterior == 0){
                    Intent i = new Intent();
                    i.putExtra("keydown",REQ_BTNATRAS);
                    setResult(RESULT_OK,i);
                }
                //return true;
                mp.stop();
                finish();
            }
            return super.onKeyDown(keyCode, event);
    }
}
