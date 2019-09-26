package br.eti.softlog.sconferencia;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.query.CountQuery;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.eti.softlog.model.Cidades;
import br.eti.softlog.model.CidadesDao;
import br.eti.softlog.model.DaoSession;
import br.eti.softlog.model.Motoristas;
import br.eti.softlog.model.MotoristasDao;
import br.eti.softlog.model.NotasFiscais;
import br.eti.softlog.model.NotasFiscaisDao;
import br.eti.softlog.model.Ocorrencias;
import br.eti.softlog.model.OcorrenciasDao;
import br.eti.softlog.model.Pessoas;
import br.eti.softlog.model.PessoasDao;
import br.eti.softlog.model.Protocolo;
import br.eti.softlog.model.ProtocoloDao;
import br.eti.softlog.model.ProtocoloRemetente;
import br.eti.softlog.model.ProtocoloRemetenteDao;
import br.eti.softlog.model.ProtocoloSetor;
import br.eti.softlog.model.ProtocoloSetorDao;
import br.eti.softlog.model.Regiao;
import br.eti.softlog.model.RegiaoCidades;
import br.eti.softlog.model.RegiaoCidadesDao;
import br.eti.softlog.model.RegiaoDao;
import br.eti.softlog.model.Remetente;
import br.eti.softlog.model.RemetenteDao;
import br.eti.softlog.model.Usuario;
import br.eti.softlog.model.UsuarioDao;
import br.eti.softlog.model.UsuariosSistema;
import br.eti.softlog.model.UsuariosSistemaDao;
import br.eti.softlog.model.Veiculos;
import br.eti.softlog.model.VeiculosDao;
import br.eti.softlog.sconferencia.ConferenceApp;
import br.eti.softlog.utils.Util;

/**
 * Created by Administrador on 2018/02/06.
 */

public class Manager {

    private ConferenceApp app ;
    private DaoSession session;

    public Manager(ConferenceApp myapp){
        app = myapp;
        session = app.getDaoSession();
    }

    //Adiciona um usuario, caso ele
    public Usuario addUsuario(String nome,
                              String login,
                              String senha,
                              String email) {

        Usuario usuario = findUsuarioByLogin(login);

        if (usuario == null) {
            usuario.setNome(nome);
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setEmail(email);
            session.getUsuarioDao().insert(usuario);
        }

        return usuario;
    }

    public Usuario findUsuarioByLogin(String login) {
        QueryBuilder query = app.getDaoSession().getUsuarioDao().queryBuilder();
        return (Usuario) query.where(UsuarioDao.Properties.Login.eq(login)).unique();
    }

    public boolean hasUsuario() {
        long qt = app.getDaoSession().getUsuarioDao().queryBuilder().count();
        if (qt==0) {
            return false;
        } else {
            return true;
        }
    }

    public Ocorrencias findOcorrenciaById(Long id){
        return app.getDaoSession().getOcorrenciasDao().queryBuilder()
                .where(OcorrenciasDao.Properties.Id.eq(id)).unique();
    }

    public Ocorrencias addOcorrencia(Long id, String ocorrencia, Boolean ativo){

        Ocorrencias ocorren = findOcorrenciaById(id);
        if (ocorren == null) {

            ocorren = new Ocorrencias(id, ocorrencia,ativo);
            this.session.insert(ocorren);
        } else {
            ocorren.setAtivo(ativo);
            this.session.update(ocorren);
        }
        return ocorren;
    }


    public Regiao findRegiaoById(Long id){
        return app.getDaoSession().getRegiaoDao().queryBuilder().where(RegiaoDao.Properties.Id.eq(id)).unique();
    }

    public Regiao addRegiao(Long id, String descricao, Long idCidadePolo, Long idRegiaoAgrupadora){

        Regiao regiao = findRegiaoById(id);
        if (regiao == null) {
            regiao = new Regiao(id, descricao,idCidadePolo,idRegiaoAgrupadora);
            this.session.insert(regiao);
        } else {

            regiao.setDescricao(descricao);
            regiao.setIdCidadePolo(idCidadePolo);
            regiao.setIdRegiaoAgrupadora(idRegiaoAgrupadora);
            this.session.update(regiao);

        }
        return regiao;
    }

    public UsuariosSistema findUsuariosSistemaById(Long id){
        return app.getDaoSession().getUsuariosSistemaDao().queryBuilder()
                .where(UsuariosSistemaDao.Properties.Id.eq(id)).unique();
    }

    public UsuariosSistema addUsuarioSistema(Long id, String nome_usuario, String email, String senha){

        UsuariosSistema usuariosSistema = findUsuariosSistemaById(id);

        if (usuariosSistema == null) {
            usuariosSistema = new UsuariosSistema(id, nome_usuario,email,senha);
            this.session.insert(usuariosSistema);
        } else {
            usuariosSistema.setNome(nome_usuario);
            usuariosSistema.setSenha(senha);
            this.session.update(usuariosSistema);
        }

        return usuariosSistema;
    }


    public Remetente findRemetenteById(Long id){
        return app.getDaoSession().getRemetenteDao().queryBuilder()
                .where(RemetenteDao.Properties.Id.eq(id)).unique();
    }

    public Remetente addRemetente(Long id, String remetente_nome, String remetente_cnpj){

        Remetente remetente = findRemetenteById(id);

        if (remetente == null) {
            remetente = new Remetente(id, remetente_nome,remetente_cnpj);
            this.session.insert(remetente);
        }

        return remetente;
    }

    public Protocolo findProtocoloById(Long id){

        return app.getDaoSession().getProtocoloDao().queryBuilder()
                 .where(ProtocoloDao.Properties.Id.eq(id)).unique();
    }


    public Date findMaxDataExpedicaoProtocolo() {
        Date date;
        Protocolo protocolo = app.getDaoSession().getProtocoloDao()
                .queryBuilder().orderDesc(ProtocoloDao.Properties.DataExpedicao).limit(1).unique();

        if (protocolo == null)
            return new Date();

        String data_expedicao = protocolo.getDataExpedicao();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formato.parse(data_expedicao);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public Protocolo addProtocolo(Long id, String data_protocolo, String data_conferencia,
                                  Long usuario_protocolo, Long usuario_conferencia, boolean status,
                                  boolean sincronizado, String data_expedicao){

        Protocolo protocolo = findProtocoloById(id);

        if (protocolo == null){
            protocolo = new Protocolo(id, data_protocolo,data_conferencia, usuario_protocolo,
                    usuario_conferencia, status,sincronizado, data_expedicao);
            this.session.insert(protocolo);
        } else {
            if (!protocolo.getEnviar()) {
                protocolo.setDataConferencia(data_conferencia);
                protocolo.setIdUsuarioConferencia(usuario_conferencia);
                protocolo.setStatus(status);
                this.session.update(protocolo);
            }
        }

        return protocolo;
    };

    public List<Protocolo> findProtocoloByEnviar(){

        return app.getDaoSession().getProtocoloDao().queryBuilder().
                where(ProtocoloDao.Properties.Enviar.eq(true),
                        ProtocoloDao.Properties.Sincronizado.eq(false)).
                        orderAsc(ProtocoloDao.Properties.Id).list();

    }


    public ProtocoloSetor findProtocoloSetorByIdProtocoloIdSetor(Long protocolo_id, Long regiao_id){

        return app.getDaoSession().getProtocoloSetorDao().queryBuilder().
                where(ProtocoloSetorDao.Properties.RegiaoId.eq(regiao_id),
                ProtocoloSetorDao.Properties.ProtocoloId.eq(protocolo_id)).unique();
    }

    public ProtocoloSetor findProtocoloSetorById(Long id){
        return app.getDaoSession().getProtocoloSetorDao().queryBuilder()
                .where(ProtocoloSetorDao.Properties.Id.eq(id)).unique();
    }

    public ProtocoloSetor addProtocoloSetor(Long id_nf_protocolo_setor, Long regiao_id, Long protocolo_id, int qtd_volumes,
           int qtd_conferencia, int qtd_notas,Long id_usuario_conferencia, String data_conferencia){

        ProtocoloSetor protocoloSetor = findProtocoloSetorByIdProtocoloIdSetor(protocolo_id,regiao_id);

        if (protocoloSetor==null){

            protocoloSetor = new ProtocoloSetor(id_nf_protocolo_setor,regiao_id,protocolo_id,qtd_volumes,qtd_conferencia,
                    qtd_notas, id_usuario_conferencia,data_conferencia,null,false);
            this.session.insert(protocoloSetor);


        } else {
            if (protocoloSetor.getProtocoloId() == 75377){
                //Log.d("Ponto Parada","Debug");
            }
            if (!protocoloSetor.getProtocolo().getEnviar()) {
                protocoloSetor.setQtdVolumes(qtd_volumes);
                protocoloSetor.setQtdConferencia(qtd_conferencia);
                protocoloSetor.setQtdNotas(qtd_notas);
                protocoloSetor.setIdUsuarioConferencia(id_usuario_conferencia);
                protocoloSetor.setDataConferencia(data_conferencia);

                this.session.update(protocoloSetor);
            }
        }
        return protocoloSetor;
    }

    public List<ProtocoloSetor> findProtocoloSetorByDataExpedicao(Date data_expedicao, Long idSetor){

        String data = Util.getDateFormatYMD(data_expedicao);

        QueryBuilder query = app.getDaoSession().getProtocoloSetorDao().queryBuilder();

        //query.LOG_SQL = true;
        //query.LOG_VALUES = true;
        if (idSetor>0)
            query.where(ProtocoloSetorDao.Properties.RegiaoId.eq(idSetor));

        query.join(ProtocoloSetorDao.Properties.ProtocoloId,Protocolo.class)
                .where(ProtocoloDao.Properties.DataExpedicao.eq(data));

        List<ProtocoloSetor> protocolos = query.orderAsc(ProtocoloSetorDao.Properties.ProtocoloId).list();

        //Log.d("Quantidade",String.valueOf(protocolos.size()));

        return protocolos;
    }


    public List<ProtocoloSetor> findProtocoloSetorByDataExpedicaoStatus(Date data_expedicao, boolean status, Long idSetor){

        String data = Util.getDateFormatYMD(data_expedicao);

        QueryBuilder query = app.getDaoSession().getProtocoloSetorDao().queryBuilder();

        //query.LOG_SQL = true;
        //query.LOG_VALUES = true;

        if (idSetor>0)
            query.where(ProtocoloSetorDao.Properties.RegiaoId.eq(idSetor));

        query.join(ProtocoloSetorDao.Properties.ProtocoloId,Protocolo.class).
                where(ProtocoloDao.Properties.DataExpedicao.eq(data),
                        ProtocoloDao.Properties.Status.eq(status));

        List<ProtocoloSetor> protocolos = query.orderAsc(ProtocoloSetorDao.Properties.ProtocoloId).list();

        //Log.d("Quantidade",String.valueOf(protocolos.size()));

        return protocolos;
    }

    public List<ProtocoloSetor> findProtocoloSetorByDataExpedicaoStatusOk(Date data_expedicao, Long idSetor){

        String data = Util.getDateFormatYMD(data_expedicao);

        QueryBuilder query = app.getDaoSession().getProtocoloSetorDao().queryBuilder();

        //query.LOG_SQL = true;
        //query.LOG_VALUES = true;
        if (idSetor>0)
            query.where(ProtocoloSetorDao.Properties.RegiaoId.eq(idSetor));

        query.join(ProtocoloSetorDao.Properties.ProtocoloId,Protocolo.class)
                .where(ProtocoloDao.Properties.DataExpedicao.eq(data),
                        ProtocoloDao.Properties.Status.eq(true))
                .where( new WhereCondition.StringCondition("T.qtd_volumes = T.qtd_conferencia"));


        List<ProtocoloSetor> protocolos = query.orderAsc(ProtocoloSetorDao.Properties.ProtocoloId).list();

        //Log.d("Quantidade",String.valueOf(protocolos.size()));

        return protocolos;
    }

    public List<ProtocoloSetor> findProtocoloSetorByDataExpedicaoStatusAlert(Date data_expedicao, Long idSetor){

        String data = Util.getDateFormatYMD(data_expedicao);

        QueryBuilder query = app.getDaoSession().getProtocoloSetorDao()
                .queryBuilder();

        if (idSetor>0)
            query.where(ProtocoloSetorDao.Properties.RegiaoId.eq(idSetor));

        query.join(ProtocoloSetorDao.Properties.ProtocoloId,Protocolo.class).
                where(ProtocoloDao.Properties.DataExpedicao.eq(data),
                        ProtocoloDao.Properties.Status.eq(true))
                .where( new WhereCondition.StringCondition("T.qtd_volumes != T.qtd_conferencia"));


        List<ProtocoloSetor> protocolos = query.orderAsc(ProtocoloSetorDao.Properties.ProtocoloId).list();

        //Log.d("Quantidade",String.valueOf(protocolos.size()));

        return protocolos;
    }


    public List<ProtocoloSetor> findProtocoloSetorByIdProtocolo(Long id_protocolo){


        QueryBuilder query = app.getDaoSession().getProtocoloSetorDao().queryBuilder();

        //query.LOG_SQL = true;
        //query.LOG_VALUES = true;

        query.where(ProtocoloSetorDao.Properties.ProtocoloId.eq(id_protocolo));

        List<ProtocoloSetor> protocolos = query.orderAsc(ProtocoloSetorDao.Properties.ProtocoloId).list();

        //Log.d("Quantidade",String.valueOf(protocolos.size()));

        return protocolos;
    }


    public ProtocoloRemetente findProtocoloRemetenteByProtocoloSetorIdRemetenteId(
            Long protocoloSetorId, Long remetenteId
    ){

        return this.session.getProtocoloRemetenteDao().queryBuilder()
                .where(ProtocoloRemetenteDao.Properties.ProtocoloSetorId.eq(protocoloSetorId),
                        ProtocoloRemetenteDao.Properties.RemetenteId.eq(remetenteId)).unique();

    }

    public ProtocoloRemetente addProtocoloRemetente(Long id, Long protocoloSetorId,Long remetenteId,
                                                    int qtdNotas, int qtdVolumes)
    {
        ProtocoloRemetente remetente;

        remetente = findProtocoloRemetenteByProtocoloSetorIdRemetenteId(protocoloSetorId,remetenteId);

        if (remetente==null) {
            remetente = new ProtocoloRemetente(id, protocoloSetorId,remetenteId,qtdNotas,
                    qtdVolumes,qtdVolumes,0,null,false);
            //Log.d("Aviso:", "Incluindo Remetente");
            this.session.insert(remetente);
        }

        return remetente;

    }

    public List<ProtocoloRemetente> findProtocoloRemetenteByIdSetor(Long id_protocolo_setor){

        return this.session.getProtocoloRemetenteDao().queryBuilder()
                .where(ProtocoloRemetenteDao.Properties.ProtocoloSetorId.eq(id_protocolo_setor))
                .orderAsc(ProtocoloRemetenteDao.Properties.Id).list();

    }


    public Pessoas findPessoasByCnpjCpf(String cnpjCpf){

        return this.session.getPessoasDao().queryBuilder()
                .where(PessoasDao.Properties.CnpjCpf.eq(cnpjCpf.trim())).unique();

    }

    public Pessoas addPessoa(Long idPessoa, String nome, String cnpjCpf, String endereco,
                             String numero, String bairro, Long idCidade, String cep,
                             String telefone, String latitude, String longitude){

        Pessoas pessoa = findPessoasByCnpjCpf(cnpjCpf);

        if (pessoa == null){
            pessoa = new Pessoas();

            pessoa.setId(idPessoa);
            pessoa.setNome(nome);
            pessoa.setCnpjCpf(cnpjCpf);
            pessoa.setEndereco(endereco);
            pessoa.setNumero(numero);
            pessoa.setBairro(bairro);
            pessoa.setIdCidade(idCidade);
            pessoa.setCep(cep);
            pessoa.setTelefone(telefone);
            pessoa.setLatitude(latitude);
            pessoa.setLongitude(longitude);
            this.session.insert(pessoa);
        }  else {
            if (!pessoa.getEndereco().equals(endereco)) {
                pessoa.setEndereco(endereco);
                pessoa.setNumero(numero);
                pessoa.setBairro(bairro);
                pessoa.setIdCidade(idCidade);
                pessoa.setCep(cep);
                pessoa.setTelefone(telefone);
                pessoa.setLatitude(latitude);
                pessoa.setLongitude(longitude);

                this.session.update(pessoa);
            }
        }
        return pessoa;
    }


    public Motoristas findMotoristaByCnpjCpf(String cnpjCpf){

        return this.session.getMotoristasDao().queryBuilder()
                .where(MotoristasDao.Properties.CnpjCpf.eq(cnpjCpf.trim())).unique();

    }

    public Motoristas addMotorista( Long idMotorista, String nome, String cnpjCpf, String endereco,
                              String numero, String bairro, Long idCidade, String cep,
                              String telefone, String latitude, String longitude){

        Motoristas motorista = findMotoristaByCnpjCpf(cnpjCpf);

        if (motorista == null){

            motorista = new Motoristas();
            motorista.setId(idMotorista);
            motorista.setNome(nome);
            motorista.setCnpjCpf(cnpjCpf);
            motorista.setEndereco(endereco);
            motorista.setNumero(numero);
            motorista.setBairro(bairro);
            motorista.setIdCidade(idCidade);
            motorista.setCep(cep);
            motorista.setTelefone(telefone);
            motorista.setLatitude(latitude);
            motorista.setLongitude(longitude);
            this.session.insert(motorista);
        }  else {
            if (!motorista.getEndereco().equals(endereco)) {
                motorista.setEndereco(endereco);
                motorista.setNumero(numero);
                motorista.setBairro(bairro);
                motorista.setIdCidade(idCidade);
                motorista.setCep(cep);
                motorista.setTelefone(telefone);
                motorista.setLatitude(latitude);
                motorista.setLongitude(longitude);

                this.session.update(motorista);
            }
        }
        return motorista;
    }

    public Cidades findCidadeById(Long idCidade){

        return this.session.getCidadesDao().queryBuilder()
                .where(CidadesDao.Properties.Id.eq(idCidade)).unique();

    }

    public Cidades addCidade(Long id, String nomeCidade, String uf, String codIbge,
                             Double latitude, Double longitude, Long idRegiao){

        Cidades cidade = findCidadeById(id);

        if (cidade==null){
            cidade = new Cidades(id, nomeCidade,uf,codIbge,latitude,longitude,idRegiao);
            this.session.insert(cidade);
        }

        return cidade;
    }

    public RegiaoCidades addRegiaoCidades(Long idCidade, Long idRegiao){
        RegiaoCidades regiaoCidades= app.getDaoSession().getRegiaoCidadesDao()
                .queryBuilder()
                .where(RegiaoCidadesDao.Properties.IdCidade.eq(idCidade))
                .where(RegiaoCidadesDao.Properties.IdRegiao.eq(idRegiao))
                .unique();

        if (regiaoCidades == null){
            regiaoCidades = new RegiaoCidades();
            regiaoCidades.setIdCidade(idCidade);
            regiaoCidades.setIdRegiao(idRegiao);
            app.getDaoSession().insert(regiaoCidades);
        }

        return regiaoCidades;
    }


    public NotasFiscais findNotaFiscalByChaveIdProtocolo(String chaveNfe, Long idProtocolo){

        return this.session.getNotasFiscaisDao().queryBuilder()
                .where(NotasFiscaisDao.Properties.ChaveNfe.eq(chaveNfe))
                .where(NotasFiscaisDao.Properties.IdNfProtocolo.eq(idProtocolo)).unique();
    }



    public NotasFiscais addNotaFiscal( String chaveNfe, String serie, String numero,
                                      Long remetenteId, Long destinatarioId, Double valorNf, Double peso,
                                      Double volumes, Double volumeCubico, Long idRomaneio, Long idNfProtocolo,
                                      Long idNotaFiscalImp, Long idOcorrencia){

        NotasFiscais notaFiscal = findNotaFiscalByChaveIdProtocolo(chaveNfe,idNfProtocolo);

        if (notaFiscal == null){
            notaFiscal = new NotasFiscais(null,chaveNfe,serie,numero,remetenteId,destinatarioId,
                    valorNf,peso,volumes,volumeCubico,idRomaneio,idNfProtocolo,idNotaFiscalImp,
                    null,0,false,idOcorrencia);
            this.session.insert(notaFiscal);
            ProtocoloSetor protocolo = notaFiscal.getProtocolo().getProtocoloSetores().get(0);
        }

        return notaFiscal;
    }

    public Veiculos findVeiculoByPlaca(String placa){

        return this.session.getVeiculosDao().queryBuilder()
                .where(VeiculosDao.Properties.Placa_veiculo.eq(placa)).unique();
    }

    public Veiculos addVeiculo(String placa_veiculo, String descricao){

        Veiculos veiculo = findVeiculoByPlaca(placa_veiculo);

        if (veiculo==null){
            veiculo = new Veiculos(null, placa_veiculo,descricao);
            this.session.insert(veiculo);
        }
        return veiculo;
    }

/*    public List<ProtocoloSetor> findProtocoloSetorByAll(String dataQuery){

        List<ProtocoloSetor> protocolos;
        return protocolos;
    }*/

}
