
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
*
* @author LEAD
*/
public class Cliente extends Application {
	
	private Label rotuloTitulo;
	private Separator separadorTitulo;
	private Label rotuloData;
	//private Label rotuloValorData;
	private TextField campoValorData;
	private Label rotuloResultado;
	private Button botaoConsultar;

	@Override
	public void start(Stage palco) throws Exception {
		VBox layoutJanela = new VBox(10);
		layoutJanela.setAlignment(Pos.CENTER);
		
		rotuloTitulo = new Label("Resultado Mega Sena");
		rotuloTitulo.setTextFill(Color.web("#0076a3"));
		rotuloTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		
		separadorTitulo = new Separator();
		
		GridPane layoutData = new GridPane();
		layoutData.setAlignment(Pos.CENTER);
		
		rotuloData = new Label("Data: ");
		rotuloData.setFont(Font.font(null, FontWeight.BOLD, 16));
		layoutData.add(rotuloData, 0, 0);
		
		//rotuloValorData = new Label();
		//rotuloValorData.setFont(Font.font(null, FontWeight.BOLD, 16));
		campoValorData = new TextField();
		layoutData.add(campoValorData, 1, 0); 
	
		rotuloResultado = new Label();
		rotuloResultado.setFont(Font.font(null, FontWeight.BOLD, 40));
		
		botaoConsultar = new Button("Consultar");
		botaoConsultar.setFont(Font.font(null, FontWeight.BOLD, 20));
		botaoConsultar.setPrefWidth(200);
		
		EventHandler<ActionEvent> eventoBotaoConsultar = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evento) {
				requisitarResultado();
			}
		};
		
		botaoConsultar.setOnAction(eventoBotaoConsultar);
		
		layoutJanela.getChildren().addAll(rotuloTitulo, separadorTitulo, layoutData, rotuloResultado, botaoConsultar);
		
		Scene cena = new Scene(layoutJanela, 400, 300);
		palco.setScene(cena);
		palco.show();
	}
	
	private void requisitarResultado() {
		try {
			Socket servidor = new Socket("127.0.0.1", 8898);
			
			String dataSorteio = campoValorData.getText();
			
			DataOutputStream dataOutputStream = new DataOutputStream(servidor.getOutputStream());
			dataOutputStream.writeUTF(dataSorteio);	
			
			DataInputStream dataInputStream = new DataInputStream(servidor.getInputStream());
			String resposta = dataInputStream.readUTF();
			
			exibirResultado(resposta);
			
			servidor.close();

		} catch (IOException exception) {
			System.err.println(exception.getMessage());
		}
	}
	
	private void exibirResultado(String resposta) {
		
		//String dados[] = resposta.split(",");
		
		//rotuloResultado.setText(dados[0]);
		//rotuloValorData.setText(dados[1]);
		rotuloResultado.setText(resposta);
	}
	
	public static void main(String[] args) {
		launch();
	}

}
