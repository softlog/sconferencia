package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

import com.sortabletableview.recyclerview.TableDataColumnAdapterDelegator;
import com.sortabletableview.recyclerview.TableView;
import com.sortabletableview.recyclerview.listeners.TableDataClickListener;
import com.sortabletableview.recyclerview.listeners.TableDataLongClickListener;
import com.sortabletableview.recyclerview.model.TableColumnWeightModel;
import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;
import com.sortabletableview.recyclerview.toolkit.SimpleTableHeaderAdapter;
import com.sortabletableview.recyclerview.toolkit.TableDataRowBackgroundProviders;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import br.eti.softlog.ColumnAdapterNotasFiscais.NotasFiscaisStringValuesExtractors;
import br.eti.softlog.JsonExtract.JsonExtractNotasFiscais;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.utils.Util;

public class OcorrenciasListActivity extends AppCompatActivity {


    TableView<NotasFiscais> tableView;
    TableDataColumnAdapterDelegator<NotasFiscais> dataAdapter;

    Long idProtocolo;
    Long idRemetente;
    Long idProtocoloSetor;

    Manager manager;
    DataSync dataSync;
    ProtocoloSetor protocolo;
    List<NotasFiscais> notasFiscais;
    Util util;

    ConferenceApp app = (ConferenceApp)getApplication();
    JsonExtractNotasFiscais jsonExtractNotasFiscais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencias_list);

        getSupportActionBar().setTitle("Ocorrências");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (ConferenceApp)getApplication();
        manager = new Manager(app);

        Intent inCall = getIntent();

        idProtocolo = inCall.getLongExtra("protocolo_id",0);
        idRemetente = inCall.getLongExtra("remetente_id",0);


        QueryBuilder query = app.getDaoSession().getNotasFiscaisDao().queryBuilder();

        query.where(NotasFiscaisDao.Properties.IdNfProtocolo.eq(idProtocolo))
             .where(NotasFiscaisDao.Properties.Observacao.isNotNull());

        if (idRemetente > 0)
            query.where(NotasFiscaisDao.Properties.RemetenteId.eq(idRemetente));


        notasFiscais = query.orderAsc(NotasFiscaisDao.Properties.RemetenteId,NotasFiscaisDao.Properties.Numero)
                .list();


        if (notasFiscais.size()> 0)
            idProtocoloSetor = notasFiscais.get(0).getProtocolo().getProtocoloSetores().get(0).getId();
        else
            idProtocoloSetor = Long.valueOf(0);

        tableView = findViewById(R.id.tableView);

        // set up header adapter
        final SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(getApplicationContext(),
                "Série","Número", "CEP", "Remetente", "Destinatário","Cidade", "UF");
        headerAdapter.setTextSize(12);
        headerAdapter.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        tableView.setHeaderAdapter(headerAdapter);

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

        tableView.addDataClickListener(new OcorrenciasListActivity.NotaFiscalClickListener());
        tableView.addDataLongClickListener(new OcorrenciasListActivity.NotaFiscalLongClickListener());

    }

    private class NotaFiscalClickListener implements TableDataClickListener<NotasFiscais> {
        @Override
        public void onDataClicked(int rowIndex, NotasFiscais notaFiscal) {

            //Log.d("Linha ",String.valueOf(rowIndex));
            // cria a intent
            Intent intent = new Intent(getApplicationContext(), OcorrenciaRegistroActivity.class);
            // seta o parametro do medico a exibir os dados
            intent.putExtra("id_nota_fiscal", notaFiscal.getId());
            intent.putExtra("protocolo_id",idProtocolo);
            if (idRemetente>0)
                intent.putExtra("remetente_id",idRemetente);
            else
                intent.putExtra("remetente_id",notaFiscal.getRemetenteId());

            intent.putExtra("operacao","alterar");

            //  chama a Activity que mostra os detalhes
            startActivity(intent);
            finish();

        }
    }


    private static class NotaFiscalLongClickListener implements
            TableDataLongClickListener<NotasFiscais> {
        @Override
        public void onDataLongClicked(int rowIndex, NotasFiscais notaFiscal) {
//            String clickedFlightString = "Long clicked flight with number " +
//                    protocolo.getNumber();
//            Toast.makeText(getContext(), clickedFlightString, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {

        Intent in;
        if (idRemetente>0)
            in = new Intent(getApplicationContext(),RemetenteSetorActivity.class);
        else
            in = new Intent(getApplicationContext(),ProtocoloActivity.class);

            if(idProtocoloSetor>0){
                in.putExtra("id_protocolo_setor",String.valueOf(idProtocoloSetor));
            } else {
                ProtocoloSetor p = app.getDaoSession().getProtocoloSetorDao()
                        .queryBuilder()
                        .where(ProtocoloSetorDao.Properties.ProtocoloId.eq(idProtocolo))
                        .unique();
                idProtocoloSetor = p.getId();
                in.putExtra("id_protocolo_setor",String.valueOf(idProtocoloSetor));
            }


        startActivity(in);
        finish();
        return true;
    }
}
