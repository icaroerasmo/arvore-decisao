package com.icaroerasmo.data;

public class TuplaBuilder {
	
	private Tupla tupla;
	
	public TuplaBuilder(){
		tupla = new Tupla();
	}
	
	public TuplaBuilder add(String chave, Double valor) {
		tupla.put(chave, valor);
		return this;
	}
	
	public TuplaBuilder add(String chave, String valor) {
		tupla.put(chave, valor);
		return this;
	}
	
	public Tupla build() {
		Tupla t = tupla;
		this.tupla = new Tupla();
		return t;
	}
}
