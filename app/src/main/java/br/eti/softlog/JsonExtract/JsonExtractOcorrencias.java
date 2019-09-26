package br.eti.softlog.JsonExtract;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

/**
 * Created by Paulo Sérgio Alves on 2018/04/06.
 */

public class JsonExtractOcorrencias {


    ConferenceApp app;
    Manager manager;
    private Context mContext;

    public JsonExtractOcorrencias(Context context) {
        mContext = context;
        app = (ConferenceApp) context.getApplicationContext();
        manager = new Manager(app);
    }

    public void extract(String response) {

        JSONObject jObj = null;

        try {
            jObj = new JSONObject(response);

            //Cidades
            JSONArray jOcorrencias = jObj.getJSONArray("ocorrencias");
            for (int i=0;i<jOcorrencias.length();i++){
                JSONObject jOcorrencia = jOcorrencias.getJSONObject(i);

                String ocorrencia = jOcorrencia.getString("ocorrencia");
                Long idOcorrencia = jOcorrencia.getLong("id_ocorrencia");
                int ativo = jOcorrencia.getInt("aplicativo_sconferencia");

                if (ativo == 0)
                    manager.addOcorrencia(idOcorrencia,ocorrencia,false);
                else
                    manager.addOcorrencia(idOcorrencia,ocorrencia,true);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
