package br.eti.softlog.sconferencia;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.sortabletableview.recyclerview.listeners.SwipeToRefreshListener;
import com.sortabletableview.recyclerview.listeners.TableDataClickListener;
import com.sortabletableview.recyclerview.listeners.TableDataLongClickListener;
import com.sortabletableview.recyclerview.model.TableColumnWeightModel;
import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;
import com.sortabletableview.recyclerview.toolkit.SimpleTableHeaderAdapter;
import com.sortabletableview.recyclerview.toolkit.TableDataRowBackgroundProviders;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Action;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.eti.softlog.ColumAdapterSetor.SetorStringValueExtrators;
import br.eti.softlog.JsonExtract.JsonExtractProtocolo;
import br.eti.softlog.model.DaoSession;
import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloDao;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.model.RegiaoDao;
import br.eti.softlog.utils.AppSingleton;
import br.eti.softlog.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.eti.softlog.utils.Util.getDate;
import static br.eti.softlog.utils.Util.getDateFormat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Basicos
    ConferenceApp myapp;
    Manager manager;
    Util util;
    DataSync dataSync;
    JsonExtractProtocolo jsonExtractProtocolo;
    static final int DATE_DIALOG_ID = 0;

    //Modelo de Dados
    List<ProtocoloSetor> protocolos;

    List<Regiao> setores;
    Long idSetor;

    @BindView(R.id.txt_date)
    TextView txtDateTitle;
    //Views
    //@BindView(R.id.txt_date)
    TextView txtDate;

    TextView txtUsuario;
    TextView txtSubTitle;
    //@BindView(R.id.txtSubTitle)

    @BindView(R.id.pbMain)
    ProgressBar progressBar;

    TableView<Regiao> tableView;
    TableDataColumnAdapterDelegator<Regiao> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        AndPermission.with(this)
                .permission(
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                //alert("Sem estas permissões o aplicativo não pode funcionar.");
                //myapp.logout();
                //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //startActivity(intent);
                ///finish();

            }
        }).start();



        AndPermission.with(this)
                .permission(
                        Permission.CAMERA
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                //alert("Sem estas permissões o aplicativo não pode funcionar.");
                //myapp.logout();
                //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //startActivity(intent);
                //finish();

            }
        }).start();

        AndPermission.with(this)
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                //alert("Sem estas permissões o aplicativo não pode funcionar.");
                //myapp.logout();
                //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                //startActivity(intent);
                //finish();

            }
        }).start();


        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Setores");
        //toolbar.setTitle("Conferência");


        myapp = (ConferenceApp) getApplicationContext();
        manager = new Manager(myapp);
        util = new Util();

        dataSync = new DataSync(getApplicationContext());
        jsonExtractProtocolo = new JsonExtractProtocolo(getApplicationContext());

        //Configuracao da Barra Lateral -----------------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        txtUsuario = (TextView) headerView.findViewById(R.id.txt_usuario);
        txtUsuario.setText(myapp.getUsuario().getNome());

        txtDate = (TextView) headerView.findViewById(R.id.txt_data);
        txtDate.setText(getDateFormat(myapp.getDate()));
        txtDateTitle.setText(txtDate.getText().toString());

        txtSubTitle = (TextView) headerView.findViewById(R.id.txt_filtro);
        txtSubTitle.setText("Todos");

        //----------------------------------------------------------------------------------
        DaoSession session = myapp.getDaoSession();

        //ProtocoloSetorDao protocoloSetorDao = session.getProtocoloSetorDao();
        setores = myapp.getDaoSession().getRegiaoDao()
                .queryBuilder()
                .where(RegiaoDao.Properties.Id.eq(-1))
                .list();

        protocolos = myapp.getDaoSession().getProtocoloSetorDao().queryBuilder()
                .where(ProtocoloSetorDao.Properties.Id.eq(-1)).list();

        //myapp.getDaoSession().getVeiculosDao().queryBuilder().LOG_SQL = true;
        //myapp.getDaoSession().getVeiculosDao().queryBuilder().LOG_VALUES = true;

        //Setup da Tabela
        tableView = findViewById(R.id.tableView);

        // set up header adapter
        final SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getApplicationContext(),
                "SETOR", "PROTOCOLOS");

        headerAdapter.setTextSize(14);
        headerAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));

        dataAdapter = new TableDataColumnAdapterDelegator<>(getApplicationContext(), setores);

        int sizeFont = 14;
        int i;
        i = 0;

        SimpleTableDataColumnAdapter colunaDescricao = new SimpleTableDataColumnAdapter<>(SetorStringValueExtrators.forDescricao());
        colunaDescricao.setTextSize(sizeFont);

        colunaDescricao.setPaddingTop(30);
        colunaDescricao.setPaddingBottom(30);
        colunaDescricao.setPaddingLeft(18);
        dataAdapter.setColumnAdapter(i,colunaDescricao);

        i++;
        SimpleTableDataColumnAdapter colunaQtd = new SimpleTableDataColumnAdapter<>(SetorStringValueExtrators.forQtdProtocolo(getApplicationContext()));
        colunaQtd.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i,colunaQtd);



        final int colorOddRows = ContextCompat.getColor(getApplicationContext(), R.color.colorOddRows);
        final int colorEvenRows = ContextCompat.getColor(getApplicationContext(), R.color.colorEvenRows);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
        tableView.setHeaderAdapter(headerAdapter);
        tableView.setDataAdapter(dataAdapter);



        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(5);

        tableColumnModel.setColumnWeight(0,3);
        tableColumnModel.setColumnWeight(1, 1);
        tableView.setColumnModel(tableColumnModel);
        //final HorizontalScrollView horizontalScrollView = findViewById(R.id.scroll_view);
        //tableView.registerHorizontalScrollView(horizontalScrollView);
        tableView.addDataClickListener(new RegiaoClickListener());
        tableView.addDataLongClickListener(new RegiaoLongClickListener());
        tableView.setSwipeToRefreshEnabled(true);
        tableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
            @Override
            public void onRefresh(RefreshIndicator refreshIndicator) {
                refreshIndicator.hide();
                getProtocolos();
            }
        });


        Intent intentSource = getIntent();
        String flagServico = intentSource.getStringExtra("flagServico");


        //Date ultimaDataExpedicao = manager.findMaxDataExpedicaoProtocolo();
        //myapp.setDate(ultimaDataExpedicao);
        txtDate.setText(getDateFormat(myapp.getDate()));
        //Log.d("Data Corrente", myapp.getDate().toString());

        //getProtocolos();
        //View Content Main
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);

        String origem = intentSource.getStringExtra("origem");
        if (origem == null)
            origem = "";

        if (origem.equals("splash"))
            getProtocolos();
        else
            reloadAllData(null);

        this.startService();



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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);

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
                reloadAllData(null);
                // ******************** Interesting Code Section **********************************************************
                return false;
            }
        });
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_atualizar) {
            getProtocolos();

            alert("Dados Atualizados.");
            // Handle the camera action
        }
        else if (id == R.id.nav_cidades) {
            // cria a intent
            Intent intent = new Intent(getApplicationContext(), CidadesRegiaoActivity.class);
            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_por_protocolo) {
            // cria a intent
            Intent intent = new Intent(getApplicationContext(), ProtocoloListActivity.class);
            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_sair) {
            myapp.logout();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendario = Calendar.getInstance();

        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, ano, mes,
                        dia);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    Date data_corrente = getDate(year,monthOfYear,dayOfMonth);
                    String dataExtenso = getDateFormat(data_corrente);
                    txtDate.setText(dataExtenso);
                    txtDateTitle.setText(txtDate.getText().toString());
                    myapp.setDate(data_corrente);
                    //reloadAllData();
                    getProtocolos();
                }
            };

    private void alert(String s){
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }

    public void startService(){
        Intent it = new Intent(this.getApplicationContext(),ConferenceService.class);
        startService(it);
    }

    public void stopService(){
        Intent it = new Intent("CONFERENCE_SERVICE");
        stopService(it);
    }

    private void reloadAllData(String cBusca){

        String cDate;

        cDate = util.getDateFormatYMD(myapp.getDate());

        QueryBuilder querySetores = myapp.getDaoSession()
                .getRegiaoDao()
                .queryBuilder();


        //querySetores.LOG_VALUES = true;
        //querySetores.LOG_SQL = true;

        Join protocoloSetores =  querySetores
                .join(ProtocoloSetor.class, ProtocoloSetorDao.Properties.RegiaoId);

        Join regiao = querySetores.join(protocoloSetores,ProtocoloSetorDao.Properties.RegiaoId,
                Regiao.class,RegiaoDao.Properties.Id);

        if (cBusca!=null)
            regiao.where(RegiaoDao.Properties.Descricao.like("%"+cBusca+"%"));

        Join protocolos = querySetores.join(
                protocoloSetores,ProtocoloSetorDao.Properties.ProtocoloId,
                Protocolo.class, ProtocoloDao.Properties.Id)
                .where(ProtocoloDao.Properties.DataExpedicao.eq(cDate));



        List<Regiao> setores = querySetores.distinct()
                .orderAsc(RegiaoDao.Properties.Descricao)
                .list();


        //Log.d("Limpando","Limpeza");
        dataAdapter.getData().clear();
        //Log.d("Limpado","Limpado");

        dataAdapter.getData().addAll(setores);
        //Log.d("Adicionado","Recarregou");

        dataAdapter.notifyDataSetChanged();
        //Log.d("Notificacao","Notificou");

        // get new modified random data
        // update data in our adapter
        //adapter.getData().clear();
        //adapter.getData().addAll(protocolos);
        // fire the event
        //adapter.notifyDataSetChanged();
    }

    public void getProtocolos() {

        if (!verificaConexao())
            return;

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Date d = myapp.getDate();
        Date ddata_expedicao = d;
        //new Date(d.getTime() - (1000*60*60*8));

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formato.applyPattern("yyyy-MM-dd");
        String data_expedicao = formato.format(ddata_expedicao);
        String codigo_acesso = String.valueOf(myapp.getUsuario().getCodigoAcesso());

        String url = "http://api.softlog.eti.br/api/softlog/protocolo/" + codigo_acesso +
                "/" + data_expedicao + "/0";
        //url = "http://api.softlog.eti.br/api/softlog/protocolo/53/2018-02-01/0";

        //Log.d("Url", url);

        //Registro do usuario e criacao do banco de dados
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonExtractProtocolo.extract(response);
                        reloadAllData(null);
                        progressBar.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                //Log.d("ERRO", error.toString());
            }
        });
        AppSingleton.getInstance(myapp.getApplicationContext()).addToRequestQueue(stringRequest, "Protocolos");
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


    private class RegiaoClickListener implements TableDataClickListener<Regiao> {
        @Override
        public void onDataClicked(int rowIndex, Regiao setor) {

            //Log.d("Linha ",String.valueOf(rowIndex));


            // cria a intent
            Intent intent = new Intent(getApplicationContext(), ProtocoloListActivity.class);
            // seta o parametro do medico a exibir os dados
            intent.putExtra("id_setor", setor.getId());

            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();

        }
    }


    private static class RegiaoLongClickListener implements
            TableDataLongClickListener<Regiao> {
        @Override
        public void onDataLongClicked(int rowIndex, Regiao regiao) {
//            String clickedFlightString = "Long clicked flight with number " +
//                    protocolo.getNumber();
//            Toast.makeText(getContext(), clickedFlightString, Toast.LENGTH_SHORT).show();
        }
    }
}
