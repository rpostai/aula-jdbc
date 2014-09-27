package com.rp.domain;

import java.util.List;

public class Funcionario {
	private double salario;

	public void aumentarSalario(int percentual) {
		this.salario += this.salario * (percentual / 100);
	}
}

class Sistema {
	public void aumentarSalarioFuncionarios(List<Funcionario> funcionarios) {
		for (Funcionario f : funcionarios) {
			f.aumentarSalario(10);
		}
	}
}
