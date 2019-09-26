package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.SearchView;
import android.widget.TextView;

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

import br.eti.softlog.ColumnAdapterNotasFiscais.NotasFiscaisStringValuesExtractors;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloDao;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.utils.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NotasFiscaisActivity extends AppCompatActivity {

    ConferenceApp app;
    Manager manager;
    Util util;

    TableView<NotasFiscais> tableView;
    FilterHelper<NotasFiscais> filterHelper;
    TableDataColumnAdapterDelegator<NotasFiscais> dataAdapter;

    Long protocoloId;
    Long remetenteId;
    Long setorId;
    List<NotasFiscais> notasFiscais;

    @BindView(R.id.txt_protocolo)
    TextView txtProtocolo;

    @BindView(R.id.txt_setor)
    TextView txtSetor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_fiscais);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Lista NFe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (ConferenceApp) getApplication();
        manager = new Manager(app);
        util = new Util();

        Intent inCall = getIntent();

        protocoloId = inCall.getLongExtra("protocolo_id", Long.valueOf(0));
        remetenteId = inCall.getLongExtra("remetente_id", Long.valueOf(0));
        setorId = inCall.getLongExtra("setor_id", Long.valueOf(0));

        notasFiscais = getNotasFiscais(protocoloId, remetenteId, setorId);

        txtProtocolo.setText(String.valueOf(protocoloId));
        if (setorId > 0) {
            Regiao setor = manager.findRegiaoById(setorId);
            txtSetor.setText(setor.getDescricao());
        } else {
            txtSetor.setVisibility(View.INVISIBLE);
            txtSetor.setVisibility(View.GONE);
        }

        tableView = findViewById(R.id.tableView);

        // set up header adapter
        final SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getApplicationContext(),
                "Série", "Número", "CEP", "Remetente", "Destinatário", "Cidade", "UF");
        headerAdapter.setTextSize(12);
        headerAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        tableView.setHeaderAdapter(headerAdapter);


        dataAdapter = new TableDataColumnAdapterDelegator<>(getApplicationContext(), notasFiscais);

        int sizeFont = 12;
        int i;
        i = 0;

        SimpleTableDataColumnAdapter colunaSerie = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forSerie());
        colunaSerie.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaSerie);

        i++;
        SimpleTableDataColumnAdapter colunaNumero = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forNumero());
        colunaNumero.setTextSize(14);
        dataAdapter.setColumnAdapter(i, colunaNumero);

        i++;
        SimpleTableDataColumnAdapter colunaCep = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forCep());
        colunaCep.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaCep);

        i++;
        SimpleTableDataColumnAdapter colunaRemetente = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forRemetente());
        colunaRemetente.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaRemetente);

        i++;
        SimpleTableDataColumnAdapter colunaDestinatario = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forDestinatario());
        colunaDestinatario.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaDestinatario);

        i++;
        SimpleTableDataColumnAdapter colunaCidade = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forCidade());
        colunaCidade.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaCidade);

        i++;
        SimpleTableDataColumnAdapter colunaUF = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forUf());
        colunaUF.setTextSize(sizeFont);
        dataAdapter.setColumnAdapter(i, colunaUF);

        i++;
        SimpleTableDataColumnAdapter colunaEspaco = new SimpleTableDataColumnAdapter<>(NotasFiscaisStringValuesExtractors.forUf());
        colunaUF.setTextSize(16);
        dataAdapter.setColumnAdapter(i, colunaEspaco);

        final int colorOddRows = ContextCompat.getColor(getApplicationContext(), R.color.colorOddRows);
        final int colorEvenRows = ContextCompat.getColor(getApplicationContext(), R.color.colorEvenRows);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));

        tableView.setDataAdapter(dataAdapter);
        final TableColumnWeightModel tableColumnModel;
        tableColumnModel = new TableColumnWeightModel(7);

        tableColumnModel.setColumnWeight(0, 1);
        tableColumnModel.setColumnWeight(1, 2);
        tableColumnModel.setColumnWeight(2, 2);
        tableColumnModel.setColumnWeight(3, 3);
        tableColumnModel.setColumnWeight(4, 3);
        tableColumnModel.setColumnWeight(5, 3);
        tableColumnModel.setColumnWeight(6, 1);
        tableColumnModel.setColumnWeight(7, 1);
        tableView.setColumnModel(tableColumnModel);

        final HorizontalScrollView horizontalScrollView = findViewById(R.id.scroll_view);
        tableView.registerHorizontalScrollView(horizontalScrollView);

        tableView.addDataClickListener(new TableClickListener());
        tableView.addDataLongClickListener(new TableLongClickListener());

        filterHelper = new FilterHelper<>(tableView);
    }

    private List<NotasFiscais> getNotasFiscais(Long protocoloId, Long remetenteId, Long setorId) {

        QueryBuilder query = app.getDaoSession().getNotasFiscaisDao().queryBuilder();

        if (protocoloId > 0)
            query.where(NotasFiscaisDao.Properties.IdNfProtocolo.eq(protocoloId));

        if (remetenteId > 0)
            query.where(NotasFiscaisDao.Properties.RemetenteId.eq(remetenteId));

        if (setorId > 0) {

            Join joinProtocolo = query.join(NotasFiscaisDao.Properties.IdNfProtocolo, Protocolo.class);

            Join joinProtocoloSetor = query.join(joinProtocolo, ProtocoloDao.Properties.Id,
                    ProtocoloSetor.class, ProtocoloSetorDao.Properties.ProtocoloId);

            joinProtocoloSetor.where(ProtocoloSetorDao.Properties.RegiaoId.eq(setorId));

        }

        return query.orderAsc(NotasFiscaisDao.Properties.Numero).list();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
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
                filterHelper.setFilter(new TableFilter(query));
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


    private class TableClickListener implements TableDataClickListener<NotasFiscais> {
        @Override
        public void onDataClicked(int rowIndex, NotasFiscais notaFiscal) {

            //Log.d("Linha ",String.valueOf(rowIndex));
            // cria a intent
            Intent intent = new Intent(getApplicationContext(), OcorrenciaRegistroActivity.class);

            // seta o parametro do medico a exibir os dados
            intent.putExtra("id_nota_fiscal", notaFiscal.getId());

            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();

        }
    }


    private static class TableLongClickListener implements
            TableDataLongClickListener<NotasFiscais> {
        @Override
        public void onDataLongClicked(int rowIndex, NotasFiscais notaFiscal) {
//            String clickedFlightString = "Long clicked flight with number " +
//                    protocolo.getNumber();
//            Toast.makeText(getContext(), clickedFlightString, Toast.LENGTH_SHORT).show();
        }
    }

    final class TableFilter implements FilterHelper.Filter<NotasFiscais> {

        private final String query;

        TableFilter(final String query) {
            this.query = query;
        }

        @Override
        public boolean apply(NotasFiscais data) {

            if (data.getNumero().contains(query))
                return true;

            if (data.getRemetente().getNome().toLowerCase().contains(query))
                return true;

            if (data.getDestinatario().getNome().toLowerCase().contains(query))
                return true;

            return false;
        }
    }


}
