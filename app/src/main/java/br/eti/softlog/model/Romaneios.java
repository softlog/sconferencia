package br.eti.softlog.model;


import android.content.Intent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

import java.util.List;

@Entity(nameInDb = "romaneios")
public class Romaneios {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "placa_veiculo")
    String placaVeiculo;

    @Property(nameInDb = "id_motorista")
    Long idMotorista;

    @Property(nameInDb = "data_romaneio")
    String dataRomaneio;

    @Property(nameInDb = "tipo_destino")
    String tipoDestino;

    @Property(nameInDb = "id_origem")
    Long idOrigem;

    @ToOne(joinProperty = "idOrigem")
    Cidades origem;

    @Property(nameInDb = "id_destino")
    Long idDestino;

    @ToOne(joinProperty = "idDestino")
    Cidades destino;

    @Property(nameInDb = "id_setor")
    Long idSetor;

    @ToOne(joinProperty = "idSetor")
    Regiao setor;

    @Property(nameInDb = "data_registro")
    String dataRegistro;

    @Property(nameInDb = "sincronizado")
    Boolean sincronizado;

    @Property(nameInDb = "id_usuario")
    Long idUsuario;

    @Property(nameInDb = "concluido")
    Boolean concluido;

    @ToMany(referencedJoinProperty = "idRomaneio")
    List<NotasFiscais> notasFiscais;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1521602884)
    private transient RomaneiosDao myDao;

    @Generated(hash = 1518644232)
    private transient Long origem__resolvedKey;

    @Generated(hash = 181593545)
    private transient Long destino__resolvedKey;

    @Generated(hash = 1617827480)
    private transient Long setor__resolvedKey;

    @Generated(hash = 1087808867)
    public Romaneios(Long id, String placaVeiculo, Long idMotorista, String dataRomaneio,
            String tipoDestino, Long idOrigem, Long idDestino, Long idSetor, String dataRegistro,
            Boolean sincronizado, Long idUsuario, Boolean concluido) {
        this.id = id;
        this.placaVeiculo = placaVeiculo;
        this.idMotorista = idMotorista;
        this.dataRomaneio = dataRomaneio;
        this.tipoDestino = tipoDestino;
        this.idOrigem = idOrigem;
        this.idDestino = idDestino;
        this.idSetor = idSetor;
        this.dataRegistro = dataRegistro;
        this.sincronizado = sincronizado;
        this.idUsuario = idUsuario;
        this.concluido = concluido;
    }

    @Generated(hash = 781128074)
    public Romaneios() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlacaVeiculo() {
        return this.placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Long getIdMotorista() {
        return this.idMotorista;
    }

    public void setIdMotorista(Long idMotorista) {
        this.idMotorista = idMotorista;
    }

    public String getDataRomaneio() {
        return this.dataRomaneio;
    }

    public void setDataRomaneio(String dataRomaneio) {
        this.dataRomaneio = dataRomaneio;
    }

    public String getTipoDestino() {
        return this.tipoDestino;
    }

    public void setTipoDestino(String tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

    public Long getIdOrigem() {
        return this.idOrigem;
    }

    public void setIdOrigem(Long idOrigem) {
        this.idOrigem = idOrigem;
    }

    public Long getIdDestino() {
        return this.idDestino;
    }

    public void setIdDestino(Long idDestino) {
        this.idDestino = idDestino;
    }

    public Long getIdSetor() {
        return this.idSetor;
    }

    public void setIdSetor(Long idSetor) {
        this.idSetor = idSetor;
    }

    public String getDataRegistro() {
        return this.dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Boolean getSincronizado() {
        return this.sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1152884194)
    public Cidades getOrigem() {
        Long __key = this.idOrigem;
        if (origem__resolvedKey == null || !origem__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CidadesDao targetDao = daoSession.getCidadesDao();
            Cidades origemNew = targetDao.load(__key);
            synchronized (this) {
                origem = origemNew;
                origem__resolvedKey = __key;
            }
        }
        return origem;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1605522501)
    public void setOrigem(Cidades origem) {
        synchronized (this) {
            this.origem = origem;
            idOrigem = origem == null ? null : origem.getId();
            origem__resolvedKey = idOrigem;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 461876824)
    public Cidades getDestino() {
        Long __key = this.idDestino;
        if (destino__resolvedKey == null || !destino__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CidadesDao targetDao = daoSession.getCidadesDao();
            Cidades destinoNew = targetDao.load(__key);
            synchronized (this) {
                destino = destinoNew;
                destino__resolvedKey = __key;
            }
        }
        return destino;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 23330336)
    public void setDestino(Cidades destino) {
        synchronized (this) {
            this.destino = destino;
            idDestino = destino == null ? null : destino.getId();
            destino__resolvedKey = idDestino;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2136306191)
    public Regiao getSetor() {
        Long __key = this.idSetor;
        if (setor__resolvedKey == null || !setor__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RegiaoDao targetDao = daoSession.getRegiaoDao();
            Regiao setorNew = targetDao.load(__key);
            synchronized (this) {
                setor = setorNew;
                setor__resolvedKey = __key;
            }
        }
        return setor;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 864093137)
    public void setSetor(Regiao setor) {
        synchronized (this) {
            this.setor = setor;
            idSetor = setor == null ? null : setor.getId();
            setor__resolvedKey = idSetor;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 678524081)
    public List<NotasFiscais> getNotasFiscais() {
        if (notasFiscais == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NotasFiscaisDao targetDao = daoSession.getNotasFiscaisDao();
            List<NotasFiscais> notasFiscaisNew = targetDao._queryRomaneios_NotasFiscais(id);
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

    public Boolean getConcluido() {
        return this.concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 724949082)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRomaneiosDao() : null;
    }
}
