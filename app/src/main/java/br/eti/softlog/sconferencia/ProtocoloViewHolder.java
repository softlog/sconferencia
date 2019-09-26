package br.eti.softlog.sconferencia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtocoloViewHolder extends RecyclerView.ViewHolder{

    TextView numeroProtocolo;

    public ProtocoloViewHolder(View itemView) {
        super(itemView);
        numeroProtocolo = itemView.findViewById(R.id.item_lista_numero_protocolo);
    }

}
