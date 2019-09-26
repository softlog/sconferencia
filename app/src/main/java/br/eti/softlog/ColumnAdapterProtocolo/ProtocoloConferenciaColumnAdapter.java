package br.eti.softlog.ColumnAdapterProtocolo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sortabletableview.recyclerview.TableDataColumnAdapterDelegator;

import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.sconferencia.R;


public final class ProtocoloConferenciaColumnAdapter extends
        TableDataColumnAdapterDelegator
        .TableDataColumnAdapter<ProtocoloSetor,
                ProtocoloConferenciaColumnAdapter.ProtocoloConferenciaViewHolder> {



    public ProtocoloConferenciaColumnAdapter() {

    }

    @Override
    public ProtocoloConferenciaViewHolder onCreateColumnCellViewHolder(ViewGroup parent, int viewType) {

        final View view = getLayoutInflater().inflate(R.layout.tablecell_conferencia, parent, false);
        return new ProtocoloConferenciaViewHolder(view);

    }

    @Override
    public void onBindColumnCellViewHolder(final ProtocoloConferenciaViewHolder protocoloConferenciaViewHolder, final int columnIndex) {

        try{
            final ProtocoloSetor protocolo = getRowData(columnIndex);
            if (protocolo.getProtocolo().getStatus()){
                if (protocolo.getQtdConferencia() == protocolo.getQtdVolumes())
                    protocoloConferenciaViewHolder.setConferencia(protocolo.getQtdConferencia(),1);
                else
                    protocoloConferenciaViewHolder.setConferencia(protocolo.getQtdConferencia(),3);
            }  else {
                protocoloConferenciaViewHolder.setConferencia(protocolo.getQtdConferencia(),2);
            }
        } catch (Exception e){
            return ;
        }



        //final ProtocoloSetor.Status flightStatus = getRowData(columnIndex).getStatus();
        //flightStatusViewHolder.setFlightStatus(flightStatus);
    }

    static final class ProtocoloConferenciaViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtConferencia;
        private int qtdVolumes;
        private int qtdConferencia;

        private ProtocoloConferenciaViewHolder(final View itemView) {
            super(itemView);
            txtConferencia = itemView.findViewById(R.id.flight_status_view);
        }

        private void setConferencia(int valor, int status) {
            txtConferencia.setText("");
            txtConferencia.setText(String.valueOf(valor));
            switch ( status) {
                case 1:
                    txtConferencia.setBackgroundResource(R.drawable.circle_green);
                    txtConferencia.setTextColor(Color.WHITE);
                    break;
                case 2:
                    txtConferencia.setBackgroundResource(R.drawable.circle_yellow);
                    txtConferencia.setTextColor(Color.BLACK);
                    break;
                case 3:
                    txtConferencia.setBackgroundResource(R.drawable.circle_red);
                    txtConferencia.setTextColor(Color.WHITE);
                    break;
            }
            return ;
        }

    }

}