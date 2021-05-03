package com.icaroerasmo;

public class Main {

	public static void main(String[] args) {
		Database db = Util.lerCsv("/home/icaroerasmo/Documentos/database2.csv");
		var ganhos = db.calculoGanho();
		for(String coluna : ganhos.keySet()) {
			System.out.println(coluna + ": "+ganhos.get(coluna));
		}	
	}
}
