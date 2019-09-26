package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "contagem_itens")
public class ContagemItens {

    @Id(autoincrement = true)
    private Long Id;

    @Property(nameInDb = "contagem_id")
    private Long contagemId;

    @ToOne(joinProperty = "contagemId")
    private Contagem contagem;

    @Property(nameInDb = "codigo_barras")
    private String codigoBarras;

    @Property(nameInDb = "fotografia")
    private String fotografia;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1752534254)
    private transient ContagemItensDao myDao;

    @Generated(hash = 712523093)
    public ContagemItens(Long Id, Long contagemId, String codigoBarras,
            String fotografia) {
        this.Id = Id;
        this.contagemId = contagemId;
        this.codigoBarras = codigoBarras;
        this.fotografia = fotografia;
    }

    @Generated(hash = 1363586485)
    public ContagemItens() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getContagemId() {
        return this.contagemId;
    }

    public void setContagemId(Long contagemId) {
        this.contagemId = contagemId;
    }

    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getFotografia() {
        return this.fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    @Generated(hash = 1110242341)
    private transient Long contagem__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 920814500)
    public Contagem getContagem() {
        Long __key = this.contagemId;
        if (contagem__resolvedKey == null || !contagem__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContagemDao targetDao = daoSession.getContagemDao();
            Contagem contagemNew = targetDao.load(__key);
            synchronized (this) {
                contagem = contagemNew;
                contagem__resolvedKey = __key;
            }
        }
        return contagem;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 767307619)
    public void setContagem(Contagem contagem) {
        synchronized (this) {
            this.contagem = contagem;
            contagemId = contagem == null ? null : contagem.getId();
            contagem__resolvedKey = contagemId;
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
    @Generated(hash = 1199927576)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getContagemItensDao() : null;
    }

}
