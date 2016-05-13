package br.com.simplecrud.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;


import br.com.simplecrud.dao.PensaoDao;
import br.com.simplecrud.model.Pensao;


@ManagedBean(name="gestaoPensoesBean")
public class GestaoPensoesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private PensaoDao dao;
	
	private List<Pensao> todasPensoes;
	
	private long pensaoSelected;
	
	// essa variavel vai guardar a pensao que vai ser cadastrada. por isso "edition" ou editada em caso de update
	private Pensao pensaoEdition;
	
	public GestaoPensoesBean(){
		dao = new PensaoDao();
		pensaoEdition = new Pensao();
	}
	
	public void consultar() {
		todasPensoes = dao.consultarTodas();
	}
	
	
	public void salvar(){
		boolean salve = dao.salvar(pensaoEdition);
		if ( salve ){
			//Adicionar msg de salve na tela
			System.out.println("Salvo com sucesso!");
		}
	}

	public void prepararNovoCadastro() {
		this.pensaoEdition = new Pensao();
	}
	
	public List<Pensao> getTodasPensoes() {
		return todasPensoes;
	}

	public void setTodasPensoes(List<Pensao> todasPensoes) {
		this.todasPensoes = todasPensoes;
	}

	public Pensao getPensaoEdition() {
		return pensaoEdition;
	}

	public void setPensaoEdition(Pensao pensaoEdition) {
		this.pensaoEdition = pensaoEdition;
	}

	public long getPensaoSelected() {
		return pensaoSelected;
	}

	public void setPensaoSelected(long pensaoSelected) {
		this.pensaoSelected = pensaoSelected;
	}
	
	
	
}

