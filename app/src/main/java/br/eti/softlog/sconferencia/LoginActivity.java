package br.eti.softlog.sconferencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.eti.softlog.model.Usuario;
import br.eti.softlog.model.UsuarioDao;
import br.eti.softlog.utils.AppSingleton;

public class LoginActivity extends AppCompatActivity {

    EditText editCodigoAcesso;
    EditText editUsuario;
    EditText editSenha;
    Button btnLogin;
    ConferenceApp myapp;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editCodigoAcesso = (EditText) findViewById(R.id.editCodigoAcesso);
        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editSenha = (EditText) findViewById(R.id.editSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        myapp = (ConferenceApp)getApplicationContext();


        //Validacao do Usuario
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCodigoAcesso.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            R.string.valid_codigo_acesso,
                            Toast.LENGTH_LONG).show();
                } else if (editUsuario.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            R.string.valid_usuario,
                            Toast.LENGTH_LONG).show();
                } else if (editSenha.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            R.string.valid_senha,
                            Toast.LENGTH_LONG).show();
                } else {

                    final String lc_usuario;
                    final String lc_senha;
                    final String lc_codigo_acesso;

                    lc_usuario = editUsuario.getText().toString();
                    lc_senha = editSenha.getText().toString();
                    lc_codigo_acesso = editCodigoAcesso.getText().toString();

                    final String nome_bd = lc_usuario + "_" + lc_codigo_acesso + ".db";

                    //Verifica se tem banco de dados para o usuario e codigo de acesso
                    boolean acessa_api = true;
                    if (myapp.doesDatabaseExist(getApplicationContext(), nome_bd)) {
                        myapp.setBD(nome_bd);
                        final Manager manager = new Manager(myapp);
                        if (manager.hasUsuario()){

                            acessa_api = false;

                            Usuario usuario = manager.findUsuarioByLogin(lc_usuario);
                            if (usuario == null){
                                Toast.makeText(getApplicationContext(),
                                        R.string.usuario_inexistente,
                                        Toast.LENGTH_LONG).show();
                            } else if (!usuario.validUsuario(lc_senha)){
                                Toast.makeText(getApplicationContext(),
                                        R.string.senha_invalida,
                                        Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                //Configuração dos objetos globais
                                myapp.setUsuario(usuario);
                                myapp.setConfigLogin(lc_usuario);
                                myapp.setConfigDb(nome_bd);
                                myapp.setConfigStatus(true);



                                Intent intent = new Intent(LoginActivity.this,
                                        MainActivity.class);

                                startActivity(intent);

                                finish();
                            }
                        }
                    }

                    if (acessa_api) {
                        //Se nao tiver, acessa api para verificar se existe usuario registrado
                        // Request a string response from the provided URL.
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                        final String url = "http://api.softlog.eti.br/api/softlog/usuario/" +
                                 lc_codigo_acesso + "/" + lc_usuario;

                        //Registro do usuario e criacao do banco de dados
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        //Log.d("Log",response.toString());
                                        try {
                                            JSONObject jObj = new JSONObject(response);

                                            Long id_usuario = jObj.getLong("id_usuario");
                                            String login = jObj.getString("login_name");
                                            String senha = jObj.getString("senha");
                                            String nome = jObj.getString("nome_usuario");
                                            String email = jObj.getString("email");

                                            //Log.d("Senha Informada",lc_senha.trim());
                                            //Log.d("Senha",senha.trim());

                                            if (!senha.trim().equals(lc_senha.trim())) {
                                                Toast.makeText(getApplicationContext(),
                                                        R.string.senha_invalida,
                                                               Toast.LENGTH_LONG).show();
                                                return;
                                            }

                                            myapp.setBD(nome_bd);

                                            final Manager manager = new Manager(myapp);

                                            int ln_codigo_acesso = Integer.parseInt(lc_codigo_acesso);

                                            //Insere na tabela de usuarios do aplicativo.
                                            Usuario usuario = new Usuario(id_usuario,nome,login,senha,
                                                    email,null,null, ln_codigo_acesso);

                                            myapp.getDaoSession().getUsuarioDao()
                                                    .insert(usuario);

                                            //Insere na tabela de usuarios do sistema
                                            manager.addUsuarioSistema(id_usuario,nome,email, senha);

                                            //Seta dados globais na application
                                            myapp.setUsuario(usuario);
                                            myapp.setConfigLogin(lc_usuario);
                                            myapp.setConfigDb(nome_bd);
                                            myapp.setConfigStatus(true);

                                            Intent intent = new Intent(LoginActivity.this,
                                                    MainActivity.class);

                                            startActivity(intent);

                                            finish();


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //Log.d("ERRO",error.toString());
                                if (error.networkResponse.statusCode==404){
                                    Toast.makeText(getApplicationContext(),
                                            R.string.usuario_inexistente,
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            R.string.erro_acesso,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,"Login");
                        //Log.d("Log","Processo Concluido!");
                    }
                }
            }
        });
    }
}
