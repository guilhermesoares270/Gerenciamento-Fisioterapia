package view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

public class consultar_control {
	
	//Referencia ao controlador do showPersonDetails
	@FXML
	private showPersonDetails_control spd_control;
	
	//Referencia ao controlador do pesquisar
	@FXML
	private pesquisa_control pc_control;
	
	@FXML
	private Button mudarValores;
	
	@FXML
	private Pane leftPane;
	
	@FXML
	private Pane rightPane;
	
	@FXML
	private void initialize() throws IOException {	
		
		// Carrega a person overview.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/personDetails.fxml"));
        AnchorPane personDetails = (AnchorPane) loader.load();

        // Define a person overview no leftPane
        leftPane.getChildren().addAll(personDetails);
        //rootLayout.setCenter(personOverview);

        // Dá ao controlador acesso à the main app.
        spd_control = loader.getController();	
        
        
        //Carrega o pesquisar 
        FXMLLoader loader_pes = new FXMLLoader();
        loader_pes.setLocation(getClass().getResource("/view/pesquisar.fxml"));
        AnchorPane pesquisar = (AnchorPane) loader_pes.load();
        
        //define pesquisar no rightPane
        rightPane.getChildren().addAll(pesquisar);
        
        pc_control = loader_pes.getController();
		
        
		mudarValores.setOnAction((event) -> {
			//spd_control.setNome("TESTE");
			spd_control.setNome("Valor Mudado");
		});
	}
}
