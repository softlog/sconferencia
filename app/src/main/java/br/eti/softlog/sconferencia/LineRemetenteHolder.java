package br.eti.softlog.sconferencia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import br.eti.softlog.interfaces.RecyclerViewClickListener;
import br.eti.softlog.model.ProtocoloRemetente;

/**
 * Created by Paulo Sérgio Alves on 2018/04/04.
 */

public class LineRemetenteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_remetente;
    public TextView txt_qtd_notas;
    public TextView txt_qtd_volumes;
    public TextView txt_conferidos;
    public TextView txtStatus;
    public ImageButton btnOk;
    public ImageButton btnPlus;
    public ImageButton btnMinus;
    public ImageButton btnNovaOcorrencia;
    public ImageButton btnNovaOcorrenciaNFe;
    public AppCompatButton btnOcorrencias;
    public RecyclerViewClickListener mListener;


    public LineRemetenteHolder(View itemView,  RecyclerViewClickListener listener){

        super(itemView);

        txt_remetente = (TextView) itemView.findViewById(R.id.txt_remetente);
        //txt_qtd_notas = (TextView) itemView.findViewById(R.id.txt_qtd_notas);
        txt_qtd_volumes = (TextView) itemView.findViewById(R.id.txt_qtd_volumes);
        txt_conferidos = (TextView) itemView.findViewById(R.id.txt_conferidos);
        txtStatus = itemView.findViewById(R.id.txt_status);

        btnOk = (ImageButton) itemView.findViewById(R.id.btnOk);
        btnPlus = (ImageButton) itemView.findViewById(R.id.btnPlus);
        btnMinus = (ImageButton) itemView.findViewById(R.id.btnMinus);
        btnNovaOcorrencia = itemView.findViewById(R.id.btn_nova_ocorrencia);
        btnNovaOcorrenciaNFe = itemView.findViewById(R.id.btn_nova_ocorrencia_by_notas);
        btnOcorrencias = itemView.findViewById(R.id.btn_ocorrencias);


        mListener = listener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view, getAdapterPosition());
    }
}
