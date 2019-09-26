package br.eti.softlog.sconferencia;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ParseException;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.eti.softlog.model.DaoMaster;
import br.eti.softlog.model.DaoSession;
import br.eti.softlog.model.DatabaseUpgradeHelper;
import br.eti.softlog.model.Usuario;
import br.eti.softlog.model.UsuarioDao;
import br.eti.softlog.utils.Util;


/**
 * Created by Paulo Sergio Alves on 2018/01/31.
 */

public class ConferenceApp extends Application{
    private static ConferenceApp singleton;

    private DaoSession mDaoSession;
    DatabaseUpgradeHelper helper;
    DaoMaster.DevOpenHelper helper2;
    private Usuario usuario;
    private boolean status;
    private int CodigoAcesso;
    private Date data_corrente;
    private int tipoStatus;
    private Util util;

    public ConferenceApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Prefs class
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


        singleton = this;
        tipoStatus = 1;

        util = new Util();


        data_corrente = getDate();




        //Recupera as configuracoes em SharedPreferences
        String db = this.getConfigDb();
        String login = this.getConfigLogin();
        boolean status = this.getConfigStatus();

        if (status){
            this.setBD(db);
            Usuario usuario = this.mDaoSession.getUsuarioDao().queryBuilder()
                    .where(UsuarioDao.Properties.Login.eq(login)).unique();

            if (!(usuario == null)){
                this.setUsuario(usuario);
            }
        } else {
            Log.d("Status", "Nenhum Usuario esta logado");
            this.setStatus(false);
        }

        //mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "conference.db").getWritableDb()).newSession();
    }

    public int getTipoStatus(){
        return tipoStatus;
    }

    public void setTipoStatus(int tipoStatus){
        this.tipoStatus = tipoStatus;
    }
    public Date getDate(){
        String cDataCorrente = Prefs.getString("data_corrente",util.getDateFormatYMD(new Date()));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            data_corrente = format.parse(cDataCorrente);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return data_corrente;
    }

    public void setDate(Date p_data_corrente){
        Prefs.putString("data_corrente",util.getDateFormatYMD(p_data_corrente));
        //data_corrente = p_data_corrente;
    }

    public void setBD(String nome_bd){
        helper = new DatabaseUpgradeHelper(getApplicationContext(),nome_bd);
        SQLiteDatabase db = helper.getWritableDatabase();
        //helper2 = new DaoMaster.DevOpenHelper(getApplicationContext(),nome_bd,null);
        //SQLiteDatabase db = helper2.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void setUsuario(Usuario p_usuario) {
        this.usuario = p_usuario;
        this.status = true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void logout() {
        SharedPreferences pref;
        pref = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        this.status = false;
        this.mDaoSession.clear();
        this.usuario = null;
        Prefs.clear();
    }


    public void setConfigDb(String nome_db) {
        //Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
        SharedPreferences pref;
        pref = getSharedPreferences("info", MODE_PRIVATE);
        //Using putXXX - with XXX is type data you want to write like: putString, putInt...   from      Editor object
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("banco_dados",nome_db);

        //finally, when you are done saving the values, call the commit() method.
        editor.commit();
    }

    public String getConfigDb() {
        //get SharedPreferences from getSharedPreferences("name_file", MODE_PRIVATE)
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        String db = shared.getString("banco_dados","");
        //Log.d("Banco Dados",db);
        return db;
    }

    public void setConfigLogin(String login) {
        //Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
        SharedPreferences pref;
        pref = getSharedPreferences("info", MODE_PRIVATE);
        //Using putXXX - with XXX is type data you want to write like: putString, putInt...   from      Editor object
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("login",login);

        //finally, when you are done saving the values, call the commit() method.
        editor.commit();
    }

    public String getConfigLogin() {
        //get SharedPreferences from getSharedPreferences("name_file", MODE_PRIVATE)
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        String login = shared.getString("login","");
        //Log.d("Login",login);
        return login;
    }

    public void setConfigStatus(boolean status) {
        //Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
        SharedPreferences pref;
        pref = getSharedPreferences("info", MODE_PRIVATE);
        //Using putXXX - with XXX is type data you want to write like: putString, putInt...   from      Editor object
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("status",status);

        //finally, when you are done saving the values, call the commit() method.
        editor.commit();
    }

    public boolean getConfigStatus() {
        //get SharedPreferences from getSharedPreferences("name_file", MODE_PRIVATE)
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        boolean status = shared.getBoolean("status",false);
        return status;
    }


    public void setCodigoConferente(String codigoConferente) {
        //Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
        SharedPreferences pref;
        pref = getSharedPreferences("info", MODE_PRIVATE);
        //Using putXXX - with XXX is type data you want to write like: putString, putInt...   from      Editor object
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("codigo_conferente",codigoConferente);

        //finally, when you are done saving the values, call the commit() method.
        editor.commit();
    }

    public String getCodigoConferente() {
        //get SharedPreferences from getSharedPreferences("name_file", MODE_PRIVATE)
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        //Using getXXX- with XX is type date you wrote to file "name_file"
        String codigo_conferente = shared.getString("codigo_conferente","");
        return codigo_conferente;
    }

}