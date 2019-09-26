package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.idescout.sql.SqlScoutServer;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import br.eti.softlog.ImageCrop.MainActivityCrop;
import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloDao;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.LoginActivity;
import br.eti.softlog.sconferencia.MainActivity;
import br.eti.softlog.sconferencia.R;

public class SplashActive extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;
    ConferenceApp myapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_active);

        SqlScoutServer.create(this, getPackageName());

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal


                myapp = (ConferenceApp)getApplicationContext();
                if (myapp.getStatus()) {



                    //Log.d("Debug","Setores");
                    //Intent i = new Intent(SplashActive.this, MainActivityCrop.class );

                    Intent i = new Intent(SplashActive.this,MainActivity.class );
                    i.putExtra("origem","splash");
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActive.this,LoginActivity.class );
                    startActivity(i);
                }
                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
