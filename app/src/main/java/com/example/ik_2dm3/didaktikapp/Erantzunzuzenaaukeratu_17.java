package com.example.ik_2dm3.didaktikapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Erantzunzuzenaaukeratu_17 extends AppCompatActivity {

    private MediaPlayer mp, mp2;
    private ImageButton btnPreviousGame, btnNext;
    private Context cont = this;
    private MyOpenHelper db;
    private int pag_anterior;
    private int puntos = 0;
    static final int REQ_BTN = 0;
    private Button respuesta1, respuesta2, respuesta3, respuesta4, respuesta5, respuesta6, respuesta7, respuesta8;
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
                mp2.stop();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erantzunzuzenaaukeratu_17);
        setTitle("Erantzun zuzena aukeratu");

        registerReceiver(miBroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(miBroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));

        btnPreviousGame = findViewById(R.id.btnPreviousGame);
        btnPreviousGame.setEnabled(false);
        btnPreviousGame.setVisibility(View.INVISIBLE);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setVisibility(View.INVISIBLE);

        respuesta1 = findViewById(R.id.respuesta1);
        respuesta2 = findViewById(R.id.respuesta2);
        respuesta3 = findViewById(R.id.respuesta3);
        respuesta4 = findViewById(R.id.respuesta4);
        respuesta5 = findViewById(R.id.respuesta5);
        respuesta6 = findViewById(R.id.respuesta6);
        respuesta7 = findViewById(R.id.respuesta7);
        respuesta8 = findViewById(R.id.respuesta8);
        DesactivarBotones();

        respuesta1.setOnClickListener(v -> {
            RespuestaCorrecta(respuesta1,respuesta2);
        });
        respuesta2.setOnClickListener(v -> {
            RespuestaIncorrecta(respuesta2);
        });
        respuesta3.setOnClickListener(v -> {
            RespuestaIncorrecta(respuesta3);
        });
        respuesta4.setOnClickListener(v -> {
            RespuestaCorrecta(respuesta4,respuesta3);
        });
        respuesta5.setOnClickListener(v -> {
            RespuestaCorrecta(respuesta5,respuesta6);
        });
        respuesta6.setOnClickListener(v -> {
            RespuestaIncorrecta(respuesta6);
        });
        respuesta7.setOnClickListener(v -> {
            RespuestaIncorrecta(respuesta7);
        });
        respuesta8.setOnClickListener(v -> {
            RespuestaCorrecta(respuesta8,respuesta7);
        });

        mp = MediaPlayer.create(getApplicationContext(), R.raw.a6_3);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ActivarBotones();
            }
        });

        pag_anterior = getIntent().getIntExtra("pag_anterior", 0);

        switch (pag_anterior){
            case 0:
                break;
            case 1:
                btnPreviousGame.setEnabled(true);
                btnPreviousGame.setVisibility(View.VISIBLE);
                btnPreviousGame.setOnClickListener(v -> {
                    mp.stop();
                    Intent i = new Intent(Erantzunzuzenaaukeratu_17.this,Informazioakuadroabete_16.class);
                    i.putExtra("pag_anterior",1);
                    startActivityForResult(i, REQ_BTN);
                    finish();
                });

                btnNext.setEnabled(true);
                btnNext.setVisibility(View.VISIBLE);
                btnNext.setOnClickListener(v -> {
                    mp.stop();
                    finish();
                });

                break;
        }
    }
    public void RespuestaCorrecta(Button boton, Button boton2){
        Toast toast = Toast.makeText(getApplicationContext(),
                "Oso ondo!!", Toast.LENGTH_SHORT);
        toast.show();
        boton.setBackgroundColor(Color.parseColor("#04B404"));
        SonidoRespuesta(R.raw.correct2, boton);
        boton.setEnabled(false);
        boton2.setEnabled(false);
        puntos++;
        comprobarVictoria();
    }
    public void RespuestaIncorrecta(Button boton){
        Toast toast = Toast.makeText(getApplicationContext(),
                "Txarto!!", Toast.LENGTH_SHORT);
        toast.show();
        boton.setBackgroundColor(Color.RED);
        SonidoRespuesta(R.raw.fail2, boton);
    }
    public void SonidoRespuesta(int sonido, Button boton){
        mp2 = MediaPlayer.create(getApplicationContext(), sonido);
        mp2.start();
    }
    public void ActivarBotones(){
        respuesta1.setEnabled(true);
        respuesta2.setEnabled(true);
        respuesta3.setEnabled(true);
        respuesta4.setEnabled(true);
        respuesta5.setEnabled(true);
        respuesta6.setEnabled(true);
        respuesta7.setEnabled(true);
        respuesta8.setEnabled(true);
    }
    public void DesactivarBotones(){
        respuesta1.setEnabled(false);
        respuesta2.setEnabled(false);
        respuesta3.setEnabled(false);
        respuesta4.setEnabled(false);
        respuesta5.setEnabled(false);
        respuesta6.setEnabled(false);
        respuesta7.setEnabled(false);
        respuesta8.setEnabled(false);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (pag_anterior == 0){
                Intent i = new Intent();
                i.putExtra("keydown",REQ_BTNATRAS);
                setResult(RESULT_OK,i);
            }
            mp.stop();
            Log.d("mytag","Has ido atras");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void comprobarVictoria(){
        if(puntos==4){
            switch (pag_anterior){
                case 0:
                    int i = 17;
                    db=new MyOpenHelper(cont);
                    db.ActualizarJuego_Id(i);
                    db.close();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",1);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                    break;

                case 1:
                    break;
            }
        }
    }
}
