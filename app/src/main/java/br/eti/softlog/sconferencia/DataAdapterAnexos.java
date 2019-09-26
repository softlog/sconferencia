package br.eti.softlog.sconferencia;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.eti.softlog.model.AnexosOcorrencias;

public class DataAdapterAnexos extends RecyclerView.Adapter<DataAdapterAnexos.ViewHolder> {
    ConferenceApp app;
    private List<AnexosOcorrencias> anexosOcorrencias;
    private Context context;
    String PATH = "/data/data/br.eti.softlog.sconferencia/files/";


    public DataAdapterAnexos(Context context, List<AnexosOcorrencias> anexosOcorrencias) {
        this.anexosOcorrencias = anexosOcorrencias;
        this.context = context;
        app = (ConferenceApp) context.getApplicationContext();
    }

    @Override
    public DataAdapterAnexos.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_anexos, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapterAnexos.ViewHolder viewHolder, int i) {

        File f = new File(PATH + anexosOcorrencias.get(i).getConteudoAnexo());
        Picasso.get().load(f).resize(240, 120).into(viewHolder.imgAnexo);

        viewHolder.btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AnexosOcorrencias item = anexosOcorrencias.get(i);
                String cFile = PATH + item.getConteudoAnexo();
                app.getDaoSession().delete(item);
                anexosOcorrencias.remove(i);
                notifyItemRemoved(i);

                File file = new File(cFile);
                file.delete();
            }
        });
    }

    @Override
    public int getItemCount() {
        return anexosOcorrencias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgAnexo;
        FloatingActionButton btnExcluir;
        public ViewHolder(View view) {
            super(view);


            imgAnexo = (ImageView) view.findViewById(R.id.img_anexo);
            btnExcluir = view.findViewById(R.id.btn_excluir);
        }
    }
}