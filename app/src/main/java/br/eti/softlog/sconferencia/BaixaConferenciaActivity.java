package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Date;
import java.util.List;

import br.eti.softlog.model.ProtocoloRemetente;
import br.eti.softlog.model.ProtocoloSetor;

import br.eti.softlog.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaixaConferenciaActivity extends AppCompatActivity {



    ConferenceApp app;
    Manager manager;
    DataSync dataSync;
    ProtocoloSetor protocolo;

    Long idProtocoloSetor;
    Long idUsuarioConferencia;
    Intent inCall;

    @BindView(R.id.editObservacao)
    EditText editObservacao;

    @BindView(R.id.btnFinalizar)
    Button btnFinalizar;

    @OnClick(R.id.btnFinalizar) void submit(){
        if (editObservacao.getText().toString().trim().equals("")){
            alert("Favor fazer as observações!");
            return ;
        }
        List<ProtocoloRemetente> regs = protocolo.getRemetentes();

        int qtdConferencia;

        qtdConferencia = 0;

        for (int i=0;i < regs.size(); i++){
            ProtocoloRemetente reg = regs.get(i);
            qtdConferencia = qtdConferencia + reg.getQtdConferencia();
        }


        Long usuarioId = app.getUsuario().getId();
        Date data_conferencia = new Date();

        protocolo.setQtdConferencia(qtdConferencia);
        protocolo.setIdUsuarioConferencia(usuarioId);

        protocolo.getProtocolo().setIdUsuarioConferencia(usuarioId);
        protocolo.getProtocolo().setObservacao(editObservacao.getText().toString());

        protocolo.setDataConferencia(Util.getDateTimeFormatYMD(data_conferencia));
        protocolo.getProtocolo().setDataConferencia(Util.getDateTimeFormatYMD(data_conferencia));


        protocolo.getProtocolo().setStatus(true);
        protocolo.getProtocolo().setEnviar(true);
        app.getDaoSession().update(protocolo);
        app.getDaoSession().update(protocolo.getProtocolo());

        try {
            dataSync.sendProtocolos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        goBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baixa_conferencia);
        ButterKnife.bind(this);

        app = (ConferenceApp)getApplicationContext();
        manager = new Manager(app);
        dataSync = new DataSync(getApplicationContext());

        inCall = getIntent();

        String cIdProtocoloSetor = inCall.getStringExtra("idProtocoloSetor");
        idProtocoloSetor = Long.valueOf(cIdProtocoloSetor);
        idUsuarioConferencia = inCall.getLongExtra("idUsuarioConferencia",Long.valueOf(0));
        protocolo = manager.findProtocoloSetorById(idProtocoloSetor);


    }

    private void alert(String s){
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }


    private void goBack(){
        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        in.putExtra("flagServico","nao");
        startActivity(in);
    }
}
