package com.icaroerasmo;

public class Main {

	public static void main(String[] args) {
		Database db = Util.lerCsv("/home/icaroerasmo/Documentos/database.csv");
		db.calculoGanho();
	}

}
