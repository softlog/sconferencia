package br.eti.softlog.sconferencia;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.eti.softlog.utils.AppSingleton;

public class ConferenceService extends Service {

    ConferenceApp myapp;
    private Timer timer = new Timer();
    public List<Worker> threads = new ArrayList<Worker>();

    private int startId;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Script","onCreate");
        myapp = (ConferenceApp)getApplicationContext();
        startId = -1;
        //this.startservice();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Script","onStartCommand");
        myapp = (ConferenceApp)getApplicationContext();


        if (this.startId == -1) {
            this.startId = startId;
            Worker worker = new Worker(startId);
            worker.start();
            threads.add(worker);
        }

        return(super.onStartCommand(intent,flags,startId));
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // Neste exemplo, iremos supor que o service será invocado apenas
        // atraves de startService()
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        for(int i = 0, tam = threads.size();i < tam; i++){
            threads.get(i).ativo = false;
        }
    }



    class Worker extends Thread {
        public int count;
        public int startId;
        public boolean ativo;

        public Worker(int startId) {
            this.startId = startId;
        }

        public void run() {
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    Log.i("Aviso", "Executando Tarefa " + myapp.getUsuario().getNome());
                    try {
                        exec();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 300000);
        }

        private void exec() throws JSONException {

            if (myapp.getUsuario()==null) {
                return ;
            }

            DataSync d = new DataSync(myapp.getApplicationContext());

//          if (verificaConexao()) {
//               d.getProtocolos();
//          }


            if (verificaConexao()) {
                d.sendProtocolos();
                d.sendRomaneios();
                d.getVeiculos();
                d.getMotoristas();
                d.getRegiaoCidades();
                d.getOcorrencias();
                //d.getNotasFiscais();
            }

//            Intent broadcastIntent = new Intent();
//            broadcastIntent.setAction(MainActivity.NOTIFY_ACTIVITY_ACTION);
//            broadcastIntent.putExtra("addtional_param", 1);
//            broadcastIntent.putExtra("addtional_param2", 2); //etc
//
//            sendBroadcast(broadcastIntent);
        }
    }
    /* Função para verificar existência de conexão com a internet
*/
    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) myapp.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

}




