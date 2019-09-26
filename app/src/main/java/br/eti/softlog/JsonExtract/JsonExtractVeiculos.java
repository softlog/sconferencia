package br.eti.softlog.JsonExtract;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

public class JsonExtractVeiculos {

    ConferenceApp app;
    Manager manager;
    private Context mContext;

    public JsonExtractVeiculos(Context context) {
        mContext = context;
        app = (ConferenceApp) context.getApplicationContext();
        manager = new Manager(app);
    }

    public void extract(String response) {

        JSONObject jObj = null;

        try {
            jObj = new JSONObject(response);
            JSONArray jveiculos = jObj.getJSONArray("veiculos");

            for (int i=0;i<jveiculos.length();i++){

                JSONObject jveiculo = jveiculos.getJSONObject(i);
                String placa = jveiculo.getString("placa_veiculo");
                String descricao = jveiculo.getString("descricao");

                manager.addVeiculo(placa,descricao);

                //Log.d("Placa",placa);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
