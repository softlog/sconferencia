package br.eti.softlog.ColumnAdapterProtocolo;


import com.sortabletableview.recyclerview.toolkit.SimpleTableDataAdapter;
import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.eti.softlog.model.ProtocoloSetor;

public class ProtocoloSetorStringValueExtractors {

    private static final NumberFormat PROTOCOLO_NUMBER_FORMATTER = new DecimalFormat("0000000");
    private static final DateFormat FORMATTER = new SimpleDateFormat("HH:mm");

    private ProtocoloSetorStringValueExtractors() {

    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor> forProtocoloNumero() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor>() {
            @Override
            public String getStringValue(ProtocoloSetor protocolo) {
                return PROTOCOLO_NUMBER_FORMATTER.format(protocolo.getProtocoloId());
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor> forProtocoloQtdNF() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor>() {
            @Override
            public String getStringValue(ProtocoloSetor protocolo) {
                return String.valueOf(protocolo.getQtdNotas());
            }
        };

    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor> forProtocoloVolumes() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor>() {
            @Override
            public String getStringValue(ProtocoloSetor protocolo) {
                return String.valueOf(protocolo.getQtdVolumes());
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor> forProtocoloConferidos() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor>() {
            @Override
            public String getStringValue(ProtocoloSetor protocolo) {
                return String.valueOf(protocolo.getQtdConferencia());
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor> forProtocoloRegiao() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<ProtocoloSetor>() {
            @Override
            public String getStringValue(ProtocoloSetor protocolo) {
                return protocolo.getRegiao().getDescricao().trim();
            }
        };
    }




}
