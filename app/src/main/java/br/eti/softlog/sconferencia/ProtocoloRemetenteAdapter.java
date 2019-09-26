package br.eti.softlog.sconferencia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Locale;

import br.eti.softlog.ColumnAdapterNotasFiscais.NotasFiscaisStringValuesExtractors;
import br.eti.softlog.interfaces.RecyclerViewClickListener;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import br.eti.softlog.model.ProtocoloRemetente;
import br.eti.softlog.utils.AppSingleton;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Paulo Sérgio Alves on 2018/04/04.
 */

public class ProtocoloRemetenteAdapter extends RecyclerView.Adapter<LineRemetenteHolder> {

    private final List<ProtocoloRemetente> mProtocoloRemetentes;

    Context mContext;
    ViewGroup parent;


    private RecyclerViewClickListener mListener;

    public ProtocoloRemetenteAdapter(List<ProtocoloRemetente> protocoloRemetente, RecyclerViewClickListener listener) {
        mProtocoloRemetentes = protocoloRemetente;
        mListener = listener;
    }

    @Override
    public LineRemetenteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        this.parent = parent;

        return new LineRemetenteHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conferencia, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(final LineRemetenteHolder holder, final int position) {

        final ConferenceApp myApp = (ConferenceApp) mContext.getApplicationContext();
        final LineRemetenteHolder mHolder;
        String remetente = mProtocoloRemetentes.get(position).getRemetente().getNome();
        mHolder = holder;

        holder.txt_remetente.setText(remetente);
        //holder.txt_qtd_notas.setText(String.valueOf(mProtocoloRemetentes.get(position).getQtdNotas()));

        holder.txt_qtd_volumes.setText(String
                .valueOf(mProtocoloRemetentes.get(position).getQtdVolumes()));

        holder.txt_conferidos.setText(String
                .valueOf(mProtocoloRemetentes.get(position).getQtdConferencia()));

        if (mProtocoloRemetentes.get(position).getStatus() == 0) {
            holder.btnOcorrencias.setVisibility(View.INVISIBLE);
            holder.btnOcorrencias.setVisibility(View.GONE);
            holder.btnNovaOcorrencia.setVisibility(View.INVISIBLE);
            holder.btnNovaOcorrencia.setVisibility(View.GONE);
            holder.txtStatus.setBackgroundColor(Color.YELLOW);
            holder.btnNovaOcorrenciaNFe.setVisibility(View.INVISIBLE);
            holder.btnNovaOcorrenciaNFe.setVisibility(View.GONE);
        } else {
            if (mProtocoloRemetentes.get(position).getQtdVolumes() !=
                    mProtocoloRemetentes.get(position).getQtdConferencia()) {

                holder.btnOcorrencias.setVisibility(View.VISIBLE);
                holder.btnNovaOcorrencia.setVisibility(View.VISIBLE);
                holder.btnNovaOcorrenciaNFe.setVisibility(View.VISIBLE);
                holder.txtStatus.setBackgroundColor(Color.RED);

            } else {
                holder.btnOcorrencias.setVisibility(View.INVISIBLE);
                holder.btnOcorrencias.setVisibility(View.GONE);
                holder.btnNovaOcorrencia.setVisibility(View.INVISIBLE);
                holder.btnNovaOcorrencia.setVisibility(View.GONE);
                holder.txtStatus.setBackgroundColor(Color.GREEN);
                holder.btnNovaOcorrenciaNFe.setVisibility(View.INVISIBLE);
                holder.btnNovaOcorrenciaNFe.setVisibility(View.GONE);
            }

        }


        if (mProtocoloRemetentes.get(position).getStatus() == 1) {
            holder.btnOk.setImageResource(R.drawable.ic_action_check);
        } else {
            holder.btnOk.setImageResource(R.drawable.ic_action_uncheck);
        }


        holder.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProtocoloRemetente reg = mProtocoloRemetentes.get(position);
                if (reg.getStatus() == 0) {
                    reg.setStatus(1);
                    mHolder.btnOk.setImageResource(R.drawable.ic_action_check);

                    if (reg.getQtdVolumes()!=reg.getQtdConferencia()){
                        mHolder.btnOcorrencias.setVisibility(View.VISIBLE);
                        mHolder.btnNovaOcorrencia.setVisibility(View.VISIBLE);
                        mHolder.btnNovaOcorrenciaNFe.setVisibility(View.VISIBLE);
                        mHolder.txtStatus.setBackgroundColor(Color.RED);
                    } else {
                        mHolder.txtStatus.setBackgroundColor(Color.GREEN);
                    }
                } else {
                    reg.setStatus(0);
                    mHolder.btnOcorrencias.setVisibility(View.INVISIBLE);
                    mHolder.btnOcorrencias.setVisibility(View.GONE);
                    mHolder.btnNovaOcorrencia.setVisibility(View.INVISIBLE);
                    mHolder.btnNovaOcorrencia.setVisibility(View.GONE);
                    mHolder.txtStatus.setBackgroundColor(Color.YELLOW);
                    mHolder.btnOk.setImageResource(R.drawable.ic_action_uncheck);
                }
                myApp.getDaoSession().update(reg);
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProtocoloRemetente reg = mProtocoloRemetentes.get(position);
                if (reg.getStatus() == 1) {
                    return;
                }
                reg.setQtdConferencia(reg.getQtdConferencia() + 1);
                notifyItemChanged(position);
            }
        });


        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProtocoloRemetente reg = mProtocoloRemetentes.get(position);
                if (reg.getStatus() == 1 || reg.getQtdConferencia() == 0) {
                    return;
                }
                reg.setQtdConferencia(reg.getQtdConferencia() - 1);
                notifyItemChanged(position);
            }
        });

        holder.btnOcorrencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(mContext,OcorrenciasListActivity.class);
                Long remetenteId =  mProtocoloRemetentes.get(position).getRemetenteId();
                Long protocoloId =  mProtocoloRemetentes.get(position).getProtocoloSetor()
                        .getProtocoloId();


                List<NotasFiscais> notasFiscais = myApp.getDaoSession().getNotasFiscaisDao()
                        .queryBuilder()
                        .where(NotasFiscaisDao.Properties.IdNfProtocolo.eq(protocoloId))
                        .where(NotasFiscaisDao.Properties.Tem_ocorrencia.eq(true))
                        .list();

                if (notasFiscais.size()>0){
                    in.putExtra("remetente_id",remetenteId);
                    in.putExtra("protocolo_id",protocoloId);
                    in.putExtra("tipo_chamada",0);

                    mContext.startActivity(in);
                }


            }
        });


        holder.btnNovaOcorrenciaNFe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext,NotasFiscaisActivity.class);
                Long remetenteId =  mProtocoloRemetentes.get(position).getRemetenteId();
                Long protocoloId =  mProtocoloRemetentes.get(position).getProtocoloSetor()
                        .getProtocoloId();
                Long setorId = mProtocoloRemetentes.get(position).getProtocoloSetor().getRegiaoId();
                in.putExtra("remetente_id",remetenteId);
                in.putExtra("protocolo_id",protocoloId);
                in.putExtra("setor_id", setorId);
                in.putExtra("tipo_chamada",0);

                mContext.startActivity(in);

            }
        });
        holder.btnNovaOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(mContext,BarCode2Activity.class);
                Long remetenteId =  mProtocoloRemetentes.get(position).getRemetenteId();
                Long protocoloId =  mProtocoloRemetentes.get(position).getProtocoloSetor()
                        .getProtocoloId();

                in.putExtra("remetente_id",remetenteId);
                in.putExtra("protocolo_id",protocoloId);
                in.putExtra("tipo_chamada",0);
                mContext.startActivity(in);
            }
        });




    }

    @Override
    public int getItemCount() {
        if (mProtocoloRemetentes != null)
            return mProtocoloRemetentes.size();
        else
            return 0;
    }


    private void checkOk(int position) {

    }

    private void checkEdit(int position) {

    }

}
