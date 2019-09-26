package br.eti.softlog.sconferencia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Date;
import java.util.List;

import br.eti.softlog.JsonExtract.JsonExtractNotasFiscais;
import br.eti.softlog.interfaces.RecyclerViewClickListener;
import br.eti.softlog.model.ProtocoloRemetente;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.UsuariosSistema;
import br.eti.softlog.utils.AppSingleton;
import br.eti.softlog.utils.Util;

public class RemetenteSetorActivity extends AppCompatActivity {

    ConferenceApp myapp;

    Manager manager;


    ProtocoloSetor protocolo;
    Long idProtocolo;
    Long idProtocoloSetor;

    DataSync dataSync;
    List<ProtocoloRemetente> protocoloRemetentes;
    Intent in;

    Button btnFinalizar;
    TextView txtProtocolo;
    ProgressBar pb;

    RecyclerView mRecyclerView;
    RecyclerViewClickListener mListener;

    private ProtocoloRemetenteAdapter mAdapter;

    JsonExtractNotasFiscais jsonExtractNotasFiscais;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolo_remetente_list);

        getSupportActionBar().setTitle("Remetentes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnFinalizar = findViewById(R.id.btnFinalizar);
        txtProtocolo = findViewById(R.id.txtProtocolo);
        pb = findViewById(R.id.pbMain);
        pb.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.GONE);

        in = getIntent();

        myapp = (ConferenceApp) getApplicationContext();
        manager = new Manager(myapp);
        dataSync = new DataSync(getApplicationContext());
        jsonExtractNotasFiscais = new JsonExtractNotasFiscais(getApplicationContext());

        String sIdProtocoloSetor = in.getStringExtra("id_protocolo_setor");
        idProtocoloSetor = Long.valueOf(sIdProtocoloSetor);

        protocoloRemetentes = manager.findProtocoloRemetenteByIdSetor(idProtocoloSetor);

        protocolo = manager.findProtocoloSetorById(Long.valueOf(sIdProtocoloSetor));

        if (protocolo.getSincronizado() != null)
            if (!protocolo.getSincronizado())
                getNotasFiscais();

        mListener = (view, position) -> {
            //Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_SHORT).show();
        };

        Long id_protocolo;
        if (protocoloRemetentes.size() > 0) {
            id_protocolo = protocoloRemetentes.get(0).getProtocoloSetor().getProtocoloId();
        } else {
            id_protocolo = Long.valueOf(0);
        }


        txtProtocolo.setText("Nr.: " + String.valueOf(id_protocolo));

        setupRecycler();

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigoConferente = myapp.getCodigoConferente();

                AlertDialog.Builder alert = new AlertDialog.Builder(RemetenteSetorActivity.this);
                alert.setTitle("Validação de Conferente");

                LinearLayout layout = new LinearLayout(RemetenteSetorActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(50, 30, 50, 0);

                final EditText txtIdUsuario = new EditText(RemetenteSetorActivity.this);
                txtIdUsuario.setText(codigoConferente);

                txtIdUsuario.setInputType(InputType.TYPE_CLASS_NUMBER);
                //txtIdUsuario.setPadding(10,10,10,10);

                layout.addView(txtIdUsuario, layoutParams);

                final EditText txtSenha = new EditText(RemetenteSetorActivity.this);
                txtSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //txtSenha.setPadding(10,10,10,10);
                layout.addView(txtSenha, layoutParams);


                alert.setView(layout);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String cIdUsuario = txtIdUsuario.getText().toString();
                        String cSenha = txtSenha.getText().toString();

                        if (cIdUsuario.isEmpty() || cSenha.isEmpty()){
                            alert("Informe os dados de validação do conferente!");
                            return ;
                        }


                        UsuariosSistema usuariosSistema = manager.findUsuariosSistemaById(Long.valueOf(cIdUsuario));

                        if (usuariosSistema != null){

                            if (!usuariosSistema.getSenha().equals(cSenha)) {
                                alert("Senha do usuário não confere!");
                                return;
                            }

                        } else {
                            alert("Usuário inexistente");
                            return ;
                        }


                        myapp.setCodigoConferente(cIdUsuario);

                        protocolo = manager.findProtocoloSetorById(idProtocoloSetor);
                        List<ProtocoloRemetente> regs = protocolo.getRemetentes();

                        int qtdConferencia;
                        int qtdConferidos;

                        qtdConferencia = 0;
                        qtdConferidos = 0;

                        for (int i=0;i < regs.size(); i++){

                            ProtocoloRemetente reg = regs.get(i);

                            if (reg.getStatus()==1) {
                                qtdConferidos++;
                            }
                            qtdConferencia = qtdConferencia + reg.getQtdConferencia();
                        }

                        if (qtdConferidos < regs.size()) {
                            alert("Existem conferências pendentes!");
                            return ;
                        }

                        if (qtdConferencia != protocolo.getQtdVolumes()) {
                            Intent intent = new Intent(getApplicationContext(),BaixaConferenciaActivity.class);
                            intent.putExtra("idUsuarioConferencia",usuariosSistema.getId());
                            intent.putExtra("idProtocoloSetor",String.valueOf(idProtocoloSetor));
                            startActivity(intent);
                        } else {

                            Long usuarioId = usuariosSistema.getId();
                            Date data_conferencia = new Date();

                            protocolo.setQtdConferencia(qtdConferencia);
                            protocolo.setIdUsuarioConferencia(usuarioId);
                            protocolo.getProtocolo().setIdUsuarioConferencia(usuarioId);

                            protocolo.setDataConferencia(Util.getDateTimeFormatYMD(data_conferencia));
                            protocolo.getProtocolo().setDataConferencia(Util.getDateTimeFormatYMD(data_conferencia));


                            protocolo.getProtocolo().setStatus(true);
                            protocolo.getProtocolo().setEnviar(true);
                            myapp.getDaoSession().update(protocolo);
                            myapp.getDaoSession().update(protocolo.getProtocolo());

                            try {
                                dataSync.sendProtocolos();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            goBack();

                        }
                        Toast.makeText(RemetenteSetorActivity.this, "Saved Sucessfully", Toast.LENGTH_LONG).show();
                    }

                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        return ;
                    }
                });

                alert.show();
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void setupRecycler() {
        mRecyclerView = findViewById(R.id.lista);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ProtocoloRemetenteAdapter(protocoloRemetentes, mListener);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );
    }

    private void goBack() {
        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        in.putExtra("flagServico", "nao");
        startActivity(in);
    }


    public void getNotasFiscais() {

        if (!verificaConexao())
            return;

        pb.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        String codigoAcesso = String.valueOf(myapp.getUsuario().getCodigoAcesso());

        String url = "http://api.softlog.eti.br/api/softlog/notasromaneio?codigo_acesso=" +
                codigoAcesso + "&id_protocolo=" + String.valueOf(protocolo.getProtocoloId()) + "&tipo_busca=1";
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url", url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonExtractNotasFiscais.extract(response);
                        pb.setVisibility(View.INVISIBLE);
                        pb.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.GONE);
                //Log.d("ERRO", error.toString());
            }
        });
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest, "NotasFiscais");
    }

    public boolean verificaConexao() {
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


    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(),ProtocoloActivity.class);
        in.putExtra("id_protocolo_setor",String.valueOf(idProtocoloSetor));
        in.putExtra("id_setor",protocolo.getRegiaoId());
        //idSetor = inProtocolo.getLongExtra("id_setor",Long.valueOf(0));
        startActivity(in);
        finish();
        return true;

    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
        //super.onBackPressed();
    }
}
