package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "regiao_cidades")
public class RegiaoCidades {

    @Id(autoincrement = true)
    Long Id;

    @Property(nameInDb = "id_cidade")
    Long idCidade;

    @Property(nameInDb = "id_regiao")
    Long idRegiao;

    @ToOne(joinProperty = "idRegiao")
    Regiao regiao;

    @ToOne(joinProperty = "idCidade")
    Cidades cidades;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 439084187)
    private transient RegiaoCidadesDao myDao;

    @Generated(hash = 101808153)
    private transient Long regiao__resolvedKey;

    @Generated(hash = 1870410430)
    private transient Long cidades__resolvedKey;

    @Generated(hash = 1204354099)
    public RegiaoCidades(Long Id, Long idCidade, Long idRegiao) {
        this.Id = Id;
        this.idCidade = idCidade;
        this.idRegiao = idRegiao;
    }

    @Generated(hash = 1459014283)
    public RegiaoCidades() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getIdCidade() {
        return this.idCidade;
    }

    public void setIdCidade(Long idCidade) {
        this.idCidade = idCidade;
    }

    public Long getIdRegiao() {
        return this.idRegiao;
    }

    public void setIdRegiao(Long idRegiao) {
        this.idRegiao = idRegiao;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1857666810)
    public Regiao getRegiao() {
        Long __key = this.idRegiao;
        if (regiao__resolvedKey == null || !regiao__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegiaoDao targetDao = daoSession.getRegiaoDao();
            Regiao regiaoNew = targetDao.load(__key);
            synchronized (this) {
                regiao = regiaoNew;
                regiao__resolvedKey = __key;
            }
        }
        return regiao;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1057358567)
    public void setRegiao(Regiao regiao) {
        synchronized (this) {
            this.regiao = regiao;
            idRegiao = regiao == null ? null : regiao.getId();
            regiao__resolvedKey = idRegiao;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 282269470)
    public Cidades getCidades() {
        Long __key = this.idCidade;
        if (cidades__resolvedKey == null || !cidades__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CidadesDao targetDao = daoSession.getCidadesDao();
            Cidades cidadesNew = targetDao.load(__key);
            synchronized (this) {
                cidades = cidadesNew;
                cidades__resolvedKey = __key;
            }
        }
        return cidades;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1790428181)
    public void setCidades(Cidades cidades) {
        synchronized (this) {
            this.cidades = cidades;
            idCidade = cidades == null ? null : cidades.getId();
            cidades__resolvedKey = idCidade;
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
    @Generated(hash = 526825640)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRegiaoCidadesDao() : null;
    }

}
