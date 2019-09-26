package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.utils.Util;

public class ProtocoloActivity extends AppCompatActivity {

    ConferenceApp myapp ;
    ListView lista_protocolo;
    Manager manager;
    DataSync dataSync;
    ProtocoloSetor protocolo;
    Util util;

    TextView txtNumeroProtocolo;
    TextView txtSetor;
    TextView txtDataProtocolo;
    TextView txtQtdNotas;
    TextView txtQtdVolumes;
    TextView txtQtdConferencia;
    TextView txtUsuarioConferencia;
    TextView txtObservacao;

    Button btnGravar;
    Button btnCancelar;

    Long idProtocolo;
    Long idSetor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolo_view);

        myapp = (ConferenceApp)getApplicationContext();
        manager = new Manager(myapp);
        util = new Util();
        dataSync = new DataSync(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Protocolo");

        Intent inProtocolo = getIntent();
        idSetor = inProtocolo.getLongExtra("id_setor",Long.valueOf(0));
        String sIdProtocoloSetor = inProtocolo.getStringExtra("id_protocolo_setor");
        idProtocolo = Long.valueOf(sIdProtocoloSetor);
        protocolo = manager.findProtocoloSetorById(idProtocolo);

        txtNumeroProtocolo = findViewById(R.id.txtNumeroProtocolo);
        txtDataProtocolo = findViewById(R.id.txtDataProtocolo);
        txtSetor = findViewById(R.id.txtSetor);
        txtQtdNotas = findViewById(R.id.txtQtdNotas);
        txtQtdVolumes = findViewById(R.id.txtQtdVolumes);
        txtQtdConferencia = findViewById(R.id.txtQtdConferencia);
        txtUsuarioConferencia = findViewById(R.id.txtUsuarioConferencia);
        txtObservacao = findViewById(R.id.txtObservacao);
        btnGravar = findViewById(R.id.btnGravar);
        btnCancelar = findViewById(R.id.btnCancelar);


        txtNumeroProtocolo.setText(protocolo.getProtocoloId().toString());
        txtDataProtocolo.setText(protocolo.getProtocolo().getData_protocolo().toString());
        txtSetor.setText(protocolo.getRegiao().getDescricao().toString());
        txtQtdNotas.setText(String.valueOf(protocolo.getQtdNotas()));
        txtQtdVolumes.setText(String.valueOf(protocolo.getQtdVolumes()));
        txtQtdConferencia.setText(String.valueOf(protocolo.getQtdConferencia()));

        if (protocolo.getUsuarioConferencia() !=null)
            txtUsuarioConferencia.setText(protocolo.getUsuarioConferencia().getNome().trim());
        else
            txtUsuarioConferencia.setText("Não conferido");


        txtObservacao.setText(protocolo.getProtocolo().getObservacao());

        if (protocolo.getProtocolo().getStatus() &&
          myapp.getUsuario().getId() != protocolo.getProtocolo().getIdUsuarioConferencia()){
            //txtQtdConferencia.setEnabled(false);
            btnGravar.setEnabled(false);
            btnGravar.setEnabled(false);
        }


        btnCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSupportNavigateUp();
                    }
                }
        );
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent in = new Intent(getApplicationContext(),ProtocoloListActivity.class);
        in.putExtra("id_setor",Long.valueOf(idSetor));
        startActivity(in);
        finish();
        return true;
    }

    private void alert(String s){
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocolo,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.make_romaneio:{

                if (!protocolo.getProtocolo().getStatus()) {
                    Toast.makeText(myapp, "Não é possível criar romaneio para protocolo sem conferência!"
                            , Toast.LENGTH_LONG).show();
                    break;
                };

                if (protocolo.getIdRomaneio() != null){
                    Toast.makeText(myapp, "Protocolo já tem romaneio!"
                            , Toast.LENGTH_LONG).show();
                    break;
                }

                Intent in = new Intent(getApplicationContext(),RomaneioNovo.class);
                in.putExtra("idProtocolo",idProtocolo);
                startActivity(in);

                break;
            }
            case R.id.conferir:{
                Long idUsuarioConferencia;
                Intent intent = new Intent(getApplicationContext(), RemetenteSetorActivity.class);
                // seta o parametro do medico a exibir os dados
                intent.putExtra("id_protocolo_setor", protocolo.getId().toString());
                startActivity(intent);
                finish();

                /*
                if (protocolo.getUsuarioConferencia() != null)
                    //Se tem conferencia, usuario so pode abrir a tela de conferencia
                    //que ele conferiu
                    idUsuarioConferencia = protocolo.getIdUsuarioConferencia();
                else
                    //Se não tem conferencia, usuario pode abrir tela de conferir
                    idUsuarioConferencia = myapp.getUsuario().getId();

                if (idUsuarioConferencia == myapp.getUsuario().getId()) {
                    Intent intent = new Intent(getApplicationContext(), RemetenteSetorActivity.class);
                    // seta o parametro do medico a exibir os dados
                    intent.putExtra("id_protocolo_setor", protocolo.getId().toString());
                    startActivity(intent);
                    finish();
                }
                */
                break;
            }
            case R.id.ocorrencias:{
                Intent intent = new Intent(getApplicationContext(), OcorrenciasListActivity.class);
                // seta o parametro do medico a exibir os dados
                intent.putExtra("id_protocolo_setor", protocolo.getId());
                intent.putExtra("protocolo_id",protocolo.getProtocoloId());
                startActivity(intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
        //super.onBackPressed();
    }
}
