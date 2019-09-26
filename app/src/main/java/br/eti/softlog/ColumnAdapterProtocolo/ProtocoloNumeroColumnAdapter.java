package br.eti.softlog.ColumnAdapterProtocolo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sortabletableview.recyclerview.TableDataColumnAdapterDelegator;

import br.eti.softlog.model.ProtocoloSetor;

public final class ProtocoloNumeroColumnAdapter extends TableDataColumnAdapterDelegator.TableDataColumnAdapter<ProtocoloSetor,
        ProtocoloNumeroColumnAdapter.ProtocoloSetorViewHolder> {

    @Override
    public ProtocoloSetorViewHolder onCreateColumnCellViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindColumnCellViewHolder(ProtocoloSetorViewHolder cellViewHolder, int rowIndex) {

    }

    static final class ProtocoloSetorViewHolder extends RecyclerView.ViewHolder {


        public ProtocoloSetorViewHolder(View itemView) {
            super(itemView);
        }
    }
}
