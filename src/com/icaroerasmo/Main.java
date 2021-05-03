package com.icaroerasmo;

public class Main {

	public static void main(String[] args) {
		Database db = Database.carregaDatabase("/home/icaroerasmo/Documentos/database2.csv");
		var ganhos = db.calcularGanho();
		for(String coluna : ganhos.keySet()) {
			System.out.println(coluna + ": "+ganhos.get(coluna));
		}	
	}
}
