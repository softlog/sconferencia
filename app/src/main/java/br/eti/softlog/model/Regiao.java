package br.eti.softlog.model;

/**
 * Created by Administrador on 2018/02/19.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Paulo Sergio Alves on 2018/01/31.
 * Chagend by Nilton Paulino on 2018/03/13.
 *
 */
@Entity(nameInDb = "regiao")
public class Regiao {

    @Id()
    private Long id;

    @Property(nameInDb = "descricao")
    private String descricao;

    @Property(nameInDb = "id_cidade_polo")
    Long idCidadePolo;

    @Property(nameInDb = "id_regiao_agrupadora")
    Long idRegiaoAgrupadora;

    @ToMany(referencedJoinProperty = "regiaoId")
    List<ProtocoloSetor> protocolos;

    @ToMany(referencedJoinProperty = "idRegiao")
    List<RegiaoCidades> regiaoCidades;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 532909860)
    private transient RegiaoDao myDao;

    public Regiao() {

    }

    @Generated(hash = 1166327330)
    public Regiao(Long id, String descricao, Long idCidadePolo,
            Long idRegiaoAgrupadora) {
        this.id = id;
        this.descricao = descricao;
        this.idCidadePolo = idCidadePolo;
        this.idRegiaoAgrupadora = idRegiaoAgrupadora;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdCidadePolo() {
        return this.idCidadePolo;
    }

    public void setIdCidadePolo(Long idCidadePolo) {
        this.idCidadePolo = idCidadePolo;
    }

    public Long getIdRegiaoAgrupadora() {
        return this.idRegiaoAgrupadora;
    }

    public void setIdRegiaoAgrupadora(Long idRegiaoAgrupadora) {
        this.idRegiaoAgrupadora = idRegiaoAgrupadora;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1609040872)
    public List<ProtocoloSetor> getProtocolos() {
        if (protocolos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProtocoloSetorDao targetDao = daoSession.getProtocoloSetorDao();
            List<ProtocoloSetor> protocolosNew = targetDao
                    ._queryRegiao_Protocolos(id);
            synchronized (this) {
                if (protocolos == null) {
                    protocolos = protocolosNew;
                }
            }
        }
        return protocolos;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 856838810)
    public synchronized void resetProtocolos() {
        protocolos = null;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1045330971)
    public List<RegiaoCidades> getRegiaoCidades() {
        if (regiaoCidades == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegiaoCidadesDao targetDao = daoSession.getRegiaoCidadesDao();
            List<RegiaoCidades> regiaoCidadesNew = targetDao._queryRegiao_RegiaoCidades(id);
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 29553303)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRegiaoDao() : null;
    }


}
