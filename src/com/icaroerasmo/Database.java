package com.icaroerasmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	
	private Map<String, Double> frequencias(List<Tupla> tuplas, String coluna) {
		Map<String, Double> frequencias = new HashMap<>();
		
		Set<String> valoresUnicosColunaRotulo = valoresUnicosColuna(coluna);
		
		List<String> valoresColunaRotulo = tuplas.stream().
				map(t -> t.getAsString(coluna)).
				collect(Collectors.toList());
		
		for(String valor : valoresUnicosColunaRotulo) {
			Integer quantidade = valoresColunaRotulo.stream().
				filter(v -> v.equals(valor)).
				map(v -> 1).
				reduce(
						(Integer _valor, Integer acumulador) -> _valor + acumulador
						).orElse(0);
			frequencias.put(valor, ((double)quantidade)/valoresColunaRotulo.size());
		}
		
		frequencias = 
				frequencias.entrySet().stream()
			    .sorted(Entry.<String, Double>comparingByValue().reversed())
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e1, LinkedHashMap::new));
		
		return frequencias;
	}

	private Set<String> valoresUnicosColuna(String coluna) {
		Set<String> valoresUnicosColunaRotulo = tuplas.stream().
				map(t -> t.getAsString(coluna)).
				collect(Collectors.toSet());
		return valoresUnicosColunaRotulo;
	}
	
	public Double calculaEntropia() {
		return this.calculaEntropia(tuplas, colunaRotulo);
	}
	
	private Double calculaEntropia(List<Tupla> tuplas, String colunaChave) {
		
		Map<String, Double> frequencias = frequencias(tuplas, colunaChave);
		
		Double entropia = null;
		
		for(String frequencia : frequencias.keySet()) {
			var valor = frequencias.get(frequencia);
			
			var tmp = (valor != 0 ? valor * Util.log2(valor) : 0D);
			
			if(entropia != null) {
				entropia -= tmp;
			} else {
				entropia = tmp;
			}
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
		
		for(String valorUnico : valoresUnicosColuna(colunaAnalise)) {
			List<Tupla> subset = filtraPorValorColuna(colunaAnalise, valorUnico);
			Map<String, Double> frequencias = frequencias(subset, colunaAnalise);
			ganho -= frequencias.get(valorUnico) * calculaEntropia(subset, colunaAnalise);
		}
		
		return ganho;
	}
	
	public Double calculoGanho() {
		var ganho = 0D;
		
		List<String> chaves = new ArrayList<>(tuplas.get(0).getChaves());
		chaves.remove(colunaRotulo);
		
		for(String coluna : chaves) {
			var _ganho = calculoGanho(coluna);
			ganho += _ganho;
		}
		
		return ganho;
	}
	
	
}
