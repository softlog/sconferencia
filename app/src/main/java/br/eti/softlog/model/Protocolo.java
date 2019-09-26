package br.eti.softlog.model;

/**
 * Created by Administrador on 2018/02/26.
 */
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Paulo Sergio Alves on 2018/01/31.
 */
@Entity(nameInDb = "protocolo")
public class Protocolo {

    @Id()
    private Long id;

    @Property(nameInDb = "data_protocolo")
    private String data_protocolo;

    @Property(nameInDb = "data_conferencia")
    private String dataConferencia;

    @Property(nameInDb = "id_usuario_protocolo")
    private Long idUsuarioProtocolo;

    @ToOne(joinProperty = "idUsuarioProtocolo")
    private UsuariosSistema usuarioProtoloco;

    @Property(nameInDb = "id_usuario_conferencia")
    private Long idUsuarioConferencia;

    @ToOne(joinProperty = "idUsuarioConferencia")
    private UsuariosSistema usuarioConferencia;

    @Property(nameInDb="status")
    private boolean status;

    @Property(nameInDb="sincronizado")
    private boolean sincronizado;

    @Property(nameInDb="data_expedicao")
    private String dataExpedicao;

    @Property(nameInDb="enviar")
    private boolean enviar;

    @Property(nameInDb = "observacao")
    private String observacao;

    @ToMany(referencedJoinProperty = "idNfProtocolo")
    List<NotasFiscais> notasFiscais;


    @ToMany(referencedJoinProperty = "protocoloId")
    @OrderBy("id ASC")
    private List<ProtocoloSetor> protocoloSetores;


    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1015198794)
    private transient ProtocoloDao myDao;

    
    public Protocolo(Long id, String data_protocolo, String dataConferencia, Long idUsuarioProtocolo,
            Long idUsuarioConferencia, boolean status, boolean sincronizado, String dataExpedicao) {
        this.id = id;
        this.data_protocolo = data_protocolo;
        this.dataConferencia = dataConferencia;
        this.idUsuarioProtocolo = idUsuarioProtocolo;
        this.idUsuarioConferencia = idUsuarioConferencia;
        this.status = status;
        this.sincronizado = sincronizado;
        this.dataExpedicao = dataExpedicao;
    }

    @Generated(hash = 794063162)
    public Protocolo() {
    }

    @Generated(hash = 610207900)
    public Protocolo(Long id, String data_protocolo, String dataConferencia, Long idUsuarioProtocolo,
            Long idUsuarioConferencia, boolean status, boolean sincronizado, String dataExpedicao,
            boolean enviar, String observacao) {
        this.id = id;
        this.data_protocolo = data_protocolo;
        this.dataConferencia = dataConferencia;
        this.idUsuarioProtocolo = idUsuarioProtocolo;
        this.idUsuarioConferencia = idUsuarioConferencia;
        this.status = status;
        this.sincronizado = sincronizado;
        this.dataExpedicao = dataExpedicao;
        this.enviar = enviar;
        this.observacao = observacao;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData_protocolo() {
        return this.data_protocolo;
    }

    public void setData_protocolo(String data_protocolo) {
        this.data_protocolo = data_protocolo;
    }

    public String getDataConferencia() {
        return this.dataConferencia;
    }

    public void setDataConferencia(String dataConferencia) {
        this.dataConferencia = dataConferencia;
    }

    public Long getIdUsuarioProtocolo() {
        return this.idUsuarioProtocolo;
    }

    public void setIdUsuarioProtocolo(Long idUsuarioProtocolo) {
        this.idUsuarioProtocolo = idUsuarioProtocolo;
    }

    public Long getIdUsuarioConferencia() {
        return this.idUsuarioConferencia;
    }

    public void setIdUsuarioConferencia(Long idUsuarioConferencia) {
        this.idUsuarioConferencia = idUsuarioConferencia;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getSincronizado() {
        return this.sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    @Generated(hash = 2112900268)
    private transient Long usuarioProtoloco__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 608288878)
    public UsuariosSistema getUsuarioProtoloco() {
        Long __key = this.idUsuarioProtocolo;
        if (usuarioProtoloco__resolvedKey == null
                || !usuarioProtoloco__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UsuariosSistemaDao targetDao = daoSession.getUsuariosSistemaDao();
            UsuariosSistema usuarioProtolocoNew = targetDao.load(__key);
            synchronized (this) {
                usuarioProtoloco = usuarioProtolocoNew;
                usuarioProtoloco__resolvedKey = __key;
            }
        }
        return usuarioProtoloco;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1818343860)
    public void setUsuarioProtoloco(UsuariosSistema usuarioProtoloco) {
        synchronized (this) {
            this.usuarioProtoloco = usuarioProtoloco;
            idUsuarioProtocolo = usuarioProtoloco == null ? null
                    : usuarioProtoloco.getId();
            usuarioProtoloco__resolvedKey = idUsuarioProtocolo;
        }
    }

    @Generated(hash = 879899944)
    private transient Long usuarioConferencia__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1322148068)
    public UsuariosSistema getUsuarioConferencia() {
        Long __key = this.idUsuarioConferencia;
        if (usuarioConferencia__resolvedKey == null
                || !usuarioConferencia__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UsuariosSistemaDao targetDao = daoSession.getUsuariosSistemaDao();
            UsuariosSistema usuarioConferenciaNew = targetDao.load(__key);
            synchronized (this) {
                usuarioConferencia = usuarioConferenciaNew;
                usuarioConferencia__resolvedKey = __key;
            }
        }
        return usuarioConferencia;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 966963861)
    public void setUsuarioConferencia(UsuariosSistema usuarioConferencia) {
        synchronized (this) {
            this.usuarioConferencia = usuarioConferencia;
            idUsuarioConferencia = usuarioConferencia == null ? null
                    : usuarioConferencia.getId();
            usuarioConferencia__resolvedKey = idUsuarioConferencia;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 409128226)
    public List<ProtocoloSetor> getProtocoloSetores() {
        if (protocoloSetores == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProtocoloSetorDao targetDao = daoSession.getProtocoloSetorDao();
            List<ProtocoloSetor> protocoloSetoresNew = targetDao
                    ._queryProtocolo_ProtocoloSetores(id);
            synchronized (this) {
                if (protocoloSetores == null) {
                    protocoloSetores = protocoloSetoresNew;
                }
            }
        }
        return protocoloSetores;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 373241287)
    public synchronized void resetProtocoloSetores() {
        protocoloSetores = null;
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

    public String getDataProtocoloFormat() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = formato.parse(this.getData_protocolo());
        formato.applyPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = formato.format(data);

        return dataFormatada;
    }

    public String getDataExpedicao() {
        return this.dataExpedicao;
    }

    public void setDataExpedicao(String dataExpedicao) {
        this.dataExpedicao = dataExpedicao;
    }

    public boolean getEnviar() {
        return this.enviar;
    }

    public void setEnviar(boolean enviar) {
        this.enviar = enviar;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1602421007)
    public List<NotasFiscais> getNotasFiscais() {
        if (notasFiscais == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NotasFiscaisDao targetDao = daoSession.getNotasFiscaisDao();
            List<NotasFiscais> notasFiscaisNew = targetDao._queryProtocolo_NotasFiscais(id);
            synchronized (this) {
                if (notasFiscais == null) {
                    notasFiscais = notasFiscaisNew;
                }
            }
        }
        return notasFiscais;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1726786399)
    public synchronized void resetNotasFiscais() {
        notasFiscais = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1858802177)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProtocoloDao() : null;
    }



}
