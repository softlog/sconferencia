package br.eti.softlog.JsonExtract;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.Pessoas;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

public class JsonExtractNotasFiscais {


    ConferenceApp app;
    Manager manager;
    private Context mContext;

    public JsonExtractNotasFiscais(Context context) {
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


            //Gravando Destinatario
            JSONArray destinatarios = jObj.getJSONArray("destinatarios");
            for (int i = 0; i < destinatarios.length();i++){

                JSONObject jdest = destinatarios.getJSONObject(i);

                Long idDestinatario = jdest.getLong("destinatario_id");
                String nome = jdest.getString("destinatario_nome");
                String cnpjCpf = jdest.getString("destinatario_cnpj");
                String bairro = jdest.getString("bairro");
                String cep = jdest.getString("cep");
                String endereco = jdest.getString("endereco");
                Long idCidade = jdest.getLong("id_cidade");
                String numero = jdest.getString("numero");
                String telefone = jdest.getString("telefone");

                Pessoas remetente = manager.addPessoa(idDestinatario,nome,cnpjCpf,endereco
                        ,numero, bairro,idCidade,cep,telefone,null,null);
            }

            //Gravando notas fiscais
            Long idNfProtocolo;
            idNfProtocolo = Long.valueOf(0);
            JSONArray notas = jObj.getJSONArray("notas_fiscais");
            for (int i=0; i< notas.length();i++){
                JSONObject nf = notas.getJSONObject(i);

                String chave = nf.getString("chave_nfe");
                String data_emissao = nf.getString("data_emissao");
                String data_expedicao = nf.getString("data_expedicao");
                Long idDestinatario = nf.getLong("destinatario_id");
                idNfProtocolo = nf.getLong("id_nf_protocolo");
                String numeroNotaFiscal = nf.getString("numero_nota_fiscal");
                Long remetenteId = nf.getLong("remetente_id");
                String serieNotaFiscal = nf.getString("serie_nota_fiscal");
                Long idNotaFiscalImp = nf.getLong("id_nota_fiscal_imp");

                Long idOcorrencia;
                try{
                    idOcorrencia = nf.getLong("id_ocorrencia");
                } catch (Exception e){
                    idOcorrencia = null;
                }



                manager.addNotaFiscal(chave,serieNotaFiscal,numeroNotaFiscal,remetenteId,
                        idDestinatario,0.00,0.00,0.00,0.00,
                        null,idNfProtocolo,idNotaFiscalImp,idOcorrencia);


            }
            ProtocoloSetor protocolo = app.getDaoSession().getProtocoloSetorDao()
                    .queryBuilder()
                    .where(ProtocoloSetorDao.Properties.ProtocoloId.eq(idNfProtocolo))
                    .unique();

            protocolo.setSincronizado(true);
            app.getDaoSession().update(protocolo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}