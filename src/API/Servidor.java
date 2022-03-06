package API;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Servidor {

	public static void main(String[] args) throws IOException {
		String msg_recebida; //lida do cliente
		String msg_enviada; //enviada ao cliente
		String assunto = "";
		String noticia = "";
		//DateTimeFormatter data_atual = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter hora_atual = DateTimeFormatter.ofPattern("HH:mm");
		try (// cria socket de comunicação com os clientes na porta 8657
		ServerSocket servidor = new ServerSocket(8657)) {
			// espera msg de algum cliente e trata
			while(true) {
			// espera conexão de algum cliente
			System.out.println("Esperando cliente se conectar...");
			Socket conexao = servidor.accept();
			System.out.println("Cliente conectado!");
			// cria streams de entrada e saida com o cliente que chegou
			BufferedReader entrada_cliente = new BufferedReader(new
			InputStreamReader(conexao.getInputStream()));
			DataOutputStream saida_cliente = new DataOutputStream(conexao.getOutputStream());
			// lê mensagem do cliente
			msg_recebida = entrada_cliente.readLine();
			
			while (msg_recebida != null && !(msg_recebida.trim().equals("")) &&
			!(msg_recebida.startsWith("fim"))) {
				
			switch(msg_recebida) {
			
			case "1": 
				assunto = "Economia";
				noticia = "Ibovespa sobe 6%";
				break;
			
			case "2":
				assunto = "Entretenimento";
				noticia = "Netflix trabalhando em nova série";
				break;
			
			case "3":
				assunto = "Tecnologia";
				noticia = "Programação auxilia no desenvolvimento de vacinas";
			break;
			
			}
			
			// mostra mensagem recebida na console
			System.out.println("Cliente: " + msg_recebida);
			// monta retorno para o cliente
			msg_enviada = assunto + ": " + noticia + " " + hora_atual.format(LocalDateTime.now()) + '\n';
			// envia retorno para o cliente
			saida_cliente.writeBytes(msg_enviada);
			// lê mensagem do cliente
			msg_recebida = entrada_cliente.readLine();
			}
			System.out.println("Cliente desconectado!");
			conexao.close();
			}
		}

	}

}
