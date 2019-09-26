package br.eti.softlog.ColumnAdapterCidades;

import com.sortabletableview.recyclerview.toolkit.SimpleTableDataColumnAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import br.eti.softlog.model.Cidades;

public class CidadeStringValuesExtrators {

    private static final NumberFormat NUMBER_FORMATTER = new DecimalFormat("00000");

    public static SimpleTableDataColumnAdapter.StringValueExtractor<Cidades> forNomeCidade() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<Cidades>() {
            @Override
            public String getStringValue(Cidades cidade) {
                return cidade.getNomeCidade();
                //return PROTOCOLO_NUMBER_FORMATTER.format(protocolo.getProtocoloId());
            }
        };
    }


    public static SimpleTableDataColumnAdapter.StringValueExtractor<Cidades> forUf() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<Cidades>() {
            @Override
            public String getStringValue(Cidades cidade) {
                return cidade.getUf();
                //return PROTOCOLO_NUMBER_FORMATTER.format(protocolo.getProtocoloId());
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<Cidades> forCodigo() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<Cidades>() {
            @Override
            public String getStringValue(Cidades cidade) {
                return NUMBER_FORMATTER.format(cidade.getId());
                //return PROTOCOLO_NUMBER_FORMATTER.format(protocolo.getProtocoloId());
            }
        };
    }

    public static SimpleTableDataColumnAdapter.StringValueExtractor<Cidades> forRegiao() {

        return new SimpleTableDataColumnAdapter.StringValueExtractor<Cidades>() {
            @Override
            public String getStringValue(Cidades cidade) {
                try {
                    return cidade.getRegiaoCidades().get(0).getRegiao().getDescricao();
                } catch (Exception e){
                    return "Sem Setor";
                }

                //return PROTOCOLO_NUMBER_FORMATTER.format(protocolo.getProtocoloId());
            }
        };
    }
}
