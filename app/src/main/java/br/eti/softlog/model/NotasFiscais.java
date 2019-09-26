package br.eti.softlog.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

import java.util.List;

@Entity(nameInDb = "notas_fiscais")
public class NotasFiscais {

    @Id(autoincrement = true)
    Long id;

    @Property(nameInDb = "chave_nfe")
    String chaveNfe;

    @Property(nameInDb = "serie")
    String serie;

    @Property(nameInDb = "numero")
    String numero;

    @Property(nameInDb = "remetente_id")
    Long remetenteId;

    @ToOne(joinProperty = "remetenteId")
    Pessoas remetente;

    @Property(nameInDb = "destinatario_id")
    Long destinatarioId;

    @ToOne(joinProperty = "destinatarioId")
    Pessoas destinatario;

    @Property(nameInDb = "valor_nf")
    Double valorNf;

    @Property(nameInDb = "peso")
    Double peso;

    @Property(nameInDb = "volumes")
    Double volumes;

    @Property(nameInDb = "volume_cubico")
    Double volumeCubico;

    @Property(nameInDb = "id_romaneio")
    Long idRomaneio;

    @Property(nameInDb = "id_nf_protocolo")
    Long idNfProtocolo;

    @ToOne(joinProperty = "idNfProtocolo")
    Protocolo protocolo;

    @Property(nameInDb = "id_nota_fiscal_imp")
    Long idNotaFiscalImp;

    @Property(nameInDb = "observacao")
    String observacao;

    @Property(nameInDb = "qtd_vol_faltando")
    int qtdVolFaltando;

    @Property(nameInDb = "temOcorrencia")
    Boolean tem_ocorrencia;

    @Property(nameInDb = "id_ocorrencia")
    Long idOcorrencia;

    @ToOne(joinProperty = "idOcorrencia")
    Ocorrencias ocorrencia;

    @ToMany(referencedJoinProperty = "idNotaFiscalImp")
    List<AnexosOcorrencias> anexosOcorrencias;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 249675720)
    private transient NotasFiscaisDao myDao;

    @Generated(hash = 1857173058)
    public NotasFiscais(Long id, String chaveNfe, String serie, String numero,
            Long remetenteId, Long destinatarioId, Double valorNf, Double peso,
            Double volumes, Double volumeCubico, Long idRomaneio,
            Long idNfProtocolo, Long idNotaFiscalImp, String observacao,
            int qtdVolFaltando, Boolean tem_ocorrencia, Long idOcorrencia) {
        this.id = id;
        this.chaveNfe = chaveNfe;
        this.serie = serie;
        this.numero = numero;
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.valorNf = valorNf;
        this.peso = peso;
        this.volumes = volumes;
        this.volumeCubico = volumeCubico;
        this.idRomaneio = idRomaneio;
        this.idNfProtocolo = idNfProtocolo;
        this.idNotaFiscalImp = idNotaFiscalImp;
        this.observacao = observacao;
        this.qtdVolFaltando = qtdVolFaltando;
        this.tem_ocorrencia = tem_ocorrencia;
        this.idOcorrencia = idOcorrencia;
    }

    @Generated(hash = 55724482)
    public NotasFiscais() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveNfe() {
        return this.chaveNfe;
    }

    public void setChaveNfe(String chaveNfe) {
        this.chaveNfe = chaveNfe;
    }

    public String getSerie() {
        return this.serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getRemetenteId() {
        return this.remetenteId;
    }

    public void setRemetenteId(Long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public Long getDestinatarioId() {
        return this.destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public Double getValorNf() {
        return this.valorNf;
    }

    public void setValorNf(Double valorNf) {
        this.valorNf = valorNf;
    }

    public Double getPeso() {
        return this.peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getVolumes() {
        return this.volumes;
    }

    public void setVolumes(Double volumes) {
        this.volumes = volumes;
    }

    public Double getVolumeCubico() {
        return this.volumeCubico;
    }

    public void setVolumeCubico(Double volumeCubico) {
        this.volumeCubico = volumeCubico;
    }

    public Long getIdRomaneio() {
        return this.idRomaneio;
    }

    public void setIdRomaneio(Long idRomaneio) {
        this.idRomaneio = idRomaneio;
    }

    public Long getIdNfProtocolo() {
        return this.idNfProtocolo;
    }

    public void setIdNfProtocolo(Long idNfProtocolo) {
        this.idNfProtocolo = idNfProtocolo;
    }

    public Long getIdNotaFiscalImp() {
        return this.idNotaFiscalImp;
    }

    public void setIdNotaFiscalImp(Long idNotaFiscalImp) {
        this.idNotaFiscalImp = idNotaFiscalImp;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQtdVolFaltando() {
        return this.qtdVolFaltando;
    }

    public void setQtdVolFaltando(int qtdVolFaltando) {
        this.qtdVolFaltando = qtdVolFaltando;
    }

    public Boolean getTem_ocorrencia() {
        return this.tem_ocorrencia;
    }

    public void setTem_ocorrencia(Boolean tem_ocorrencia) {
        this.tem_ocorrencia = tem_ocorrencia;
    }

    public Long getIdOcorrencia() {
        return this.idOcorrencia;
    }

    public void setIdOcorrencia(Long idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
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

    @Generated(hash = 695539817)
    private transient Long destinatario__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1708312780)
    public Pessoas getDestinatario() {
        Long __key = this.destinatarioId;
        if (destinatario__resolvedKey == null
                || !destinatario__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PessoasDao targetDao = daoSession.getPessoasDao();
            Pessoas destinatarioNew = targetDao.load(__key);
            synchronized (this) {
                destinatario = destinatarioNew;
                destinatario__resolvedKey = __key;
            }
        }
        return destinatario;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 347350314)
    public void setDestinatario(Pessoas destinatario) {
        synchronized (this) {
            this.destinatario = destinatario;
            destinatarioId = destinatario == null ? null : destinatario.getId();
            destinatario__resolvedKey = destinatarioId;
        }
    }

    @Generated(hash = 163363226)
    private transient Long protocolo__resolvedKey;

    @Generated(hash = 1723205787)
    private transient Long ocorrencia__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 14119917)
    public Protocolo getProtocolo() {
        Long __key = this.idNfProtocolo;
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
    @Generated(hash = 601082698)
    public void setProtocolo(Protocolo protocolo) {
        synchronized (this) {
            this.protocolo = protocolo;
            idNfProtocolo = protocolo == null ? null : protocolo.getId();
            protocolo__resolvedKey = idNfProtocolo;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 80608987)
    public List<AnexosOcorrencias> getAnexosOcorrencias() {
        if (anexosOcorrencias == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AnexosOcorrenciasDao targetDao = daoSession.getAnexosOcorrenciasDao();
            List<AnexosOcorrencias> anexosOcorrenciasNew = targetDao
                    ._queryNotasFiscais_AnexosOcorrencias(id);
            synchronized (this) {
                if (anexosOcorrencias == null) {
                    anexosOcorrencias = anexosOcorrenciasNew;
                }
            }
        }
        return anexosOcorrencias;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 401687450)
    public synchronized void resetAnexosOcorrencias() {
        anexosOcorrencias = null;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 548721076)
    public Ocorrencias getOcorrencia() {
        Long __key = this.idOcorrencia;
        if (ocorrencia__resolvedKey == null || !ocorrencia__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OcorrenciasDao targetDao = daoSession.getOcorrenciasDao();
            Ocorrencias ocorrenciaNew = targetDao.load(__key);
            synchronized (this) {
                ocorrencia = ocorrenciaNew;
                ocorrencia__resolvedKey = __key;
            }
        }
        return ocorrencia;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 588812711)
    public void setOcorrencia(Ocorrencias ocorrencia) {
        synchronized (this) {
            this.ocorrencia = ocorrencia;
            idOcorrencia = ocorrencia == null ? null : ocorrencia.getId();
            ocorrencia__resolvedKey = idOcorrencia;
        }
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1512849439)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNotasFiscaisDao() : null;
    }



}
