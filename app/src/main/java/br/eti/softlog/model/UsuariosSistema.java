package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Paulo Sergio Alves on 2018/03/02.
 */
@Entity(nameInDb = "usuarios_sistema")
public class UsuariosSistema {

    @Id()
    private Long id;

    @Property(nameInDb = "nome")
    private String nome;

    @Property(nameInDb = "email")
    private String email;

    @Property(nameInDb = "senha")
    private String senha;

    @Generated(hash = 1144681427)
    public UsuariosSistema(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Generated(hash = 311720681)
    public UsuariosSistema() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
