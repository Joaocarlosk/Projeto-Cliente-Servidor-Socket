
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LEAD
 */
public class Servidor {

	private String resultado;
	private String data;
	private Map<String, String> mapa;

	public void iniciarServico() {

		//resultado = "06-55-13-47-22-19";
		//data = "08/10";
		mapa = new HashMap<String, String>();
		mapa.put("08/10", "06-55-13-47-22-19");
		mapa.put("15/10", "10-34-22-01-43-17");
		mapa.put("22/10", "02-31-42-21-12-49");
		mapa.put("29/10", "54-15-14-32-24-11");
		mapa.put("05/11", "01-35-19-32-25-44");
		
		try {

			ServerSocket servidor = new ServerSocket(8898);
			while (true) {

				System.out.println("Aguardando conexão...");
				Socket cliente = servidor.accept();
				
				DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());
				data = dataInputStream.readUTF();
				
				resultado = mapa.get(data);
				
				DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());

				System.out.println("Enviando "+ resultado + " para o cliente " + cliente.getInetAddress());
				dataOutputStream.writeUTF(resultado);
			}

		} catch (IOException exception) {
			System.err.println(exception.getMessage());
		}

	}

	public static void main(String[] args) {
		Servidor servidorDeResultados = new Servidor();
		servidorDeResultados.iniciarServico();
	}

}
