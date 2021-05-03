package com.icaroerasmo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static Double log2(Double valor) {
		return (double) (Math.log(valor) / Math.log(2));
	}

	public static Database lerCsv(String arquivoCSV) {

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
