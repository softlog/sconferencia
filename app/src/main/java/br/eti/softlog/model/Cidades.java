package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Generated;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "cidades")
public class Cidades{

    @Id(autoincrement = false)
    Long id;

    @Property(nameInDb = "nome_cidade")
    String nomeCidade;

    @Property(nameInDb = "uf")
    String uf;

    @Property(nameInDb = "cod_ibge")
    String codIbge;

    @Property(nameInDb = "latitude")
    Double latitude;

    @Property(nameInDb = "longitude")
    Double longitude;

    @Property(nameInDb = "id_regiao")
    Long idRegiao;

    @ToMany(referencedJoinProperty = "idCidade")
    List<RegiaoCidades> regiaoCidades;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 17103412)
    private transient CidadesDao myDao;


    @Generated(hash = 209728495)
    public Cidades(Long id, String nomeCidade, String uf, String codIbge,
            Double latitude, Double longitude, Long idRegiao) {
        this.id = id;
        this.nomeCidade = nomeCidade;
        this.uf = uf;
        this.codIbge = codIbge;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idRegiao = idRegiao;
    }

    @Generated(hash = 1634339622)
    public Cidades() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return this.nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCodIbge() {
        return this.codIbge;
    }

    public void setCodIbge(String codIbge) {
        this.codIbge = codIbge;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getIdRegiao() {
        return this.idRegiao;
    }

    public void setIdRegiao(Long idRegiao) {
        this.idRegiao = idRegiao;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 514818725)
    public List<RegiaoCidades> getRegiaoCidades() {
        if (regiaoCidades == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegiaoCidadesDao targetDao = daoSession.getRegiaoCidadesDao();
            List<RegiaoCidades> regiaoCidadesNew = targetDao
                    ._queryCidades_RegiaoCidades(id);
            synchronized (this) {
                if (regiaoCidades == null) {
                    regiaoCidades = regiaoCidadesNew;
                }
            }
        }
        return regiaoCidades;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 821258158)
    public synchronized void resetRegiaoCidades() {
        regiaoCidades = null;
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
    @Generated(hash = 1900524822)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCidadesDao() : null;
    }


}
