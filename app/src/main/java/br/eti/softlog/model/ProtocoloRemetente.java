package br.eti.softlog.model;

/**
 * Created by Paulo Sérgio Alves on 2018/04/02.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import java.text.ParseException;


@Entity(nameInDb = "protocolo_remetentes")
public class ProtocoloRemetente {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "protocolo_setor_id")
    private Long protocoloSetorId;

    @ToOne(joinProperty = "protocoloSetorId")
    private ProtocoloSetor protocoloSetor;

    @Property(nameInDb = "remetente_id")
    private Long remetenteId;

    @ToOne(joinProperty = "remetenteId")
    private Pessoas remetente;
    //private Remetente remetente;

    @Property(nameInDb = "qtd_notas")
    private int qtdNotas;

    @Property(nameInDb = "qtd_volumes")
    private int qtdVolumes;

    @Property(nameInDb = "qtd_conferencia")
    private int qtdConferencia;

    @Property(nameInDb = "status")
    private int status;

    @Property(nameInDb = "observacao")
    private String observacao;

    @Property(nameInDb = "sincronizado")
    private Boolean sincronizado;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1015890324)
    private transient ProtocoloRemetenteDao myDao;

    @Generated(hash = 1807491332)
    public ProtocoloRemetente(Long id, Long protocoloSetorId, Long remetenteId, int qtdNotas,
            int qtdVolumes, int qtdConferencia, int status, String observacao,
            Boolean sincronizado) {
        this.id = id;
        this.protocoloSetorId = protocoloSetorId;
        this.remetenteId = remetenteId;
        this.qtdNotas = qtdNotas;
        this.qtdVolumes = qtdVolumes;
        this.qtdConferencia = qtdConferencia;
        this.status = status;
        this.observacao = observacao;
        this.sincronizado = sincronizado;
    }

    @Generated(hash = 1077676244)
    public ProtocoloRemetente() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProtocoloSetorId() {
        return this.protocoloSetorId;
    }

    public void setProtocoloSetorId(Long protocoloSetorId) {
        this.protocoloSetorId = protocoloSetorId;
    }

    public Long getRemetenteId() {
        return this.remetenteId;
    }

    public void setRemetenteId(Long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public int getQtdNotas() {
        return this.qtdNotas;
    }

    public void setQtdNotas(int qtdNotas) {
        this.qtdNotas = qtdNotas;
    }

    public int getQtdVolumes() {
        return this.qtdVolumes;
    }

    public void setQtdVolumes(int qtdVolumes) {
        this.qtdVolumes = qtdVolumes;
    }

    public int getQtdConferencia() {
        return this.qtdConferencia;
    }

    public void setQtdConferencia(int qtdConferencia) {
        this.qtdConferencia = qtdConferencia;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Generated(hash = 2003936452)
    private transient Long protocoloSetor__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1624486545)
    public ProtocoloSetor getProtocoloSetor() {
        Long __key = this.protocoloSetorId;
        if (protocoloSetor__resolvedKey == null
                || !protocoloSetor__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProtocoloSetorDao targetDao = daoSession.getProtocoloSetorDao();
            ProtocoloSetor protocoloSetorNew = targetDao.load(__key);
            synchronized (this) {
                protocoloSetor = protocoloSetorNew;
                protocoloSetor__resolvedKey = __key;
            }
        }
        return protocoloSetor;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1385652534)
    public void setProtocoloSetor(ProtocoloSetor protocoloSetor) {
        synchronized (this) {
            this.protocoloSetor = protocoloSetor;
            protocoloSetorId = protocoloSetor == null ? null
                    : protocoloSetor.getId();
            protocoloSetor__resolvedKey = protocoloSetorId;
        }
    }

    @Generated(hash = 603090513)
    private transient Long remetente__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1218649513)
    public Pessoas getRemetente() {
        Long __key = this.remetenteId;
        if (remetente__resolvedKey == null
                || !remetente__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PessoasDao targetDao = daoSession.getPessoasDao();
            Pessoas remetenteNew = targetDao.load(__key);
            synchronized (this) {
                remetente = remetenteNew;
                remetente__resolvedKey = __key;
            }
        }
        return remetente;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1937640412)
    public void setRemetente(Pessoas remetente) {
        synchronized (this) {
            this.remetente = remetente;
            remetenteId = remetente == null ? null : remetente.getId();
            remetente__resolvedKey = remetenteId;
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

    public Boolean getSincronizado() {
        return this.sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1035384087)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProtocoloRemetenteDao() : null;
    }


}
