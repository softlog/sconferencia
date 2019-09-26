package br.eti.softlog.JsonExtract;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.softlog.model.Regiao;
import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

public class JsonExtractRegiaoCidades {


    ConferenceApp app;
    Manager manager;
    private Context mContext;

    public JsonExtractRegiaoCidades(Context context){
        mContext = context;
        app = (ConferenceApp) context.getApplicationContext();
        manager = new Manager(app);
    }

    public String extract(String response) {


        JSONObject jObj = null;
        String etag;

        try {
            jObj = new JSONObject(response);

            etag = jObj.getString("ETag");
            //if (etag.equals(prefs))
            //Gravando Cidades
            JSONObject joDados = jObj.getJSONObject("data");

            JSONArray jaRegioes = joDados.getJSONArray("regioes");
            for (int i = 0; i<jaRegioes.length();i++){
                JSONObject joRegiao = jaRegioes.getJSONObject(i);

                Long id = joRegiao.getLong("id_regiao");
                Long idCidadePolo = joRegiao.getLong("id_cidade_polo");
                Long idRegiaoAgrupadora;
                try {
                    idRegiaoAgrupadora = joRegiao.getLong("id_regiao_agrupadora");
                } catch (JSONException e) {
                    idRegiaoAgrupadora = null;
                }
                String descricao = joRegiao.getString("descricao");
                Regiao regiao = manager.addRegiao(id,descricao, idCidadePolo,idRegiaoAgrupadora);
            }

            JSONArray jCidades = joDados.getJSONArray("cidades");
            for (int i = 0; i < jCidades.length(); i++) {

                JSONObject jcidade = jCidades.getJSONObject(i);

                String codIbge = jcidade.getString("cod_ibge");
                Long idCidade;
                try {
                    idCidade = jcidade.getLong("id_cidade");
                } catch (Exception e) {
                    idCidade = null;
                    continue;
                }
                Double latitude;
                try {
                    latitude = jcidade.getDouble("latitude");
                } catch (Exception e){
                    latitude = null;
                }
                Double longitude;
                try {
                    longitude = jcidade.getDouble("longitude");
                } catch (Exception e){
                    longitude = null;
                }

                String nomeCidade = jcidade.getString("nome_cidade");
                String uf = jcidade.getString("uf");
                Long idRegiao = jcidade.getLong("id_regiao");
                manager.addCidade(idCidade, nomeCidade, uf, codIbge, latitude, longitude, null);
                manager.addRegiaoCidades(idCidade,idRegiao);
            }
        } catch (Exception e){
            e.printStackTrace();
            etag = null;
        }

        return etag;
    }
}
