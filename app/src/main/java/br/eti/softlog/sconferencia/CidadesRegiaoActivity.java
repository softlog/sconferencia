package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.SearchView;

import com.sortabletableview.recyclerview.TableDataColumnAdapterDelegator;
import com.sortabletableview.recyclerview.TableView;
import com.sortabletableview.recyclerview.listeners.TableDataClickListener;
import com.sortabletableview.recyclerview.listeners.TableDataLongClickListener;
import com.sortabletableview.recyclerview.model.TableColumnWeightModel;
import com.sortabletableview.recyclerview.toolkit.FilterHelper;
import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;
import com.sortabletableview.recyclerview.toolkit.SimpleTableHeaderAdapter;
import com.sortabletableview.recyclerview.toolkit.TableDataRowBackgroundProviders;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


import br.eti.softlog.ColumnAdapterCidades.CidadeStringValuesExtrators;

import br.eti.softlog.model.Cidades;
import br.eti.softlog.model.CidadesDao;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.RegiaoCidades;
import br.eti.softlog.model.RegiaoCidadesDao;
import br.eti.softlog.utils.Util;
import butterknife.ButterKnife;

public class CidadesRegiaoActivity extends AppCompatActivity {

    ConferenceApp app;
    Manager manager;
    Util util;


    TableView<Cidades> tableView;
    FilterHelper<Cidades> filterHelper;
    TableDataColumnAdapterDelegator<Cidades> dataAdapter;

    List<Cidades> cidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cidades_regiao);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Cidades");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (ConferenceApp) getApplication();
        manager = new Manager(app);
        util = new Util();

        cidades = app.getDaoSession().getCidadesDao().queryBuilder()
                .orderAsc(CidadesDao.Properties.NomeCidade).list();




        tableView = findViewById(R.id.tableView);

        // set up header adapter
        final SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getApplicationContext(),
                "Cidade", "UF", "Setor", "Código");
        headerAdapter.setTextSize(12);
        headerAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        tableView.setHeaderAdapter(headerAdapter);


        dataAdapter = new TableDataColumnAdapterDelegator<>(getApplicationContext(), cidades);

        int sizeFont = 12;
        int i;
        i = -1;


        i++;
        SimpleTableDataColumnAdapter colunaNumero = new SimpleTableDataColumnAdapter<>(CidadeStringValuesExtrators.forNomeCidade());
        colunaNumero.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaNumero);

        i++;
        SimpleTableDataColumnAdapter colunaUf = new SimpleTableDataColumnAdapter<>(CidadeStringValuesExtrators.forUf());
        colunaUf.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaUf);

        i++;
        SimpleTableDataColumnAdapter colunaRegiao = new SimpleTableDataColumnAdapter<>(CidadeStringValuesExtrators.forRegiao());
        colunaRegiao.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaRegiao);


        i++;
        SimpleTableDataColumnAdapter colunaCodigo = new SimpleTableDataColumnAdapter<>(CidadeStringValuesExtrators.forCodigo());
        colunaCodigo.setTextSize(16);
        dataAdapter.setColumnAdapter(i, colunaCodigo);


        final int colorOddRows = ContextCompat.getColor(getApplicationContext(), R.color.colorOddRows);
        final int colorEvenRows = ContextCompat.getColor(getApplicationContext(), R.color.colorEvenRows);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));

        tableView.setDataAdapter(dataAdapter);
        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(7);

        tableColumnModel.setColumnWeight(0, 3);
        tableColumnModel.setColumnWeight(1, 1);
        tableColumnModel.setColumnWeight(2, 3);
        tableColumnModel.setColumnWeight(3, 1);
        tableView.setColumnModel(tableColumnModel);

        final HorizontalScrollView horizontalScrollView = findViewById(R.id.scroll_view);
        tableView.registerHorizontalScrollView(horizontalScrollView);

        tableView.addDataClickListener(new CidadesRegiaoActivity.TableClickListener());
        tableView.addDataLongClickListener(new CidadesRegiaoActivity.TableLongClickListener());

        filterHelper = new FilterHelper<>(tableView);

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
        finish();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notas_fiscais, menu);
        final MenuItem item = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String query) {
                // ******************** Interesting Code Section **********************************************************
                filterHelper.setFilter(new CidadesRegiaoActivity.TableFilter(query));
                // ******************** Interesting Code Section **********************************************************
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // ******************** Interesting Code Section **********************************************************
                filterHelper.clearFilter();
                // ******************** Interesting Code Section **********************************************************
                return false;
            }
        });
        return true;
    }

        //qryRegiaoCidades.orderAsc()

    private class TableClickListener implements TableDataClickListener<Cidades> {
        @Override
        public void onDataClicked(int rowIndex, Cidades cidade) {

            //Log.d("Linha ",String.valueOf(rowIndex));
            Intent intent = new Intent(getApplicationContext(), ProtocoloListActivity.class);
            intent.putExtra("id_setor", cidade.getRegiaoCidades().get(0).getIdRegiao());
            ////
            ////            // seta o parametro do medico a exibir os dados
            ////            intent.putExtra("id_nota_fiscal", cidade.getId());
            ////
            ////            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();
            // cria a intent
//

        }
    }


    private static class TableLongClickListener implements
            TableDataLongClickListener<Cidades> {
        @Override
        public void onDataLongClicked(int rowIndex, Cidades cidade) {
//            String clickedFlightString = "Long clicked flight with number " +
//                    protocolo.getNumber();
//            Toast.makeText(getContext(), clickedFlightString, Toast.LENGTH_SHORT).show();
        }
    }

    final class TableFilter implements FilterHelper.Filter<Cidades> {

        private final String query;

        TableFilter(final String query) {
            this.query = query.toLowerCase();
        }

        @Override
        public boolean apply(Cidades data) {

            if (data.getNomeCidade().toLowerCase().contains(query))
                return true;

            try{
                if (data.getRegiaoCidades().get(0).getRegiao().getDescricao().toLowerCase().contains(query))
                    return true;
            } catch (Exception e){

            }


            if (data.getUf().toLowerCase().contains(query))
                return true;

            return false;
        }

    }



}
