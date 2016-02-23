package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.Connectionjdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Contato;

public class ContatoDAO {
	private Connection connection;
	
		public ContatoDAO(){
			this.connection  = new ConnectionFactory().getConnection();
		}
		
		public void adiciona (Contato contato){
			String sql = "insert into contatos" +
					"(nome, email, endereco, dataNascimento)" + 
					"values (?,?,?,?)";
			try {
				//prepare statement para inser��o
				PreparedStatement stmt = connection.prepareStatement(sql);
				//seta os valores
				stmt.setString(1, contato.getNome());
				stmt.setString(2, contato.getEmail());
				stmt.setString(3,contato.getEmail());
				stmt.setDate(4, new Date( 
						contato.getDataNascimento().getTimeInMillis())); 
				
				stmt.execute();
				stmt.close();
			} catch (SQLException e){
				throw new RuntimeException(e);
			}
		}
		
		public List<Contato> getLista(){
			try{
				List<Contato> contatos = new ArrayList<Contato>();
				PreparedStatement stmt = this.connection.prepareStatement("Select * from contatos");
				ResultSet rs = stmt.executeQuery();
				
			while (rs.next()){
				//Criando objeto contato
				Contato contato  = new Contato();
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("Nome"));
				contato.setEmail(rs.getString("Email"));
				contato.setEndereoco(rs.getString("endereco"));
				
				//mostrar Data atraves do Calendar
				Calendar data = Calendar.getInstance(); 
				data.setTime(rs.getDate("dataNascimento"));
				contato.setDataNascimento(data);
				
				// adicionar objetos na lista
				contatos.add(contato);
			}
			rs.close();
			stmt.close();
			return contatos;
			} catch (SQLException e){
				throw new RuntimeException(e);
			}
			
		}
		
	
}
