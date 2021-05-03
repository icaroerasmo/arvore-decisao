package com.icaroerasmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Database {
	
	private List<Tupla> tuplas;
	
	private String colunaRotulo;
	
	public Database(String colunaRotulo) {
		this.colunaRotulo = colunaRotulo;
		this.tuplas = new ArrayList<>();
	}

	public List<Tupla> getTuplas() {
		return tuplas;
	}

	public void setTuplas(List<Tupla> tuplas) {
		this.tuplas = tuplas;
	}
	
	private Map<String, Double> frequencias(List<Tupla> tuplas) {
		Map<String, Double> frequencias = new HashMap<>();
		
		Set<String> valoresUnicosColunaRotulo = valoresUnicosColuna(colunaRotulo);
		
		List<String> valoresColunaRotulo = tuplas.stream().
				map(t -> t.getAsString(colunaRotulo)).
				collect(Collectors.toList());
		
		for(String valor : valoresUnicosColunaRotulo) {
			Integer quantidade = valoresColunaRotulo.stream().
				filter(v -> v.equals(valor)).
				map(v -> 1).
				reduce((v,ac) -> v + ac).orElse(0);
			frequencias.put(valor, ((double)quantidade)/tuplas.size());
		}
		
		return frequencias;
	}

	private Set<String> valoresUnicosColuna(String coluna) {
		Set<String> valoresUnicosColunaRotulo = tuplas.stream().
				map(t -> t.getAsString(coluna)).
				collect(Collectors.toSet());
		return valoresUnicosColunaRotulo;
	}
	
	public Double calculaEntropia() {
		return this.calculaEntropia(tuplas);
	}
	
	private Double calculaEntropia(List<Tupla> tuplas) {
		
		Map<String, Double> frequencias = frequencias(tuplas);
		
		Double entropia = 0D;
		
		for(String frequencia : frequencias.keySet()) {
			var valor = frequencias.get(frequencia);
			
			entropia += -(valor != 0 ? valor * Util.log2(valor) : 0D);
		}
		
		return entropia;
	}
	
	public List<Tupla> filtraPorValorColuna(String colunaChave, String valor) {
		return this.tuplas.stream().
				filter(t -> t.getAsString(colunaChave).
						equals(valor)).collect(Collectors.toList());
	}
	
	private Double calculoGanho(String colunaAnalise) {
		
		var ganho = calculaEntropia();
		
		for(String valorUnicoSubset : valoresUnicosColuna(colunaAnalise)) {
			List<Tupla> subset = filtraPorValorColuna(colunaAnalise, valorUnicoSubset);
			Double valorUnico = ((double) subset.size()) / tuplas.size();
			ganho -= (valorUnico != null ? valorUnico : 0) * calculaEntropia(subset);
		}
		
		return ganho;
	}
	
	public Map<String, Double> calculoGanho() {
		
		List<String> chaves = new ArrayList<>(tuplas.get(0).getChaves());
		
		chaves.remove(colunaRotulo);
		
		Map<String, Double> ganhos = chaves.stream().collect(
				Collectors.
				toMap(c -> c, c -> calculoGanho(c)));
		
		return ganhos;
	}
}
