package br.eti.softlog.utils;

/**
 * Created by Paulo Sergio Alves on 2018/01/25.
 */

public class Login {
    private static final String USUARIO = "THECLUB";
    private static final String SENHA = "123456";

    private String Usuario;
    private String Senha;

    public Login()
    {

    }

    public String getUsuario()
    {
        return Usuario;
    }

    public void setUsuario(String usuario)
    {
        Usuario = usuario;
    }

    public String getSenha()
    {
        return Senha;
    }

    public void setSenha(String senha)
    {
        Senha = senha;
    }


    public boolean ValidarUsuario()
    {
        if (Usuario.equals(""))
        {
            return false;
        }
        else if (Senha.equals(""))
        {
            return false;
        }
        else if (!Usuario.equals(USUARIO) || !Senha.equals(SENHA))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean getAutenticarUsuarioApi(){
        return true;
    }

}
