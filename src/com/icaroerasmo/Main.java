package com.icaroerasmo;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Tupla> tuplas = new ArrayList<>();
		
		TuplaBuilder tupla = new TuplaBuilder();
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Sim").
				add("tem pelo", "Sim").
				add("eh preto", "Sim").
				add("tem 4 patas", "Sim").
				add("eh cachorro", "Sim").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Não").
				add("tem pelo", "Sim").
				add("eh preto", "Não").
				add("tem 4 patas", "Sim").
				add("eh cachorro", "Sim").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Não").
				add("tem pelo", "Não").
				add("eh preto", "Não").
				add("tem 4 patas", "Sim").
				add("eh cachorro", "Sim").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Não").
				add("tem pelo", "Sim").
				add("eh preto", "Sim").
				add("tem 4 patas", "Sim").
				add("eh cachorro", "Sim").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Não").
				add("tem pelo", "Sim").
				add("eh preto", "Não").
				add("tem 4 patas", "Sim").
				add("eh cachorro", "Sim").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Não").
				add("come vegetais", "Não").
				add("tem pelo", "Sim").
				add("eh preto", "Não").
				add("tem 4 patas", "Não").
				add("eh cachorro", "Não").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Não").
				add("tem pelo", "Sim").
				add("eh preto", "Não").
				add("tem 4 patas", "Não").
				add("eh cachorro", "Não").build()
				);
		
		tuplas.add(
				tupla.add("come carne", "Sim").
				add("come vegetais", "Não").
				add("tem pelo", "Não").
				add("eh preto", "Não").
				add("tem 4 patas", "Não").
				add("eh cachorro", "Não").build()
				);
		
		Database db = new Database("eh cachorro");
		db.setTuplas(tuplas);
		
		System.out.println(db.calculoGanho());

	}

}
