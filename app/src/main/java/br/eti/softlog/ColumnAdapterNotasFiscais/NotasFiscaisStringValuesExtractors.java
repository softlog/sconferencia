package br.eti.softlog.ColumnAdapterNotasFiscais;

import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.ProtocoloSetor;

public class NotasFiscaisStringValuesExtractors {
    private static final NumberFormat PROTOCOLO_NUMBER_FORMATTER = new DecimalFormat("0000000");
    private static final DateFormat FORMATTER = new SimpleDateFormat("HH:mm");

    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forSerie() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return notasFiscais.getSerie();
                //return PROTOCOLO_NUMBER_FORMATTER.format(protocolo.getProtocoloId());
            }
        };
    }
    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forNumero() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue (NotasFiscais notasFiscais){
                return notasFiscais.getNumero();
            }
        } ;
    }
    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forCep() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return notasFiscais.getDestinatario().getCep();
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forRemetente() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return notasFiscais.getRemetente().getNome();
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forDestinatario() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return notasFiscais.getDestinatario().getNome();
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forCidade() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return notasFiscais.getDestinatario().getCidade().getNomeCidade();

            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forUf() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return notasFiscais.getDestinatario().getCidade().getUf();

            }
        };
    }


    public static SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais> forEspaco() {
        return new SimpleTableDataColumnAdapter.StringValueExtractor<NotasFiscais>() {
            @Override
            public String getStringValue(NotasFiscais notasFiscais) {
                return "#";

            }
        };
    }
}



