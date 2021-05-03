package com.icaroerasmo;

import com.icaroerasmo.data.Database;

public class Main {

	public static void main(String[] args) {
		Database db = Database.carregaDatabase("./database.csv");
		var ganhos = db.calcularGanho();
		for(String coluna : ganhos.keySet()) {
			System.out.println(coluna + ": "+ganhos.get(coluna));
		}	
	}
}
