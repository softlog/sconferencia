package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "contagem")
public class Contagem {

    @Id(autoincrement = true)
    private Long Id;

    @Property(nameInDb = "descricao")
    private String descricao;

    @Property(nameInDb = "data_contagem")
    private String dataContagem;

    @Property(nameInDb = "latitude")
    private String latitude;

    @Property(nameInDb = "longitude")
    private String longitude;

    @Property(nameInDb = "remetente_cnpj")
    private Long remetente_cnpj;

    @Property(nameInDb = "quantidade")
    private Long quantidade;

    @Property(nameInDb = "protocolo_remetente_id")
    private Long protocoloRemetenteId;

    @ToOne(joinProperty = "protocoloRemetenteId")
    private ProtocoloRemetente protocoloRemetente;

    @ToMany(referencedJoinProperty = "contagemId")
    private List<ContagemItens> contagemItens;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 859220550)
    private transient ContagemDao myDao;

    @Generated(hash = 1391557930)
    public Contagem(Long Id, String descricao, String dataContagem, String latitude,
            String longitude, Long remetente_cnpj, Long quantidade,
            Long protocoloRemetenteId) {
        this.Id = Id;
        this.descricao = descricao;
        this.dataContagem = dataContagem;
        this.latitude = latitude;
        this.longitude = longitude;
        this.remetente_cnpj = remetente_cnpj;
        this.quantidade = quantidade;
        this.protocoloRemetenteId = protocoloRemetenteId;
    }

    @Generated(hash = 828534)
    public Contagem() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataContagem() {
        return this.dataContagem;
    }

    public void setDataContagem(String dataContagem) {
        this.dataContagem = dataContagem;
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

    public Long getRemetente_cnpj() {
        return this.remetente_cnpj;
    }

    public void setRemetente_cnpj(Long remetente_cnpj) {
        this.remetente_cnpj = remetente_cnpj;
    }

    public Long getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Long getProtocoloRemetenteId() {
        return this.protocoloRemetenteId;
    }

    public void setProtocoloRemetenteId(Long protocoloRemetenteId) {
        this.protocoloRemetenteId = protocoloRemetenteId;
    }

    @Generated(hash = 610526216)
    private transient Long protocoloRemetente__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 496612656)
    public ProtocoloRemetente getProtocoloRemetente() {
        Long __key = this.protocoloRemetenteId;
        if (protocoloRemetente__resolvedKey == null
                || !protocoloRemetente__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ProtocoloRemetenteDao targetDao = daoSession.getProtocoloRemetenteDao();
            ProtocoloRemetente protocoloRemetenteNew = targetDao.load(__key);
            synchronized (this) {
                protocoloRemetente = protocoloRemetenteNew;
                protocoloRemetente__resolvedKey = __key;
            }
        }
        return protocoloRemetente;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1328150306)
    public void setProtocoloRemetente(ProtocoloRemetente protocoloRemetente) {
        synchronized (this) {
            this.protocoloRemetente = protocoloRemetente;
            protocoloRemetenteId = protocoloRemetente == null ? null
                    : protocoloRemetente.getId();
            protocoloRemetente__resolvedKey = protocoloRemetenteId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 608379050)
    public List<ContagemItens> getContagemItens() {
        if (contagemItens == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContagemItensDao targetDao = daoSession.getContagemItensDao();
            List<ContagemItens> contagemItensNew = targetDao
                    ._queryContagem_ContagemItens(Id);
            synchronized (this) {
                if (contagemItens == null) {
                    contagemItens = contagemItensNew;
                }
            }
        }
        return contagemItens;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1669313204)
    public synchronized void resetContagemItens() {
        contagemItens = null;
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
    @Generated(hash = 1871779424)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContagemDao() : null;
    }

}
