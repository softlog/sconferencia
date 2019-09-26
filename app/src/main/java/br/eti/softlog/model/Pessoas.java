package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "pessoas")
public class Pessoas {

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

    @ToOne(joinProperty = "idCidade")
    Cidades cidade;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2048929302)
    private transient PessoasDao myDao;

    @Generated(hash = 869691244)
    private transient Long cidade__resolvedKey;
    
    @Generated(hash = 703469521)
    public Pessoas(Long id, String nome, String cnpjCpf, String endereco,
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

    @Generated(hash = 1054633519)
    public Pessoas() {
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 554775171)
    public Cidades getCidade() {
        Long __key = this.idCidade;
        if (cidade__resolvedKey == null || !cidade__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CidadesDao targetDao = daoSession.getCidadesDao();
            Cidades cidadeNew = targetDao.load(__key);
            synchronized (this) {
                cidade = cidadeNew;
                cidade__resolvedKey = __key;
            }
        }
        return cidade;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 412831111)
    public void setCidade(Cidades cidade) {
        synchronized (this) {
            this.cidade = cidade;
            idCidade = cidade == null ? null : cidade.getId();
            cidade__resolvedKey = idCidade;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 249612730)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPessoasDao() : null;
    }



}
