package br.eti.softlog.sconferencia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pixplicity.easyprefs.library.Prefs;
import com.sortabletableview.recyclerview.TableDataColumnAdapterDelegator;
import com.sortabletableview.recyclerview.TableView;
import com.sortabletableview.recyclerview.listeners.TableDataClickListener;
import com.sortabletableview.recyclerview.listeners.TableDataLongClickListener;
import com.sortabletableview.recyclerview.model.TableColumnWeightModel;
import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;
import com.sortabletableview.recyclerview.toolkit.SimpleTableHeaderAdapter;
import com.sortabletableview.recyclerview.toolkit.TableDataRowBackgroundProviders;

import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.ColumnAdapterProtocolo.ProtocoloConferenciaColumnAdapter;
import br.eti.softlog.ColumnAdapterProtocolo.ProtocoloSetorStringValueExtractors;
import br.eti.softlog.JsonExtract.JsonExtractProtocolo;
import br.eti.softlog.model.DaoSession;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.model.RegiaoDao;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.eti.softlog.utils.Util.getDateFormat;

public class ProtocoloListActivity extends AppCompatActivity   {

    //Basicos
    ConferenceApp myapp;
    Manager manager;
    DataSync dataSync;
    JsonExtractProtocolo jsonExtractProtocolo;
    static final int DATE_DIALOG_ID = 0;

    Long idSetor;

    //Modelo de Dados
    List<ProtocoloSetor> protocolos;

    @BindView(R.id.txt_setor) TextView txtSetor;


    TableView<ProtocoloSetor> tableView;
    TableDataColumnAdapterDelegator<ProtocoloSetor> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolo_list);

        Prefs.putInt("filtro_status",0);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Protocolos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        myapp = (ConferenceApp) getApplicationContext();
        manager = new Manager(myapp);
        dataSync = new DataSync(getApplicationContext());


        Intent inCall = getIntent();

        idSetor = inCall.getLongExtra("id_setor",Long.valueOf(0));

        if (idSetor>0){
            Regiao setor = myapp.getDaoSession().getRegiaoDao().queryBuilder()
                    .where(RegiaoDao.Properties.Id.eq(idSetor)).unique();

            txtSetor.setText(setor.getDescricao().trim());
        } else {
            txtSetor.setText("Todos Setores");
        }

        //----------------------------------------------------------------------------------
        DaoSession session = myapp.getDaoSession();

        ProtocoloSetorDao protocoloSetorDao = session.getProtocoloSetorDao();
        protocolos = myapp.getDaoSession().getProtocoloSetorDao().queryBuilder()
                .where(ProtocoloSetorDao.Properties.Id.eq(-1)).list();

        //myapp.getDaoSession().getVeiculosDao().queryBuilder().LOG_SQL = true;
        //myapp.getDaoSession().getVeiculosDao().queryBuilder().LOG_VALUES = true;

        //Setup da Tabela
        tableView = findViewById(R.id.tableView);

        // set up header adapter
        final SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getApplicationContext(),
                "Número", "Região", "Vol.", "Contagem","NF");

        headerAdapter.setTextSize(12);
        headerAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

        dataAdapter = new TableDataColumnAdapterDelegator<>(getApplicationContext(), protocolos);

        int sizeFont = 14;
        int i;
        i = 0;

        SimpleTableDataColumnAdapter colunaNumero = new SimpleTableDataColumnAdapter<>(ProtocoloSetorStringValueExtractors.forProtocoloNumero());
        colunaNumero.setTextSize(12);

        colunaNumero.setPaddingTop(30);
        colunaNumero.setPaddingBottom(30);
        dataAdapter.setColumnAdapter(i,colunaNumero);

        i++;
        SimpleTableDataColumnAdapter colunaRegiao = new SimpleTableDataColumnAdapter<>(ProtocoloSetorStringValueExtractors.forProtocoloRegiao());
        colunaRegiao.setTextSize(12);
        dataAdapter.setColumnAdapter(i,colunaRegiao);

        i++;
        SimpleTableDataColumnAdapter colunaVolumes = new SimpleTableDataColumnAdapter<>(ProtocoloSetorStringValueExtractors.forProtocoloVolumes());
        colunaVolumes.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaVolumes);

        i++;
        ProtocoloConferenciaColumnAdapter colunaConferidos = new ProtocoloConferenciaColumnAdapter();
        dataAdapter.setColumnAdapter(i,colunaConferidos);

        i++;
        SimpleTableDataColumnAdapter colunaQtdNfe = new SimpleTableDataColumnAdapter<>(ProtocoloSetorStringValueExtractors.forProtocoloQtdNF());
        colunaQtdNfe.setTextSize(sizeFont);

        dataAdapter.setColumnAdapter(i,colunaQtdNfe);

        final int colorOddRows = ContextCompat.getColor(getApplicationContext(), R.color.colorOddRows);
        final int colorEvenRows = ContextCompat.getColor(getApplicationContext(), R.color.colorEvenRows);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
        tableView.setHeaderAdapter(headerAdapter);
        tableView.setDataAdapter(dataAdapter);

        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(5);

        tableColumnModel.setColumnWeight(0,1);
        tableColumnModel.setColumnWeight(1, 2);
        tableColumnModel.setColumnWeight(2, 1);
        tableColumnModel.setColumnWeight(3, 1);
        tableColumnModel.setColumnWeight(4, 3);
        tableView.setColumnModel(tableColumnModel);



        final HorizontalScrollView horizontalScrollView = findViewById(R.id.scroll_view);
        tableView.registerHorizontalScrollView(horizontalScrollView);

        tableView.addDataClickListener(new ProtocoloClickListener());
        tableView.addDataLongClickListener(new ProtocoloLongClickListener());

        Intent intentSource = getIntent();
        String flagServico = intentSource.getStringExtra("flagServico");


        //Date ultimaDataExpedicao = manager.findMaxDataExpedicaoProtocolo();
        //myapp.setDate(ultimaDataExpedicao);

        //Log.d("Data Corrente", myapp.getDate().toString());

        reloadAllData();
        //reloadAllData();



        //View Content Main
        //progressBar.setVisibility(View.INVISIBLE);
        //progressBar.setVisibility(View.GONE);

        //txtDate.setText(getDateFormat(myapp.getDate()));

        //Configuracao de Views
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DATE_DIALOG_ID);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_protoloco_list, menu);



        final List<String> listaStatus = new ArrayList<>();
        listaStatus.add("Todos");
        listaStatus.add("N.Conferidos");
        listaStatus.add("Conferidos Ok");
        listaStatus.add("Com ocorrência");

        MenuItem itemFazenda = menu.findItem(R.id.spinner_status);
        MaterialSpinner spinnerStatus = (MaterialSpinner) itemFazenda.getActionView();
        spinnerStatus.setBackgroundColor(Color.parseColor("#3F51B5"));
        spinnerStatus.setTextColor(Color.WHITE);
        spinnerStatus.setVerticalScrollBarEnabled(true);
        spinnerStatus.setDropdownHeight(600);
        spinnerStatus.setTextSize(16);
        spinnerStatus.setItems(listaStatus);

        spinnerStatus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Prefs.putInt("filtro_status",position);
                reloadAllData();
            }
        });

        spinnerStatus.setSelectedIndex(Prefs.getInt("filtro_status",0));



        SearchView mSearchView = (SearchView) menu.findItem(R.id.localizar)
                .getActionView();
        //mSearchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        //
        mSearchView.setQueryHint("Busca por setor");

        //lista_entregas = findViewById(R.id)
        // exemplos de utilização:
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    reloadAllData(query);
                } catch (Exception e) {
                    //Log.d("Erro ao converter",e.toString());
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.i("well", " this worked");
                return false;
            }
        });


        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // ******************** Interesting Code Section **********************************************************
                reloadAllData();
                // ******************** Interesting Code Section **********************************************************
                return false;
            }
        });
        return true;
    }



    private void alert(String s){
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }

    private void reloadAllData(){

        int status = Prefs.getInt("filtro_status",0);

        if (status == 0){
            protocolos = manager.findProtocoloSetorByDataExpedicao(myapp.getDate(),idSetor);
        } else if (status==1) {
            protocolos = manager.findProtocoloSetorByDataExpedicaoStatus(myapp.getDate(),false, idSetor);
        } else if (status==2) {
            protocolos = manager.findProtocoloSetorByDataExpedicaoStatusOk(myapp.getDate(), idSetor);
        }  else if (status==3) {
            protocolos = manager.findProtocoloSetorByDataExpedicaoStatusAlert(myapp.getDate(), idSetor);
        }

        dataAdapter.getData().clear();
        dataAdapter.getData().addAll(protocolos);
        dataAdapter.notifyDataSetChanged();

    }

    private void reloadAllData(String query){

        try{
            protocolos = myapp.getDaoSession().getProtocoloSetorDao()
                    .queryBuilder()
                    .where(ProtocoloSetorDao.Properties.ProtocoloId.eq(Long.valueOf(query)))
                    .list();
        } catch (Exception e){
            reloadAllData();
            return ;
        }

        dataAdapter.getData().clear();
        dataAdapter.getData().addAll(protocolos);
        dataAdapter.notifyDataSetChanged();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.localizar) {
            /*try {
                dataSync.sendProtocolos();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        return super.onOptionsItemSelected(item);

    }


    private class ProtocoloClickListener implements TableDataClickListener<ProtocoloSetor> {
        @Override
        public void onDataClicked(int rowIndex, ProtocoloSetor protocolo) {
            //Log.d("Linha ",String.valueOf(rowIndex));
            // cria a intent
            Intent intent = new Intent(getApplicationContext(), ProtocoloActivity.class);
            // seta o parametro do medico a exibir os dados
            intent.putExtra("id_protocolo_setor", protocolo.getId().toString());
            intent.putExtra("id_setor",idSetor);

            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();

        }
    }


    private static class ProtocoloLongClickListener implements
            TableDataLongClickListener<ProtocoloSetor> {
        @Override
        public void onDataLongClicked(int rowIndex, ProtocoloSetor protocolo) {
//            String clickedFlightString = "Long clicked flight with number " +
//                    protocolo.getNumber();
//            Toast.makeText(getContext(), clickedFlightString, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp(){

        Intent in = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(in);
        finish();

        return true;
    }
}
