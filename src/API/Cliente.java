package API;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Cliente extends Thread {

	BufferedReader entrada;

	static String dig_mensagem;

	public Cliente(BufferedReader i) {

		entrada = i;
		dig_mensagem = "Not Null";
	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		System.out.println("Cliente Ativo!");

		String msg_digitada; // mensagem digitada
		String msg_recebida; // mensagem recebida
		JTextField nome_cliente; // nome do cliente
		JTextField ip; // ip da conexão
		JTextField porta; // porta da conexão
		String[] data = { "Economia", "Entretenimento", "Tecnologia" }; // opções de assunto
		JList<String> assunto = new JList<String>(data); // lista de assuntos

		//Menu de opções
		JLabel lblMessage = new JLabel("Conectar");
		ip = new JTextField("localhost");
		porta = new JTextField("8657");
		nome_cliente = new JTextField("Cliente");
		Object[] texts = { lblMessage, nome_cliente, assunto };
        do{
            JOptionPane.showMessageDialog(null, texts);
            if(assunto.getSelectedValue()==null)
                JOptionPane.showMessageDialog(null, "Escolha um assunto!");
            if(nome_cliente.getText().replaceAll("\\s+","").isEmpty())
                JOptionPane.showMessageDialog(null, "Informe o nome do cliente!");
        }while(assunto.getSelectedValue()==null || nome_cliente.getText().isEmpty());
		
		// cria o stream do teclado
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

		// cria o socket de acesso ao server hostname na porta 8657
		Socket cliente = new Socket(ip.getText(), Integer.parseInt(porta.getText()));
		System.out.println(nome_cliente.getText() + " entrou no chat!");

		// Envia a Mensagem
		DataOutputStream saida_servidor = new DataOutputStream(cliente.getOutputStream());
		// Recebe a Mensagem
		BufferedReader entrada_servidor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

		//Envia nome para o servidor
		saida_servidor.writeBytes(nome_cliente.getText() + "\n");

		// lê resposta do servidor
		msg_recebida = entrada_servidor.readLine();

		System.out.println(msg_recebida);

		// envia o assunto para o servidor
		saida_servidor.writeBytes(assunto.getSelectedValue() + '\n');

		// Inicializa a Thread que recebe a mensagem 
		Thread t = new Cliente(entrada_servidor);

		t.start();

		while (true) {
			
			// le uma linha do teclado
			msg_digitada = teclado.readLine();

			// envia a linha para o servidor
			saida_servidor.writeBytes(msg_digitada + '\n');

			// testa se o chat deve ser finalizado
			if (msg_digitada.startsWith("sair") == true)
			break;
		}

		// lê uma linha do servidor
		msg_recebida = entrada_servidor.readLine();

		if (msg_recebida != null) {
			System.out.println(msg_recebida);
		}
		// fecha o cliente
		cliente.close();
		System.out.println(nome_cliente.getText() + " saiu do chat!");
	}

	public void run() {
		try {
			// Verifica se o Chat foi encerrado
			while (dig_mensagem != null && !(dig_mensagem.trim().equals("")) && !(dig_mensagem.startsWith("fim"))) {
				System.out.println(entrada.readLine());
			}
			System.exit(0);
		} catch (IOException e) {
			System.exit(0);

		}

	}

}
