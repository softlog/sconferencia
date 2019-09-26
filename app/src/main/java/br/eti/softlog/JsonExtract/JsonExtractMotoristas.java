package br.eti.softlog.JsonExtract;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

public class JsonExtractMotoristas {

    ConferenceApp app;
    Manager manager;
    private Context mContext;

    public JsonExtractMotoristas(Context context) {
        mContext = context;
        app = (ConferenceApp) context.getApplicationContext();
        manager = new Manager(app);
    }

    public void extract(String response) {

        JSONObject jObj = null;

        try {
            jObj = new JSONObject(response);

            //Cidades
            JSONArray jCidades = jObj.getJSONArray("cidades");
            for (int i=0;i<jCidades.length();i++){
                JSONObject jcidade = jCidades.getJSONObject(i);

                String codIbge = jcidade.getString("cod_ibge");
                Long idCidade = jcidade.getLong("id_cidade");
                Double latitude = jcidade.getDouble("latitude");
                Double longitude = jcidade.getDouble("longitude");
                String nomeCidade = jcidade.getString("nome_cidade");
                String uf = jcidade.getString("uf");
                manager.addCidade(idCidade,nomeCidade,uf,codIbge,latitude,longitude,null);

            }

            //Motoristas
            JSONArray jmotoristas = jObj.getJSONArray("motoristas");

            for(int i=0;i<jmotoristas.length();i++) {

                JSONObject jmotorista = jmotoristas.getJSONObject(i);
                String bairro = jmotorista.getString("bairro");
                String cep = jmotorista.getString("cep");
                String cidade = jmotorista.getString("cidade");
                String cnpjCpf = jmotorista.getString("cnpj_cpf");
                String endereco = jmotorista.getString("endereco");
                String estado = jmotorista.getString("estado");
                Long idCidade = jmotorista.getLong("id_cidade");
                String nomeMotorista = jmotorista.getString("nome_motorista");
                String numero = jmotorista.getString("numero");
                String telefone = jmotorista.getString("telefone");
                Long idMotorista = jmotorista.getLong("id_fornecedor");

                try {
                    manager.addMotorista(idMotorista, nomeMotorista, cnpjCpf, endereco, numero, bairro, idCidade,
                            cep, telefone, null, null);
                } catch (Exception e){
                    continue;
                }




            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
