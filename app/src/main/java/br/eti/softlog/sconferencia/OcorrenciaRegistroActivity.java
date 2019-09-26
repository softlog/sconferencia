package br.eti.softlog.sconferencia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.eti.softlog.ImageCrop.CropResultActivity;
import br.eti.softlog.ImageCrop.MainActivityCrop;
import br.eti.softlog.model.AnexosOcorrencias;
import br.eti.softlog.model.AnexosOcorrenciasDao;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import br.eti.softlog.model.Ocorrencias;
import br.eti.softlog.model.OcorrenciasDao;
import br.eti.softlog.utils.AlphaNumericInputFilter;
import br.eti.softlog.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OcorrenciaRegistroActivity extends AppCompatActivity {


    @BindView(R.id.txt_numero_nota)
    TextView txtNumeroNota;

    @BindView(R.id.txt_remetente)
    TextView txtRemetente;

    @BindView(R.id.txt_destinatario)
    TextView txtDestinatario;

    @BindView(R.id.txt_volumes)
    TextView txtVolumes;

    @BindView(R.id.txt_ocorrencia)
    TextView txtOcorrencia;

    @BindView(R.id.btn_ocorrencia)
    Button btnOcorrencia;

    @BindView(R.id.ev_observacao)
    AppCompatEditText evObservacao;

    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;



    ConferenceApp app;
    Manager manager;
    Util util;
    Long idNotaFiscal;
    String cOperacao;
    NotasFiscais notaFiscal;
    List<AnexosOcorrencias> anexos;
    Long remetenteId;
    Long protocoloId;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ocorrencia_registro);

        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Ocorrência");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (ConferenceApp)getApplication();
        manager = new Manager(app);
        util = new Util();
        Intent inCall = getIntent();
        idNotaFiscal = inCall.getLongExtra("id_nota_fiscal", Long.valueOf(0));
        protocoloId = inCall.getLongExtra("protocolo_id",Long.valueOf(0));
        remetenteId = inCall.getLongExtra("remetente_id",Long.valueOf(0));
        cOperacao = inCall.getStringExtra("operacao");

        if (cOperacao == null)
            cOperacao = "";

        notaFiscal = app.getDaoSession().getNotasFiscaisDao()
                .queryBuilder()
                .where(NotasFiscaisDao.Properties.Id.eq(idNotaFiscal))
                .unique();

        anexos = app.getDaoSession().getAnexosOcorrenciasDao().queryBuilder()
                .where(AnexosOcorrenciasDao.Properties.IdNotaFiscalImp.eq(notaFiscal.getIdNotaFiscalImp()))
                .list();

        txtRemetente.setText(notaFiscal.getRemetente().getNome());
        txtDestinatario.setText(notaFiscal.getDestinatario().getNome());
        txtNumeroNota.setText(notaFiscal.getSerie()+ "-" + notaFiscal.getNumero());
        txtVolumes.setText(notaFiscal.getVolumes().toString());

        if (notaFiscal.getObservacao() != null)
            evObservacao.setText(notaFiscal.getObservacao());

        ArrayList<InputFilter> curInputFilters = new ArrayList<InputFilter>(Arrays.asList(evObservacao.getFilters()));
        //curInputFilters.add(0, new AlphaNumericInputFilter());
        curInputFilters.add(0, new InputFilter.AllCaps());
        InputFilter[] newInputFilters = curInputFilters.toArray(new InputFilter[curInputFilters.size()]);
        evObservacao.setFilters(newInputFilters);

        initViews();
        hideKeyboard();

        if (notaFiscal.getOcorrencia()!=null && notaFiscal.getTem_ocorrencia()){
            txtOcorrencia.setText(notaFiscal.getOcorrencia().toString());
        }

        final List<String> listaOcorrencias = new ArrayList<>();

        List<Ocorrencias> ocorrencias = app.getDaoSession().getOcorrenciasDao().queryBuilder()
                .where(OcorrenciasDao.Properties.Ativo.eq(true))
                .orderAsc(OcorrenciasDao.Properties.Id).list();


        for (Ocorrencias o:ocorrencias){
            listaOcorrencias.add(o.toString());
        }


        adapter = new ArrayAdapter<String>(OcorrenciaRegistroActivity.this,
                R.layout.simple_list, listaOcorrencias);

        btnOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialog = DialogPlus.newDialog(OcorrenciaRegistroActivity.this)
                        .setAdapter(adapter)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, final int position) {
                                //Toast.makeText(activity.getApplicationContext(),"Posição "+ String.valueOf(position),Toast.LENGTH_LONG).show();
                                Ocorrencias o;
                                o = ocorrencias.get(position);
                                txtOcorrencia.setText(o.toString());
                                //avaliacaoAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setExpanded(false)
                        .setOverlayBackgroundResource(R.color.dialogplus_black_overlay)
                        .setContentBackgroundResource(R.color.colorWhite)
                        .setPadding(8, 8, 8, 8)
                        .setGravity(Gravity.CENTER)
                        .create();
                dialog.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {


        Intent in;
        if (cOperacao.equals("alterar")){
            in = new Intent(getApplicationContext(),OcorrenciasListActivity.class);
            in.putExtra("protocolo_id",protocoloId);
            in.putExtra("remetente_id",remetenteId);
        } else{
            Long idProtocoloSetor = notaFiscal.getProtocolo().getProtocoloSetores().get(0).getId();
            in = new Intent(getApplicationContext(),RemetenteSetorActivity.class);
            in.putExtra("id_protocolo_setor",String.valueOf(idProtocoloSetor));
        }


        startActivity(in);
        finish();
        return true;
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );


        DataAdapterAnexos adapter = new DataAdapterAnexos(getApplicationContext(),anexos);
        recyclerView.setAdapter(adapter);

    }
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_registro_ocorrencia,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_salvar: {
                String observacao;
                observacao = evObservacao.getText().toString();

                if (observacao.isEmpty()){
                    new MaterialDialog.Builder(OcorrenciaRegistroActivity.this)
                            .title("Mensagem")
                            .content("Favor, digitar alguma observação.")
                            .positiveText("OK")
                            .show();
                        break;
                }

                String ocorrencia ;
                ocorrencia = txtOcorrencia.getText().toString();
                if (ocorrencia.isEmpty()){
                    new MaterialDialog.Builder(OcorrenciaRegistroActivity.this)
                            .title("Mensagem")
                            .content("Favor, informar uma ocorrência.")
                            .positiveText("OK")
                            .show();
                    break;
                }


                notaFiscal.setObservacao(observacao);


                notaFiscal.setTem_ocorrencia(true);
                notaFiscal.setIdOcorrencia(Long.valueOf(txtOcorrencia.getText().toString().substring(0,3)));
                app.getDaoSession().update(notaFiscal);

                new MaterialDialog.Builder(OcorrenciaRegistroActivity.this)
                        .title("Mensagem")
                        .content("Nota Fiscal salva com sucesso!")
                        .positiveText("OK")
                        .show();
                break;

            }
            case R.id.menu_anexar: {



                Intent in = new Intent(getApplicationContext(), MainActivityCrop.class);
                in.putExtra("id_nota_fiscal",notaFiscal.getId());
                startActivity(in);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
