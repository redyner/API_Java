package API;

public class Teste extends Thread{
	String valor;

	Teste(String valor){
		this.valor = valor;
		start();
	}

	public void run() {
		for(int i = 0; i<=10;i++ ) 
		{
			System.out.println(valor + i);
		}

	}

}
