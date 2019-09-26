package br.eti.softlog.JsonExtract;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.softlog.model.Pessoas;
import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloRemetente;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.model.Remetente;
import br.eti.softlog.model.UsuariosSistema;
import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

/**
 * Created by Paulo Sérgio Alves on 2018/04/06.
 */

public class JsonExtractProtocolo {
    ConferenceApp app;
    Manager manager;
    private Context mContext;

    public JsonExtractProtocolo(Context context) {
        mContext = context;
        app = (ConferenceApp) context.getApplicationContext();
        manager = new Manager(app);
    }

    public void extract(String response) {
        try {

            JSONObject jObj = new JSONObject(response);
            //Gravando Setores
            JSONArray setores = jObj.getJSONArray("setores");
            for (int i = 0; i < setores.length(); i++){
                //Log.d("Setores", setores.get(i).toString());
                JSONObject jregiao = setores.getJSONObject(i);
                Long id = jregiao.getLong("id_regiao");
                Long idCidadePolo = jregiao.getLong("id_cidade_polo");
                Long idRegiaoAgrupadora = jregiao.getLong("id_regiao_agrupadora");
                String descricao = jregiao.getString("descricao");


                Regiao regiao = manager.addRegiao(id,descricao, idCidadePolo,idRegiaoAgrupadora);
            }

            //Gravando Regioes
            setores = jObj.getJSONArray("regioes");
            for (int i = 0; i < setores.length(); i++){
                //Log.d("Setores", setores.get(i).toString());
                JSONObject jregiao = setores.getJSONObject(i);
                Long id = jregiao.getLong("id_regiao");
                Long idCidadePolo = jregiao.getLong("id_cidade_polo");
                Long idRegiaoAgrupadora;
                try {
                    idRegiaoAgrupadora = jregiao.getLong("id_regiao_agrupadora");
                } catch (JSONException e) {
                    idRegiaoAgrupadora = null;
                }
                String descricao = jregiao.getString("descricao");
                Regiao regiao = manager.addRegiao(id,descricao, idCidadePolo,idRegiaoAgrupadora);
            }

            //Gravando Cidades
            JSONArray jCidades = jObj.getJSONArray("cidades");
            for (int i=0;i<jCidades.length();i++){

                JSONObject jcidade = jCidades.getJSONObject(i);

                String codIbge = jcidade.getString("cod_ibge");
                Long idCidade;
                try{
                    idCidade = jcidade.getLong("id_cidade");
                } catch (Exception e){
                    idCidade = null;
                    continue;
                }

                Double latitude = jcidade.getDouble("latitude");
                Double longitude = jcidade.getDouble("longitude");
                String nomeCidade = jcidade.getString("nome_cidade");
                String uf = jcidade.getString("uf");
                manager.addCidade(idCidade,nomeCidade,uf,codIbge,latitude,longitude,null);
            }

            //Gravando Usuarios
            JSONArray usuarios = jObj.getJSONArray("usuarios");
            for (int i = 0; i < usuarios.length(); i++){
                JSONObject jusuario = usuarios.getJSONObject(i);
                Long id_usuario = jusuario.getLong("id_usuario");
                String nome_usuario = jusuario.getString("nome_usuario");
                String senha = jusuario.getString("senha");
                UsuariosSistema usuario = manager.addUsuarioSistema(id_usuario,nome_usuario,null,senha);
            }

            //Gravando Remetentes
            JSONArray remetentes = jObj.getJSONArray("remetentes");
            for (int i = 0; i < remetentes.length();i++){

                JSONObject jrem = remetentes.getJSONObject(i);
                Long remetente_id = jrem.getLong("remetente_id");
                String remetente_nome = jrem.getString("remetente_nome");
                String remetente_cnpj = jrem.getString("remetente_cnpj");
                String bairro = jrem.getString("bairro");
                String cep = jrem.getString("cep");
                String endereco = jrem.getString("endereco");
                Long idCidade = jrem.getLong("id_cidade");
                String numero = jrem.getString("numero");
                String telefone = jrem.getString("telefone");

                Pessoas remetente = manager.addPessoa(remetente_id,remetente_nome,remetente_cnpj,endereco
                        ,numero, bairro,idCidade,cep,telefone,null,null);
            }

            //Gravando Protocolos
            JSONArray protocolos = jObj.getJSONArray("protocolos");
            for (int i = 0; i < protocolos.length();i++){
                //Log.d("Protocolos",protocolos.get(i).toString());
                JSONObject jprotocolo = protocolos.getJSONObject(i);

                Long id_nf_protocolo = jprotocolo.getLong("id_nf_protocolo");
                String data_protocolo = jprotocolo.getString("data_protocolo");

                String data_expedicao = jprotocolo.getString("data_expedicao");

                String data_conferencia;

                try {
                    data_conferencia = jprotocolo.getString("data_conferencia");
                } catch (JSONException e) {
                    data_conferencia = null;
                }

                int status = jprotocolo.getInt("status");

                Long id_usuario_protocolo = jprotocolo.getLong("usuario_protocolo");
                Long id_usuario_conferencia ;
                try {
                    id_usuario_conferencia = jprotocolo.getLong("usuario_conferencia");
                } catch (JSONException e){
                    id_usuario_conferencia = null;
                }

                boolean status_b;
                if (status==0) status_b = false; else status_b = true;

                Protocolo protocolo = manager.addProtocolo(id_nf_protocolo,
                        data_protocolo,data_conferencia,id_usuario_protocolo,
                        id_usuario_conferencia,status_b,false,
                        data_expedicao);

                //Gravando os protocolo setores
                JSONArray protocolo_setores = jprotocolo.getJSONArray("protocolo_setores");
                for (int j = 0; j < protocolo_setores.length();j++){
                    JSONObject jps = protocolo_setores.getJSONObject(j);

                    Long id_nf_protocolo_setor = jps.getLong("id_nf_protocolo_setor");
                    Long regiao_id = jps.getLong("id_setor");
                    Long protocolo_id = jps.getLong("id_nf_protocolo");
                    int qtd_volumes = jps.getInt("qtd_volumes");
                    int qtd_conferencia = jps.getInt("qtd_conferencia");
                    int qtd_notas = jps.getInt("qtd_notas");
                    Long id_usuario_conf;
                    try{
                        id_usuario_conf = jps.getLong("id_usuario_conferencia");
                    } catch (JSONException e) {
                        id_usuario_conf = null;
                    }

                    ProtocoloSetor ps = manager.addProtocoloSetor(id_nf_protocolo_setor, regiao_id,
                            protocolo_id,qtd_volumes,qtd_conferencia,qtd_notas,
                            id_usuario_conf,data_conferencia);

                    JSONArray protocolo_remetentes = jps.getJSONArray("remetentes");
                    for (int k = 0; k < protocolo_remetentes.length();k++) {
                        JSONObject pr = protocolo_remetentes.getJSONObject(k);

                        Long pr_remetente_id = pr.getLong("remetente_id");
                        int pr_qtd_notas = pr.getInt("qt_nf");
                        int pr_qtd_volumes = pr.getInt("qtd_volumes");

                        ProtocoloRemetente protocoloRemetente =
                                manager.addProtocoloRemetente(null,
                                        ps.getId(),pr_remetente_id,
                                        pr_qtd_notas,pr_qtd_volumes);
                    }
                }

            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
}
