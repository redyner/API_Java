package API;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente extends Thread{
	
	public Cliente() 
	{
		start();
	}


	public void run() 
	{
		System.out.println("Cliente Ativo!");
		String msg_digitada;
		String msg_recebida;
		String nome_cliente;
		String orientacoes;

		try 
		{
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Informe o nome do cliente:");
			nome_cliente = teclado.readLine();
			Socket cliente = new Socket("localhost", 8657);
			System.out.println(nome_cliente + " entrou no chat!");
			orientacoes = "Informe o assunto sobre o qual deseja enviar ou receber notícias:\n1 - Economia\n2 - Entretenimento\n3 - Tecnologia";
			System.out.println(orientacoes);
			DataOutputStream saida_servidor = new DataOutputStream(cliente.getOutputStream());
			BufferedReader entrada_servidor = new BufferedReader(new
			InputStreamReader(cliente.getInputStream()));
			while (true) {
			msg_digitada = teclado.readLine();
			if (msg_digitada.startsWith("fim") == true)
			break;
			saida_servidor.writeBytes(msg_digitada + '\n');
			msg_recebida = entrada_servidor.readLine();
			System.out.println("Servidor: " + msg_recebida);
			}
			cliente.close();
			System.out.println(nome_cliente + " saiu do chat!");
		} 	catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}

