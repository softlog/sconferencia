package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "veiculos")
public class Veiculos {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "placa_veiculo")
    String placa_veiculo;

    @Property(nameInDb = "descricao")
    String descricao;


    @Generated(hash = 1389819297)
    public Veiculos(Long id, String placa_veiculo, String descricao) {
        this.id = id;
        this.placa_veiculo = placa_veiculo;
        this.descricao = descricao;
    }

    @Generated(hash = 1129863896)
    public Veiculos() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca_veiculo() {
        return this.placa_veiculo;
    }

    public void setPlaca_veiculo(String placa_veiculo) {
        this.placa_veiculo = placa_veiculo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString(){
        String alias;
        if (getDescricao().length()>15)
            alias =this.getPlaca_veiculo().substring(0,3) + "-" +
                    this.getPlaca_veiculo().substring(3,7)
                + " " + getDescricao().substring(0,15) + "...";
        else
            alias =this.getPlaca_veiculo().substring(0,3) + "-" +
                    this.getPlaca_veiculo().substring(3,7)
                    + " " + getDescricao();

        return alias;
    }

}
