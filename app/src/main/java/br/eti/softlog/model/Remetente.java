package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Paulo Sérgio Alves on 2018/04/02.
 */

@Entity(nameInDb = "remetentes")
public class Remetente {

    @Id()
    private Long id;

    @Property(nameInDb = "remetente_nome")
    private String remetenteNome;

    @Property(nameInDb = "remetente_cnpj")
    private String remetenteCnpj;

    @Generated(hash = 1535504945)
    public Remetente(Long id, String remetenteNome, String remetenteCnpj) {
        this.id = id;
        this.remetenteNome = remetenteNome;
        this.remetenteCnpj = remetenteCnpj;
    }

    @Generated(hash = 1485476802)
    public Remetente() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemetenteNome() {
        return this.remetenteNome;
    }

    public void setRemetenteNome(String remetenteNome) {
        this.remetenteNome = remetenteNome;
    }

    public String getRemetenteCnpj() {
        return this.remetenteCnpj;
    }

    public void setRemetenteCnpj(String remetenteCnpj) {
        this.remetenteCnpj = remetenteCnpj;
    }


}
