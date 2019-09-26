package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "ocorrencias")
public class Ocorrencias {

    @Id()
    Long id;

    @Property(nameInDb = "ocorrencia")
    String ocorrencia;

    @Property(nameInDb = "ativo")
    Boolean ativo;

    @Generated(hash = 2044360960)
    public Ocorrencias(Long id, String ocorrencia, Boolean ativo) {
        this.id = id;
        this.ocorrencia = ocorrencia;
        this.ativo = ativo;
    }

    @Generated(hash = 25739883)
    public Ocorrencias() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOcorrencia() {
        return this.ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


    @Override
    public String toString() {
        return String.format("%03d",this.getId()) + "-" + this.getOcorrencia();
    }
}
