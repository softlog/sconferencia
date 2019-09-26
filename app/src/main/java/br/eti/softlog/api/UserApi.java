package br.eti.softlog.api;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.eti.softlog.sconferencia.ConferenceApp;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Paulo Sergio Alves on 2018/01/31.
 */

public class UserApi {

    private static String nome;
    private static String login;
    private static String senha;
    private static String email;
    private static int code_status;

    public static final String API_URL = "http://api.softlog.eti.br";

    public static class User {
        public final String login;
        public final String codigo_acesso;

        public User(String login, String codigo_acesso) {
            this.login = login;
            this.codigo_acesso = codigo_acesso;
        }
    }

    public interface ApiSoftlog {
        @GET("/api/softlog/usuario/{codigo_acesso}/{login}")
        Call<List<User>> user(
                @Path("codigo_acesso") String codigo_acesso,
                @Path("login") String login);
    }

    public static int getUsuarioByLogin(String login_id, String codigo_acesso) {

        nome =  "Administrador Sistema";
        login =  "administrador";
        senha = "gyserver";
        email =  "suporte@softlog.eti.br";
        code_status = 200;

        return code_status;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static int getCode_status() {
        return code_status;
    }

    public boolean checkSenha(String senha_informada){
        if (senha==senha_informada)
            return true;
        else
            return false;
    }
}
