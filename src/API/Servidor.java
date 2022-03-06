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
		String msg_recebida;
		String msg_enviada;
		String assunto = "";
		String noticia = "";
		DateTimeFormatter hora_atual = DateTimeFormatter.ofPattern("HH:mm");
		try (ServerSocket servidor = new ServerSocket(8657)) {
			while(true) {
			System.out.println("Esperando cliente se conectar...");
			Socket conexao = servidor.accept();
			System.out.println("Cliente conectado!");
			BufferedReader entrada_cliente = new BufferedReader(new
			InputStreamReader(conexao.getInputStream()));
			DataOutputStream saida_cliente = new DataOutputStream(conexao.getOutputStream());
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
			
			default:
				assunto = "Opção inválida";
				noticia = "Tente novamente!";
			
			}
			
			System.out.println("Cliente: " + msg_recebida);
			msg_enviada = assunto + ": " + noticia + " " + hora_atual.format(LocalDateTime.now()) + '\n';
			saida_cliente.writeBytes(msg_enviada);
			msg_recebida = entrada_cliente.readLine();
			}
			System.out.println("Cliente desconectado!");
			conexao.close();
			}
		}

	}

}
