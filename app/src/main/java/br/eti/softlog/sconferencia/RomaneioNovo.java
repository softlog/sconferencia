package br.eti.softlog.sconferencia;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sortabletableview.recyclerview.TableDataColumnAdapterDelegator;
import com.sortabletableview.recyclerview.TableView;
import com.sortabletableview.recyclerview.model.TableColumnWeightModel;
import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;
import com.sortabletableview.recyclerview.toolkit.SimpleTableHeaderAdapter;
import com.sortabletableview.recyclerview.toolkit.TableDataRowBackgroundProviders;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.eti.softlog.ColumnAdapterNotasFiscais.NotasFiscaisStringValuesExtractors;
import br.eti.softlog.JsonExtract.JsonExtractNotasFiscais;
import br.eti.softlog.model.Motoristas;
import br.eti.softlog.model.MotoristasDao;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.Romaneios;
import br.eti.softlog.model.Veiculos;
import br.eti.softlog.utils.AppSingleton;
import br.eti.softlog.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class RomaneioNovo extends AppCompatActivity {

    Long idProtocolo;
    Manager manager;
    DataSync dataSync;
    ProtocoloSetor protocolo;
    List<NotasFiscais> notasFiscais;
    Util util;

    ConferenceApp app = (ConferenceApp)getApplication();;
    JsonExtractNotasFiscais jsonExtractNotasFiscais;

    List<Veiculos> veiculos ;
    List<Motoristas> motoristas;

    @BindView(R.id.id_protocolo)
    TextView txtIdProtocolo;

    @BindView(R.id.btnVeiculo)
    Button btnVeiculo;

    @BindView(R.id.txtVeiculo)
    TextView txtVeiculo;

    @BindView(R.id.txtMotorista)
    TextView txtMotorista;

    @BindView(R.id.txtCpfMotorista)
    TextView txtCpfMotorista;

    @BindView(R.id.btnMotorista)
    Button btnMotorista;

    @BindView(R.id.txt_setor)
    TextView txtSetor;

    @BindView(R.id.pbMain)
    ProgressBar pb;

    String placaVeiculo;
    String cnpjMotorista;

    TableView<NotasFiscais> tableView;
    TableDataColumnAdapterDelegator<NotasFiscais> dataAdapter;

    //@BindView(R.id.searchVeiculo)
//    SearchableSpinner

    private ArrayList<SearchModel> createVeiculos(){
        veiculos = app.getDaoSession().getVeiculosDao().loadAll();
        ArrayList<SearchModel> items = new ArrayList<>();
        for (int i=0;i<veiculos.size();i++)
            items.add(new SearchModel(veiculos.get(i).toString()));
        return items;
    };

    private ArrayList<SearchModel> createMotoristas(){
        motoristas = app.getDaoSession().getMotoristasDao()
                .queryBuilder().orderAsc(MotoristasDao.Properties.Nome).list();

        ArrayList<SearchModel> items = new ArrayList<>();
        for (int i=0;i<motoristas.size();i++)
            items.add(new SearchModel(motoristas.get(i).getNome()));
        return items;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romaneio_novo);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Romaneio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (ConferenceApp)getApplication();
        manager = new Manager(app);
        util = new Util();
        jsonExtractNotasFiscais = new JsonExtractNotasFiscais(getApplicationContext());
        dataSync = new DataSync(getApplicationContext());

        pb.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.GONE);

        Intent inCall = getIntent();

        idProtocolo = inCall.getLongExtra("idProtocolo",0);

        protocolo = manager.findProtocoloSetorById(idProtocolo);

        txtIdProtocolo.setText(String.valueOf(idProtocolo));
        txtSetor.setText(protocolo.getRegiao().getDescricao());


        btnVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(RomaneioNovo.this, "Pesquisando...",
                        "Escolha um veículo...?", null, createVeiculos(),
                        new SearchResultListener<SearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   SearchModel item, int position) {

                                placaVeiculo = veiculos.get(position).getPlaca_veiculo();
                                txtVeiculo.setText(placaVeiculo.substring(0,3)+
                                        placaVeiculo.substring(3,7));
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        btnMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(RomaneioNovo.this, "Pesquisando...",
                        "Escolha um Motorista...?", null, createMotoristas(),
                        new SearchResultListener<SearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   SearchModel item, int position) {

                                Motoristas motorista = motoristas.get(position);

                                txtCpfMotorista.setText(motorista.getCnpjCpf().trim());
                                txtMotorista.setText(motorista.getNome());

                                dialog.dismiss();
                            }
                        }).show();
            }
        });
//Setup da Tabela
        tableView = findViewById(R.id.tableView);

        // set up header adapter
        final SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getApplicationContext(),
                "Série","Número", "CEP", "Remetente", "Destinatário","Cidade", "UF");
        headerAdapter.setTextSize(12);
        headerAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        tableView.setHeaderAdapter(headerAdapter);
        getNotasFiscais();
    }

    public void getNotasFiscais() {

        if (!verificaConexao())
            return;

        pb.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        String codigoAcesso = String.valueOf(app.getUsuario().getCodigoAcesso());

        String url = "http://api.softlog.eti.br/api/softlog/notasromaneio?codigo_acesso=" +
                codigoAcesso + "&id_protocolo=" + String.valueOf(protocolo.getProtocoloId()) +"&tipo_busca=1";
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

                        notasFiscais = app.getDaoSession().getNotasFiscaisDao()
                                .queryBuilder()
                                .where(NotasFiscaisDao.Properties.IdNfProtocolo.eq(protocolo.getProtocoloId()))
                                .orderAsc(NotasFiscaisDao.Properties.RemetenteId,NotasFiscaisDao.Properties.Numero)
                                .list();

                        dataAdapter = new TableDataColumnAdapterDelegator<>(getApplicationContext(), notasFiscais);

                        int sizeFont = 12;
                        int i;
                        i = 0;

                        SimpleTableDataColumnAdapter colunaSerie = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forSerie());
                        colunaSerie.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaSerie);

                        i++;
                        SimpleTableDataColumnAdapter colunaNumero = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forNumero());
                        colunaNumero.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaNumero);

                        i++;
                        SimpleTableDataColumnAdapter colunaCep = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forCep());
                        colunaCep.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaCep);

                        i++;
                        SimpleTableDataColumnAdapter colunaRemetente = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forRemetente());
                        colunaRemetente.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaRemetente);

                        i++;
                        SimpleTableDataColumnAdapter colunaDestinatario = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forDestinatario());
                        colunaDestinatario.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaDestinatario);

                        i++;
                        SimpleTableDataColumnAdapter colunaCidade = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forCidade());
                        colunaCidade.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaCidade);

                        i++;
                        SimpleTableDataColumnAdapter colunaUF = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forUf());
                        colunaUF.setTextSize(sizeFont);
                        dataAdapter.setColumnAdapter(i,colunaUF);

                        final int colorOddRows = ContextCompat.getColor(getApplicationContext(), R.color.colorOddRows);
                        final int colorEvenRows = ContextCompat.getColor(getApplicationContext(), R.color.colorEvenRows);
                        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));

                        tableView.setDataAdapter(dataAdapter);
                        final TableColumnWeightModel tableColumnModel;
                        tableColumnModel = new TableColumnWeightModel(7);

                        tableColumnModel.setColumnWeight(0,1);
                        tableColumnModel.setColumnWeight(1, 2);
                        tableColumnModel.setColumnWeight(2, 2);
                        tableColumnModel.setColumnWeight(3, 3);
                        tableColumnModel.setColumnWeight(4, 3);
                        tableColumnModel.setColumnWeight(5, 3);
                        tableColumnModel.setColumnWeight(6, 1);
                        tableView.setColumnModel(tableColumnModel);

                        final HorizontalScrollView horizontalScrollView = findViewById(R.id.scroll_view);
                        tableView.registerHorizontalScrollView(horizontalScrollView);

                        //tableView.addDataClickListener(new MainActivity.ProtocoloClickListener());
                        //tableView.addDataLongClickListener(new MainActivity.ProtocoloLongClickListener());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pb.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.GONE);
                //Log.d("ERRO", error.toString());
            }
        });
        AppSingleton.getInstance(app.getApplicationContext()).addToRequestQueue(stringRequest, "NotasFiscais");
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_romaneio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.finalizar){

            //Verificar se tem motorista
            String placa = txtVeiculo.getText().toString();
            if (placa.isEmpty()){
                Snackbar.make(getWindow().getDecorView(), "Selecione um veículo!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
                return true;
            }

            String motorista = txtCpfMotorista.getText().toString();
            if (motorista.isEmpty()){
                Snackbar.make(getWindow().getDecorView(), "Selecione um motorista!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            }

            Romaneios romaneio = new Romaneios();
            romaneio.setDataRegistro(util.getDateTimeFormatYMD(new Date()));
            romaneio.setDataRomaneio(util.getDateTimeFormatYMD(new Date()));
            romaneio.setPlacaVeiculo(placa);
            romaneio.setIdMotorista(manager.findMotoristaByCnpjCpf(motorista).getId());
            romaneio.setIdOrigem(protocolo.getRegiao().getIdCidadePolo());
            romaneio.setIdDestino(protocolo.getRegiao().getIdCidadePolo());
            romaneio.setIdSetor(protocolo.getRegiaoId());
            romaneio.setIdUsuario(app.getUsuario().getId());
            romaneio.setSincronizado(false);
            romaneio.setConcluido(true);
            app.getDaoSession().insert(romaneio);

            for(int i=0; i<notasFiscais.size();i++){
                NotasFiscais nf = notasFiscais.get(i);
                nf.setIdRomaneio(romaneio.getId());
                app.getDaoSession().update(nf);
            }

            protocolo.setIdRomaneio(romaneio.getId());
            app.getDaoSession().update(protocolo);

            try {
                dataSync.sendRomaneios();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(app, "Romaneio criado com sucesso!", Toast.LENGTH_SHORT).show();

            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(in);
            finish();
            //Verificar se tem veiculo
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent in = new Intent(getApplicationContext(), ProtocoloActivity.class);
        in.putExtra("id_protocolo_setor",String.valueOf(protocolo.getId()));
        startActivity(in);
        finish();

        return true;
    }

    public class SearchModel implements Searchable {
        private String mTitle;

        public SearchModel(String title) {
            mTitle = title;
        }

        @Override
        public String getTitle() {
            return mTitle;
        }

        public SearchModel setTitle(String title) {
            mTitle = title;
            return this;
        }


    }
}
