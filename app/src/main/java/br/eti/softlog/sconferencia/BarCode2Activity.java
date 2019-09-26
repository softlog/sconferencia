package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;


import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import info.androidhive.barcode.BarcodeReader;

/**
 * Created by Paulo Sérgio Alves on 2018/03/28.
 */

public class BarCode2Activity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private BarcodeReader barcodeReader;

    ConferenceApp app;
    Manager manager;
    int tipoChamada;
    Long remetenteId;
    Long protocoloId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode2);

        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);

        Intent inCall = getIntent();

        remetenteId = inCall.getLongExtra("remetente_id",Long.valueOf(0));
        protocoloId = inCall.getLongExtra("protocolo_id",Long.valueOf(0));
        tipoChamada = inCall.getIntExtra("tipo_chamada",0);

        app = (ConferenceApp) getApplicationContext();
        manager = new Manager(app);

        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");

        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         * */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }

    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        barcodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (tipoChamada==0) {
                    String chaveNfe = barcode.displayValue;
                    if (chaveNfe.length()==44){
                        NotasFiscais documento = app.getDaoSession().getNotasFiscaisDao()
                                .queryBuilder()
                                .where(NotasFiscaisDao.Properties.ChaveNfe.eq(chaveNfe))
                                .unique();

                        if (documento != null){
                            //Verifica se a nota fiscal está no protocolo
                            // e pertence ao remetente da ocorrencia
                            if (!documento.getRemetenteId().equals(remetenteId)){
                                new MaterialDialog.Builder(BarCode2Activity.this)
                                        .title("Mensagem")
                                        .content("NFe não pertence ao remetente da ocorrência.")
                                        .positiveText("OK")
                                        .onPositive((dialog, which) -> finish())
                                        .show();
                            }

                            if (!documento.getIdNfProtocolo().equals(protocoloId)){
                                new MaterialDialog.Builder(BarCode2Activity.this)
                                        .title("Mensagem")
                                        .content("NFe não foi encontrada no protoloco: " + String.valueOf(protocoloId))
                                        .positiveText("OK")
                                        .onPositive((dialog, which) -> finish())
                                        .show();

                            }
                            Toast.makeText(getApplicationContext(),"Chave " + chaveNfe,Toast.LENGTH_LONG);
                            Intent i = new Intent(getApplicationContext(),OcorrenciaRegistroActivity.class);
                            i.putExtra("id_nota_fiscal",documento.getId());
                            startActivity(i);
                            finish();
                        } else {

                                new MaterialDialog.Builder(BarCode2Activity.this)
                                        .title("Mensagem")
                                        .content("NFe não encontrada.")
                                        .positiveText("OK")
                                        .onPositive((dialog, which) -> finish())
                                        .show();

                        }

                    }
                }
            }
        });
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        //Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}