package com.rp.domain.sistema;

import java.util.Date;
import java.util.List;

import com.rp.domain.Cliente;
import com.rp.domain.Sexo;
import com.rp.domain.repository.ClienteRepository;

public class Sistema {
	
	private static final String URL="jdbc:mysql://localhost:3306/alfa";
	private static final String USUARIO = "root";
	private static final String SENHA = "root";
	
	public static void main(String[] args) {
		
		Cliente c = new Cliente();
		c.setNome("Rodrigo Postai");
		c.setCpf("09420939204");
		c.setSexo(Sexo.Masculino);
		c.setDataNascimento(new Date(1981, 1, 22));
		
		ClienteRepository repository = new ClienteRepository(URL, USUARIO, SENHA);
		int result = repository.inserirCliente(c);
		if (result > 0) {
			System.out.println("Cliente incluído com sucesso");
		}
		
		boolean sucesso = repository.removerCliente(c);
		if (sucesso) {
			System.out.println("Cliente excluído com sucesso!");
		}
		
		List<Cliente> clientes = repository.consultarCliente("Rodrigo");
		System.out.println(clientes);
		
		Cliente c1 = clientes.get(0);
		c1.setNome("Rodrigo M. Postai");
		repository.updateCliente(c1);
				
	}

}
