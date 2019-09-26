package br.eti.softlog.ColumAdapterSetor;

import android.content.Context;

import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;

import java.util.List;

import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.sconferencia.Manager;

public class SetorStringValueExtrators {

    private SetorStringValueExtrators() {

    }


    public static SimpleTableDataColumnAdapter.StringValueExtractor<Regiao> forDescricao() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<Regiao>() {
            @Override
            public String getStringValue(Regiao regiao) {
                return regiao.getDescricao().trim();
            }
        };
    }


    public static SimpleTableDataColumnAdapter.StringValueExtractor<Regiao> forQtdProtocolo(Context context) {

        ConferenceApp myapp = (ConferenceApp)context.getApplicationContext();
        Manager manager = new Manager(myapp);

        return new SimpleTableDataColumnAdapter.StringValueExtractor<Regiao>() {
            @Override
            public String getStringValue(Regiao regiao) {


                List<ProtocoloSetor> protocolos = manager.findProtocoloSetorByDataExpedicao(myapp.getDate(),regiao.getId());

                String lcQtd;
                if (protocolos != null){
                    lcQtd = String.valueOf(protocolos.size());
                } else {
                    lcQtd = "0";
                }
                return lcQtd;
            }
        };
    }

}
