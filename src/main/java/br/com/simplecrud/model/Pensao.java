package br.com.simplecrud.model;

public class Pensao {
	private Long id;
	
	private String nomeMulher;
	
	private String nomeFilho;
	
	private float valorPensao;
	
	/** getters and setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeMulher() {
		return nomeMulher;
	}

	public void setNomeMulher(String nomeMulher) {
		this.nomeMulher = nomeMulher;
	}

	public String getNomeFilho() {
		return nomeFilho;
	}

	public void setNomeFilho(String nomeFilho) {
		this.nomeFilho = nomeFilho;
	}

	public float getValorPensao() {
		return valorPensao;
	}

	public void setValorPensao(float valorPensao) {
		this.valorPensao = valorPensao;
	}
}
