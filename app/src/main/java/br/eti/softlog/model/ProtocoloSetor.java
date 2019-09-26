package br.eti.softlog.model;

/**
 * Created by Paulo Sergio Alves on 2018/01/31.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;
import java.text.ParseException;
import java.util.List;

@Entity(nameInDb = "protocolo_setor")
public class ProtocoloSetor {

    @Id()
    private Long id;

    @Property(nameInDb="regiao_id")
    private Long regiaoId;

    @ToOne(joinProperty = "regiaoId")
    private Regiao regiao;

    @Property(nameInDb="protocolo_id")
    private Long protocoloId;

    @ToOne(joinProperty = "protocoloId")
    private Protocolo protocolo;

    @Property(nameInDb="qtd_volumes")
    private int qtdVolumes;

    @Property(nameInDb="qtd_conferencia")
    private int qtdConferencia;

    @Property(nameInDb="qtd_notas")
    private int qtdNotas;

    @Property(nameInDb="id_usuario_conferencia")
    private Long idUsuarioConferencia;

    @ToOne(joinProperty = "idUsuarioConferencia")
    private UsuariosSistema usuarioConferencia;

    @Property(nameInDb = "data_conferencia")
    private String dataConferencia;

    @ToMany(referencedJoinProperty = "protocoloSetorId")
    @OrderBy("id ASC")
    private List<ProtocoloRemetente> remetentes;

    @Property(nameInDb = "id_romaneio")
    Long idRomaneio;

    @ToOne(joinProperty = "idRomaneio")
    Romaneios romaneio;

    @Property(nameInDb = "sincronizado")
    Boolean sincronizado;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    /** Used for active entity operations. */
    @Generated(hash = 359420000)
    private transient ProtocoloSetorDao myDao;



    @Generated(hash = 27161482)
    public ProtocoloSetor(Long id, Long regiaoId, Long protocoloId, int qtdVolumes, int qtdConferencia,
            int qtdNotas, Long idUsuarioConferencia, String dataConferencia, Long idRomaneio,
            Boolean sincronizado) {
        this.id = id;
        this.regiaoId = regiaoId;
        this.protocoloId = protocoloId;
        this.qtdVolumes = qtdVolumes;
        this.qtdConferencia = qtdConferencia;
        this.qtdNotas = qtdNotas;
        this.idUsuarioConferencia = idUsuarioConferencia;
        this.dataConferencia = dataConferencia;
        this.idRomaneio = idRomaneio;
        this.sincronizado = sincronizado;
    }

    @Generated(hash = 1251330090)
    public ProtocoloSetor() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegiaoId() {
        return this.regiaoId;
    }

    public void setRegiaoId(Long regiaoId) {
        this.regiaoId = regiaoId;
    }

    public Long getProtocoloId() {
        return this.protocoloId;
    }

    public void setProtocoloId(Long protocoloId) {
        this.protocoloId = protocoloId;
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

    public int getQtdNotas() {
        return this.qtdNotas;
    }

    public void setQtdNotas(int qtdNotas) {
        this.qtdNotas = qtdNotas;
    }

    public Long getIdUsuarioConferencia() {
        return this.idUsuarioConferencia;
    }

    public void setIdUsuarioConferencia(Long idUsuarioConferencia) {
        this.idUsuarioConferencia = idUsuarioConferencia;
    }

    public String getDataConferencia() {
        return this.dataConferencia;
    }

    public void setDataConferencia(String dataConferencia) {
        this.dataConferencia = dataConferencia;
    }

    @Generated(hash = 101808153)
    private transient Long regiao__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 962445062)
    public Regiao getRegiao() {
        Long __key = this.regiaoId;
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
    @Generated(hash = 1920134820)
    public void setRegiao(Regiao regiao) {
        synchronized (this) {
            this.regiao = regiao;
            regiaoId = regiao == null ? null : regiao.getId();
            regiao__resolvedKey = regiaoId;
        }
    }

    @Generated(hash = 163363226)
    private transient Long protocolo__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1945419988)
    public Protocolo getProtocolo() {
        Long __key = this.protocoloId;
        if (protocolo__resolvedKey == null
                || !protocolo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProtocoloDao targetDao = daoSession.getProtocoloDao();
            Protocolo protocoloNew = targetDao.load(__key);
            synchronized (this) {
                protocolo = protocoloNew;
                protocolo__resolvedKey = __key;
            }
        }
        return protocolo;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1132151836)
    public void setProtocolo(Protocolo protocolo) {
        synchronized (this) {
            this.protocolo = protocolo;
            protocoloId = protocolo == null ? null : protocolo.getId();
            protocolo__resolvedKey = protocoloId;
        }
    }

    @Generated(hash = 879899944)
    private transient Long usuarioConferencia__resolvedKey;

    @Generated(hash = 1771332099)
    private transient Long romaneio__resolvedKey;

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

    @Override
    public String toString(){
        String mensagem;

        try {
            mensagem ="Nr.: " + this.getProtocolo().getId().toString()
                    + " - " + this.getProtocolo().getDataProtocoloFormat()
                    + " - " + this.getRegiao().getDescricao().toString();
        } catch (ParseException e) {
            mensagem = "";
            e.printStackTrace();
        }

        return mensagem;
    }




    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1471278815)
    public List<ProtocoloRemetente> getRemetentes() {
        if (remetentes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProtocoloRemetenteDao targetDao = daoSession.getProtocoloRemetenteDao();
            List<ProtocoloRemetente> remetentesNew = targetDao
                    ._queryProtocoloSetor_Remetentes(id);
            synchronized (this) {
                if (remetentes == null) {
                    remetentes = remetentesNew;
                }
            }
        }
        return remetentes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1289265571)
    public synchronized void resetRemetentes() {
        remetentes = null;
    }

    public Long getIdRomaneio() {
        return this.idRomaneio;
    }

    public void setIdRomaneio(Long idRomaneio) {
        this.idRomaneio = idRomaneio;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 265507785)
    public Romaneios getRomaneio() {
        Long __key = this.idRomaneio;
        if (romaneio__resolvedKey == null || !romaneio__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RomaneiosDao targetDao = daoSession.getRomaneiosDao();
            Romaneios romaneioNew = targetDao.load(__key);
            synchronized (this) {
                romaneio = romaneioNew;
                romaneio__resolvedKey = __key;
            }
        }
        return romaneio;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1835363966)
    public void setRomaneio(Romaneios romaneio) {
        synchronized (this) {
            this.romaneio = romaneio;
            idRomaneio = romaneio == null ? null : romaneio.getId();
            romaneio__resolvedKey = idRomaneio;
        }
    }

    public Boolean getSincronizado() {
        return this.sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 919818851)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProtocoloSetorDao() : null;
    }


}
