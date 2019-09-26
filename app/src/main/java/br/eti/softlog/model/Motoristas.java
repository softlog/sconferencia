package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "motoristas")
public class Motoristas {

    @Id(autoincrement = false)
    Long id;

    @Property(nameInDb = "nome")
    String nome;

    @Property(nameInDb = "cnpj_cpf")
    String cnpjCpf;

    @Property(nameInDb = "endereco")
    String endereco;

    @Property(nameInDb = "numero")
    String numero;

    @Property(nameInDb = "bairro")
    String bairro;

    @Property(nameInDb = "id_cidade")
    Long idCidade;

    @Property(nameInDb = "cep")
    String cep;

    @Property(nameInDb = "telefone")
    String telefone;

    @Property(nameInDb = "latitude")
    String latitude;

    @Property(nameInDb = "longitude")
    String longitude;

    @Generated(hash = 1450669998)
    public Motoristas(Long id, String nome, String cnpjCpf, String endereco,
            String numero, String bairro, Long idCidade, String cep,
            String telefone, String latitude, String longitude) {
        this.id = id;
        this.nome = nome;
        this.cnpjCpf = cnpjCpf;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.idCidade = idCidade;
        this.cep = cep;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Generated(hash = 1055865038)
    public Motoristas() {
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

    public String getCnpjCpf() {
        return this.cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Long getIdCidade() {
        return this.idCidade;
    }

    public void setIdCidade(Long idCidade) {
        this.idCidade = idCidade;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
