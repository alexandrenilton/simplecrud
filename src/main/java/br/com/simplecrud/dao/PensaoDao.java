package br.com.simplecrud.dao;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.simplecrud.model.Pensao;

public class PensaoDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String COL_ID = "id";
	public static final String COL_NOME_MULHER = "nomemulher";
	public static final String COL_NOME_FILHO = "nomefilho";
	public static final String COL_VAL_PENSAO = "valorpensao";
	
	public static final String SQL_UPDATE_PENSAO = 
							" UPDATE PENSAO  "+
							" SET nomemulher = ? , "+
							"     nomefilho  = ? ,"+
							"     valorpensao = ? " +
						  " WHERE id = ? " ;
	
	public static final String SQL_CRIAR_PENSAO = 
					"INSERT INTO PENSAO VALUES ( NULL , ? , ?,  ? )";
	
	public static final String SQL_CONSULTAR_PENSAO = 
						"SELECT id, nomemulher, nomefilho, valorpensao " +
						 " FROM  pensao  "+
						 " WHERE id = ?" ;
	
	public static final String SQL_CONSULTAR_TODOS = 
			"SELECT id, nomemulher, nomefilho, valorpensao " +
			 " FROM  pensao  "+
			 " ORDER BY nomemulher, nomefilho " ;
	
	public static final String SQL_DELETA_PENSAO = " delete from  PENSAO "+ 
	                                        " WHERE  id = ? ";
	
	public static final String SQL_CADASTRAR_PENSAO = 
			" INSERT INTO pensao VALUES ( null, ?, ?, ? )";

	
	public boolean salvar(Pensao pensao){
		
		if ( pensao.getId() != null ){
			return this.atualizar(pensao);
		} else {
			return this.cadastrar(pensao);
		}
	}	
	
	
	public boolean excluir(Long id){
		try{
			Connection conn = Conexao.obterConexao();
			PreparedStatement pstmt = conn.prepareStatement(SQL_DELETA_PENSAO);
			
			pstmt.setLong(1, id);
			
			int result = pstmt.executeUpdate();
			
			return true;
			
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	public boolean cadastrar(Pensao pensao){	
		
		boolean existeErro = false;
		
		try{
			Connection conn = Conexao.obterConexao();
			PreparedStatement pstmt = conn.prepareStatement(SQL_CADASTRAR_PENSAO);
			
			conn.setAutoCommit(false);
			
			pstmt.setString(1, pensao.getNomeMulher());
			pstmt.setString(2, pensao.getNomeFilho());
			pstmt.setFloat(3, pensao.getValorPensao());
			
			int resultPessoa = pstmt.executeUpdate();
			
			if ( resultPessoa == 0){
				existeErro = true;
			}
			
			
			if ( existeErro){
				conn.rollback();
			}else{
				conn.commit();
			}
			Conexao.fecharTudo(null, pstmt, conn);
			
			if ( existeErro ){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Pensao> consultarTodas(){
		List<Pensao> pensoes = new ArrayList<Pensao>();
		
		try{
			Connection conn = Conexao.obterConexao();
			PreparedStatement pstmt = conn.prepareStatement(SQL_CONSULTAR_TODOS);
			ResultSet rs = pstmt.executeQuery();
			
			Pensao pensaoAtual;
			
			while (rs.next()){
				pensaoAtual = new Pensao();
				pensaoAtual.setId(new Long(rs.getLong(COL_ID)));
				pensaoAtual.setNomeMulher(rs.getString(COL_NOME_MULHER));
				pensaoAtual.setNomeFilho(rs.getString(COL_NOME_FILHO));
				pensaoAtual.setValorPensao(rs.getFloat(COL_VAL_PENSAO));
				
				/*Adicionando na colecao */
				pensoes.add(pensaoAtual);
			}
				
			Conexao.fecharTudo(rs, pstmt, conn);
			return pensoes;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Consulta uma pensao na base da dados
	 * @param id
	 * @return
	 */
	public Pensao consultar(Long id){
		Pensao pensao = null;
		try{
			Connection conn = Conexao.obterConexao();
			PreparedStatement pstmt = conn.prepareStatement(SQL_CONSULTAR_PENSAO);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()){
				if (rs.isFirst()){
					pensao = new Pensao();
				}
				pensao.setId(rs.getLong(COL_ID));
				pensao.setNomeMulher(rs.getString(COL_NOME_MULHER));
				pensao.setNomeFilho(rs.getString(COL_NOME_FILHO));
				pensao.setValorPensao(rs.getFloat(COL_VAL_PENSAO));
			}
			
			Conexao.fecharTudo(rs, pstmt, conn);
			return pensao;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Atualiza uma pensao na base de dados.
	 * @param pensao
	 * @return
	 */
	public boolean atualizar(Pensao pensao) {
		boolean existeErro = false;
		
		try{
			Connection conn = Conexao.obterConexao();
			PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE_PENSAO);
			
			conn.setAutoCommit(false);
	
			pstmt.setString(1, pensao.getNomeMulher());
			pstmt.setString(2, pensao.getNomeFilho());
			pstmt.setFloat(3, pensao.getValorPensao());
			pstmt.setLong(4, pensao.getId());
			
			int resultPessoa = pstmt.executeUpdate();
			
			if ( resultPessoa == 0){
				existeErro = true;
			}
			
			
			if ( existeErro){
				conn.rollback();
			}else{
				conn.commit();
			}
			Conexao.fecharTudo(null, pstmt, conn);
			
			if ( existeErro ){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Metodos main para testes paralelos.
	 * @param args
	 */
	public static void main(String[] args){
		PensaoDao dao = new PensaoDao();
		/** testando a consulta de uma pensao **/
		Pensao pensaoTemp = dao.consultar(new Long(1));
		dao.printPensao(pensaoTemp);
		
		
		/** testando a alteração dessa pensao **/
		pensaoTemp.setNomeMulher("Joicy Meireles");
		boolean resultado = dao.atualizar(pensaoTemp);
		
		if ( resultado ){
			dao.printPensao(pensaoTemp);
		}else {
			System.out.println("Erro!");
		}
		
		
		/** testando consultar todas as pensoes **/
		List<Pensao> pensoes = dao.consultarTodas();
		for (Pensao pensao : pensoes) {
			dao.printPensao(pensao);
		}
	}
	
	
	private void printPensao(Pensao pensao){
		System.out.println("IMPRIMINDO DADOS DA PENSÃO: ");
		System.out.println("Nome da golpista: "+pensao.getNomeMulher());
		System.out.println("Nome do bacuri: "+pensao.getNomeFilho());
		System.out.println("Valor do leite do menino(a): "+pensao.getValorPensao());
		System.out.println("================================================================");
	}
	
}
