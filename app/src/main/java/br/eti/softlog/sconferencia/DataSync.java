package br.eti.softlog.sconferencia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.eti.softlog.JsonExtract.JsonExtractMotoristas;
import br.eti.softlog.JsonExtract.JsonExtractNotasFiscais;
import br.eti.softlog.JsonExtract.JsonExtractOcorrencias;
import br.eti.softlog.JsonExtract.JsonExtractProtocolo;
import br.eti.softlog.JsonExtract.JsonExtractRegiaoCidades;
import br.eti.softlog.JsonExtract.JsonExtractVeiculos;
import br.eti.softlog.model.AnexosOcorrencias;
import br.eti.softlog.model.AnexosOcorrenciasDao;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.Romaneios;
import br.eti.softlog.model.RomaneiosDao;
import br.eti.softlog.utils.AppSingleton;

/**
 * Created by Paulo Sergio on 2018/03/01.
 */

public class DataSync {

    private static final String TAG = "DataSync";

    private Context mContext;

    ConferenceApp myapp;
    private Manager manager;
    private boolean executando;
    JsonExtractProtocolo jsonExtractProtocolo;
    JsonExtractVeiculos jsonExtractVeiculos;
    JsonExtractMotoristas jsonExtractMotoristas;
    JsonExtractNotasFiscais jsonExtractNotasFiscais;
    JsonExtractRegiaoCidades jsonExtractRegiaoCidades;
    JsonExtractOcorrencias jsonExtractOcorrencias;
    String chaveEtag;

    String codigo_acesso;
    String PATH = "/data/data/br.eti.softlog.sconferencia/files/";

    public DataSync(Context context) {
        mContext = context;
        myapp = (ConferenceApp)context.getApplicationContext();
        manager = new Manager(myapp);

        jsonExtractProtocolo = new JsonExtractProtocolo(context);
        jsonExtractVeiculos = new JsonExtractVeiculos(context);
        jsonExtractMotoristas = new JsonExtractMotoristas(context);
        jsonExtractNotasFiscais = new JsonExtractNotasFiscais(context);
        jsonExtractRegiaoCidades = new JsonExtractRegiaoCidades(context);
        jsonExtractOcorrencias = new JsonExtractOcorrencias(context);

        codigo_acesso = String.valueOf(myapp.getUsuario().getCodigoAcesso());
    }

    public void getProtocolos(){
    //Se nao tiver, acessa api para verificar se existe usuario registrado
        // Request a string response from the provided URL.

        if (!verificaConexao())
            return;

        RequestQueue queue = Volley.newRequestQueue(mContext);


        Date d = myapp.getDate();
        Date ddata_expedicao = d;
                //new Date(d.getTime() - (1000*60*60*8));

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formato.applyPattern("yyyy-MM-dd");
        String data_expedicao = formato.format(ddata_expedicao);


        String url = "http://api.softlog.eti.br/api/softlog/protocolo/" + codigo_acesso +
                "/" + data_expedicao + "/0";
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url",url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        jsonExtractProtocolo.extract(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("ERRO",error.toString());
            }
        });

        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Protocolos");
        //Log.d("Log","Processo Concluido!");
    }

    public void getVeiculos(){

        if (!verificaConexao())
            return;

        String url = "http://api.softlog.eti.br/api/softlog/veiculos?codigo_acesso=" + codigo_acesso;
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url",url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("Veiculos",response);
                        jsonExtractVeiculos.extract(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("ERRO",error.toString());
            }
        });

        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Veiculos");
        //Log.d("Log","Processo Concluido!");

    }

    public void getOcorrencias(){

        if (!verificaConexao())
            return;

        String url = "http://api.softlog.eti.br/api/softlog/ocorrencia/"+ codigo_acesso;
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url",url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("Veiculos",response);
                        jsonExtractOcorrencias.extract(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("ERRO",error.toString());
            }
        });

        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"ocorrencias");
        //Log.d("Log","Processo Concluido!");

    }

    public void getMotoristas(){

        if (!verificaConexao())
            return;

        String url = "http://api.softlog.eti.br/api/softlog/motoristas?codigo_acesso=" + codigo_acesso;
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url",url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("Veiculos",response);
                        jsonExtractMotoristas.extract(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("ERRO",error.toString());
            }
        });

        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Veiculos");
        //Log.d("Log","Processo Concluido!");

    }

    public void getRegiaoCidades(){

        if (!verificaConexao())
            return;

        String url = "http://api.softlog.eti.br/api/softlog/regiao_cidades?codigo_acesso=" + codigo_acesso;

        //Verifica se tem eTag para controle de modificacao de conteudo
        chaveEtag = Prefs.getString("etag_regiao_cidades","");


        //Log.d("Url",url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("Veiculos",response);
                        String etag = jsonExtractRegiaoCidades.extract(response);
                        Prefs.putString("etag_regiao_cidades",etag);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("ERRO",error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Etag", chaveEtag);
                return params;
            }
        };

        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Veiculos");
        //Log.d("Log","Processo Concluido!");

    }

    public void getNotasFiscais(){

        if (!verificaConexao())
            return;

        String url = "http://api.softlog.eti.br/api/softlog/notasromaneio?codigo_acesso=53&id_protocolo=104050&tipo_busca=1";
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url",url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("Veiculos",response);
                        jsonExtractNotasFiscais.extract(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("ERRO",error.toString());
            }
        });

        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Veiculos");
        //Log.d("Log","Processo Concluido!");

    }


    public void sendProtocolos() throws JSONException {


        if (!verificaConexao()){
            return ;
        }
        final List<Protocolo> protocolos = manager.findProtocoloByEnviar();

        if (protocolos.size()>0) {

            final JSONArray jaProtocolos = new JSONArray();

            for (int i=0; i < protocolos.size(); i++){
                JSONObject joProtocolo = new JSONObject();
                Protocolo protocolo = protocolos.get(i);
                joProtocolo.put("id_protocolo_nf",protocolo.getId().longValue());
                joProtocolo.put("id_usuario_conferencia", protocolo.getIdUsuarioConferencia());
                joProtocolo.put("usuario_conferencia", myapp.getUsuario().getLogin());

                joProtocolo.put("data_conferencia",protocolo.getDataConferencia());
                joProtocolo.put("status",1);

                joProtocolo.put("observacao",protocolo.getObservacao());

                JSONArray jaSetores = new JSONArray();
                List<ProtocoloSetor> setores = protocolo.getProtocoloSetores();
                for (int j=0; j<setores.size(); j++){
                    JSONObject joSetor = new JSONObject();
                    ProtocoloSetor setor = setores.get(j);
                    joSetor.put("id_regiao",setor.getRegiaoId());
                    joSetor.put("qtd_conferencia",setor.getQtdConferencia());

                    jaSetores.put(joSetor);
                }
                joProtocolo.put("setores",jaSetores);


                //enviar ocorrencias
                JSONArray jaNotas = new JSONArray();
                List<NotasFiscais> notasFiscais = protocolo.getNotasFiscais();
                for (int j=0; j<notasFiscais.size();j++){
                    NotasFiscais notaFiscal = notasFiscais.get(j);

                    if (!notaFiscal.getTem_ocorrencia())
                        continue;

                    JSONObject joNotas = new JSONObject();
                    joNotas.put("id_nota_fiscal_imp",notaFiscal.getIdNotaFiscalImp());
                    joNotas.put("id_ocorrencia",notaFiscal.getIdOcorrencia());
                    joNotas.put("observacao",notaFiscal.getObservacao());

                    //Envia ocorrencias
                    JSONArray jaImagens = new JSONArray();
                    List<AnexosOcorrencias> anexos = myapp.getDaoSession()
                            .getAnexosOcorrenciasDao()
                            .queryBuilder()
                            .where(AnexosOcorrenciasDao.Properties.IdNotaFiscalImp.eq(notaFiscal.getIdNotaFiscalImp()))
                            .list();

                    for (int k=0;k<anexos.size();k++){
                        AnexosOcorrencias image = anexos.get(k);
                        JSONObject joImagem = new JSONObject();

                        if (image.getConteudoAnexo() == null) {
                            continue;
                        }

                        String encoded;
                        try {

                            String path = myapp.getApplicationContext().getFilesDir().toString();

                            //File root =
                            //ImageView IV = (ImageView) findViewById(R.id."image view");
                            Bitmap bMap = BitmapFactory.decodeFile(path + "/" + image.getConteudoAnexo());

                            if (bMap == null) {
                                continue;
                            }
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                            byte[] byteArray = byteArrayOutputStream .toByteArray();

                            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        } catch (Error e2) {
                            e2.printStackTrace();
                            continue;

                        }

                        try {
                            joImagem.put("id",image.getId());
                            joImagem.put("nome_arquivo",image.getConteudoAnexo());
                            joImagem.put("arquivo",encoded);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        jaImagens.put(joImagem);
                    }
                    joNotas.put("anexos",jaImagens);
                    jaNotas.put(joNotas);
                }
                joProtocolo.put("notas_fiscais",jaNotas);
                jaProtocolos.put(joProtocolo);
            }

            JSONObject json = new JSONObject();
            json.put("conferencias",jaProtocolos);

            String strJson = json.toString();

            Log.d("Json", json.toString());


            final String codigo_acesso = String.valueOf(myapp.getUsuario().getCodigoAcesso());
            String url = "http://api.softlog.eti.br/api/softlog/protocolo";


            //Registro do usuario e criacao do banco de dados
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            for(int i=0; i < protocolos.size();i++){
                                Protocolo protocolo = protocolos.get(i);
                                protocolo.setSincronizado(true);
                                myapp.getDaoSession().update(protocolo);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Log.d("ERRO",error.toString());
                    if (error.networkResponse.statusCode==404){
                        Log.i("Erro","Sem protocolos");
                    } else {
                        Log.i("Erro","Ocorreu um erro");
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parameters = new HashMap<String,String>();
                    //Log.d("Envio Json", jaProtocolos.toString());
                    parameters.put("conferencias",jaProtocolos.toString());
                    parameters.put("codigo_acesso",codigo_acesso);
                    //Log.d("parametros",parameters.toString());
                    return parameters;
                }

            };

            AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Protocolo");
        }

    }

    public void sendRomaneios() throws JSONException {

        if (!verificaConexao()){
            return ;
        }

        final List<Romaneios> romaneios = myapp.getDaoSession().getRomaneiosDao()
                .queryBuilder()
                .where(RomaneiosDao.Properties.Sincronizado.eq(false))
                .where(RomaneiosDao.Properties.Concluido.eq(true))
                .list();

        if (romaneios.size()<0) {
            final JSONArray jaRomaneios = new JSONArray();


            for (int i=0; i < romaneios.size(); i++){
                JSONObject joRomaneio = new JSONObject();

                Romaneios romaneio = romaneios.get(i);
                joRomaneio.put("placa_veiculo",romaneio.getPlacaVeiculo());
                joRomaneio.put("id_motorista",romaneio.getIdMotorista());
                joRomaneio.put("data_romaneio",romaneio.getDataRomaneio());
                joRomaneio.put("tipo_destino","C");
                joRomaneio.put("id_origem",romaneio.getIdOrigem());
                joRomaneio.put("id_destino",romaneio.getIdDestino());
                joRomaneio.put("id_setor",romaneio.getIdSetor());
                joRomaneio.put("id_usuario", romaneio.getIdUsuario());

                JSONArray jaNotas = new JSONArray();

                for (int j=0; j<romaneio.getNotasFiscais().size();j++){
                    jaNotas.put(romaneio.getNotasFiscais().get(j).getIdNotaFiscalImp());
                }
                joRomaneio.put("notas_fiscais",jaNotas);

                jaRomaneios.put(joRomaneio);
            }

            JSONObject json = new JSONObject();
            json.put("romaneios",jaRomaneios);

            String strJson = json.toString();

            //Log.d("Json", json.toString());

            final String codigo_acesso = String.valueOf(myapp.getUsuario().getCodigoAcesso());
            String url = "http://api.softlog.eti.br/api/softlog/romaneiosapp";


            //Registro do usuario e criacao do banco de dados
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("Resposta",response);

                            for(int i=0; i < romaneios.size();i++){
                                Romaneios romaneio = romaneios.get(i);
                                romaneio.setSincronizado(true);
                                myapp.getDaoSession().update(romaneio);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Log.d("ERRO",error.toString());
                    if (error.networkResponse.statusCode==404){
                        Log.i("Erro","Sem protocolos");
                    } else {
                        Log.i("Erro","Ocorreu um erro");
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> parameters = new HashMap<String,String>();
                    //Log.d("Envio Json", jaRomaneios.toString());
                    parameters.put("romaneios",jaRomaneios.toString());
                    parameters.put("codigo_acesso",codigo_acesso);
                    //Log.d("parametros",parameters.toString());
                    return parameters;
                }

            };

            AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest,"Romaneios");
        }

    }
    public boolean isExecutando() {
        return executando;
    }

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
