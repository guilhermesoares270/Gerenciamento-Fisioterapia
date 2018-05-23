package view;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/personDetails.fxml"));
        AnchorPane personDetails = (AnchorPane) loader.load();

        leftPane.getChildren().addAll(personDetails);
        spd_control = loader.getController();	
        
        FXMLLoader loader_pes = new FXMLLoader();
        loader_pes.setLocation(getClass().getResource("/view/pesquisar.fxml"));
        AnchorPane pesquisar = (AnchorPane) loader_pes.load();

        rightPane.getChildren().addAll(pesquisar);
        
        pc_control = loader_pes.getController();
		
        pc_control.tv_tabela.addEventFilter(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>() {
            		public void handle(MouseEvent e) {
            			
            			if(!pc_control.tv_tabela.getItems().isEmpty() && pc_control.tv_tabela.getItems() != null) {

                			pc_control.setPessoa(pc_control.tv_tabela.getSelectionModel().getSelectedItem());
                			System.out.println("Bairro " + pc_control.getPessoa().getBairro());
                			pc_control.getPessoa().printData();

                			spd_control.setPersonInfo(pc_control.getPessoa());
                			
                			////////////////////////////////////
                			spd_control.setConsultasMarcadas();
                			///////////////////////////////////
  
                			System.out.println("mouse clicked on pesquisar");	
            			}else {
            				System.out.println("A tabela está vazia");
            			}
            		}
            	}
        );
        
		mudarValores.setOnAction((event) -> {
			spd_control.setNome("Valor Mudado");
		});
	}
}
