package com.icaroerasmo;

import java.util.Scanner;

import com.icaroerasmo.data.Database;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		Database db = Database.carregaDatabase("./database.csv");
		
		System.out.println("Devo jogar tênis?");
		
		while(!db.isFinished()) {
			var ganhos = db.calcularGanho();
			var maiorGanhoChave = "";
			var maiorGanhoValor = 0D;
			for(String coluna : ganhos.keySet()) {
				var valor = ganhos.get(coluna);
				if(ganhos.get(coluna) > maiorGanhoValor) {
					maiorGanhoChave = coluna;
					maiorGanhoValor = valor;
				}
			}
			
			System.out.println("Qual valor de "+maiorGanhoChave+"?");
			
			var resposta = s.nextLine();
			db.geraSubsetPorValorColuna(maiorGanhoChave, resposta);
		}
		
		System.out.println(db.resultado());
		
		s.close();
	}
}
