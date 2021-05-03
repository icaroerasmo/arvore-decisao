package com.icaroerasmo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Database {
	
	private List<Tupla> tuplas;
	
	private String colunaRotulo;
	
	private Database() {}
	
	private Database(String colunaRotulo) {
		this.colunaRotulo = colunaRotulo;
		this.tuplas = new ArrayList<>();
	}

	public List<Tupla> getTuplas() {
		return tuplas;
	}

	private void setTuplas(List<Tupla> tuplas) {
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
	
	private Double calculaEntropia() {
		return this.calculaEntropia(tuplas);
	}
	
	private Double calculaEntropia(List<Tupla> tuplas) {
		
		Map<String, Double> frequencias = frequencias(tuplas);
		
		Double entropia = 0D;
		
		for(String chavesFrequencia : frequencias.keySet()) {
			var frequencia = frequencias.get(chavesFrequencia);
			entropia += -(frequencia != 0 ? frequencia * Util.log2(frequencia) : 0D);
		}
		
		return entropia;
	}
	
	private List<Tupla> filtraPorValorColuna(String colunaChave, String valor) {
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
	
	public Map<String, Double> calcularGanho() {
		
		List<String> chaves = new ArrayList<>(tuplas.get(0).getChaves());
		
		chaves.remove(colunaRotulo);
		
		Map<String, Double> ganhos = chaves.stream().collect(
				Collectors.
				toMap(c -> c, c -> calculoGanho(c)));
		
		return ganhos;
	}
	
	public static Database carregaDatabase(String arquivoCSV) {

		List<String> labels = new ArrayList<>();
		List<Tupla> tuplas = new ArrayList<>();
		TuplaBuilder tupla = new TuplaBuilder();

		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ",";
		int index = 0;
		try {

			br = new BufferedReader(new FileReader(arquivoCSV));
			while ((linha = br.readLine()) != null) {

				String[] values = linha.split(csvDivisor);

				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					if (index < 1) {
						labels.add(value);
					} else {
						try {
							Double numericValue = Double.parseDouble(value);
							tupla.add(labels.get(i), numericValue);
						} catch(NumberFormatException e) {
							tupla.add(labels.get(i), value);
						}
					}
				}
				
				if(index > 0) {
					tuplas.add(tupla.build());
				}

				index++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Database db = new Database(labels.get(labels.size()-1));
		db.setTuplas(tuplas);
		return db;
	}
}
