package br.eti.softlog.model;



import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "anexos_ocorrencias")
public class AnexosOcorrencias {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "protocolo_id")
    Long protocoloId;

    @Property(nameInDb = "id_nota_fiscal_imp")
    Long idNotaFiscalImp;

    @Property(nameInDb = "sincronizado")
    Boolean sincronizado;

    @Property(nameInDb = "data_registro")
    String dataRegistro;

    @Property(nameInDb = "conteudo_anexo")
    String conteudoAnexo;

    @ToOne(joinProperty = "idNotaFiscalImp")
    NotasFiscais notaFiscal;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1535358944)
    private transient AnexosOcorrenciasDao myDao;

    @Generated(hash = 1606585048)
    public AnexosOcorrencias(Long id, Long protocoloId, Long idNotaFiscalImp,
            Boolean sincronizado, String dataRegistro, String conteudoAnexo) {
        this.id = id;
        this.protocoloId = protocoloId;
        this.idNotaFiscalImp = idNotaFiscalImp;
        this.sincronizado = sincronizado;
        this.dataRegistro = dataRegistro;
        this.conteudoAnexo = conteudoAnexo;
    }

    @Generated(hash = 1140862740)
    public AnexosOcorrencias() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProtocoloId() {
        return this.protocoloId;
    }

    public void setProtocoloId(Long protocoloId) {
        this.protocoloId = protocoloId;
    }

    public Long getIdNotaFiscalImp() {
        return this.idNotaFiscalImp;
    }

    public void setIdNotaFiscalImp(Long idNotaFiscalImp) {
        this.idNotaFiscalImp = idNotaFiscalImp;
    }

    public Boolean getSincronizado() {
        return this.sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public String getDataRegistro() {
        return this.dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getConteudoAnexo() {
        return this.conteudoAnexo;
    }

    public void setConteudoAnexo(String conteudoAnexo) {
        this.conteudoAnexo = conteudoAnexo;
    }

    @Generated(hash = 1300611105)
    private transient Long notaFiscal__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 898261230)
    public NotasFiscais getNotaFiscal() {
        Long __key = this.idNotaFiscalImp;
        if (notaFiscal__resolvedKey == null
                || !notaFiscal__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NotasFiscaisDao targetDao = daoSession.getNotasFiscaisDao();
            NotasFiscais notaFiscalNew = targetDao.load(__key);
            synchronized (this) {
                notaFiscal = notaFiscalNew;
                notaFiscal__resolvedKey = __key;
            }
        }
        return notaFiscal;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1708714074)
    public void setNotaFiscal(NotasFiscais notaFiscal) {
        synchronized (this) {
            this.notaFiscal = notaFiscal;
            idNotaFiscalImp = notaFiscal == null ? null : notaFiscal.getId();
            notaFiscal__resolvedKey = idNotaFiscalImp;
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
    @Generated(hash = 1263725329)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAnexosOcorrenciasDao() : null;
    }


}
