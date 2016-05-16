package br.com.simplecrud.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.simplecrud.dao.PensaoDao;
import br.com.simplecrud.model.Pensao;
import br.com.simplecrud.util.FacesMessages;


@ManagedBean(name="gestaoPensoesBean")
@SessionScoped
public class GestaoPensoesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PensaoDao dao;
	
	private FacesMessages messages;
	
	private List<Pensao> todasPensoes;
	
	private Pensao pensaoSelected;
	
	// essa variavel vai guardar a pensao que vai ser cadastrada. por isso "edition" ou editada em caso de update
	private Pensao pensaoEdition;
	
	
	public void consultar() {
		todasPensoes = dao.consultarTodas();
	}
	
	public GestaoPensoesBean(){
		dao = new PensaoDao();
		messages = new FacesMessages();
		
	}
	
	
	public void selecionarRow(SelectEvent event){
		//System.out.println("teste");
	}
	
	
	public void salvar(){
		boolean salve = dao.salvar(pensaoEdition);
		if ( salve ){
			messages.info("Pensão salva com sucesso! Lamento!");
			
			//Atualizando a lista de pensões, pois agora entrou mais uma nova.
			todasPensoes = dao.consultarTodas();
			
			//faz a atualizacao direta dos componentes de id (msgs, e empresas-table) do FORMULARIO frm
			RequestContext.getCurrentInstance().update(Arrays.asList("frm:msgs", "frm:pensoes-table")	);
		}
	}
	
	
	public void excluir(){
		dao.excluir(pensaoSelected.getId());
		pensaoSelected = null; //Des-seleciona a linha que já foi excluída
		consultar();
		messages.info("Pensão excluída com sucesso! Que alívio!");
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

	public Pensao getPensaoSelected() {
		return pensaoSelected;
	}

	public void setPensaoSelected(Pensao pensaoSelected) {
		this.pensaoSelected = pensaoSelected;
	}
	
	
	
}

