package com.rp.domain;

public enum Sexo {
	Masculino("M"), Feminino("F");
	
	private final String genero; 
	
	private Sexo(String genero) {
		this.genero = genero;
	}

	public String getGenero() {
		return genero;
	}
	
	public static Sexo getSexo(String genero) {
		Sexo[] values = values();
		for (Sexo s: values) {
			if (s.getGenero().equals(genero)) {
				return s;
			}
		}
		return null;
	}

}



 