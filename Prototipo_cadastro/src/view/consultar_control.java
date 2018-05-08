package view;

import java.io.IOException;

import application.Main;
import javafx.beans.property.ObjectProperty;
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
		
        /////////////revisar
        //pc_control.tv_tabela.getOnMouseClicked();
        //Teste para passar os valores para outra classe//
        //spd_control.getDetails(pc_control.getPessoa());
        //spd_control.setPersonInfo();
        //spd_control.setDetails();
        
        pc_control.tv_tabela.addEventFilter(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>() {
            		public void handle(MouseEvent e) {
            			
            			if(!pc_control.tv_tabela.getItems().isEmpty() && pc_control.tv_tabela.getItems() != null) {
            				
            				//Pega o elemento selecionado e manda para o showPersonDetails
                			pc_control.setPessoa(pc_control.tv_tabela.getSelectionModel().getSelectedItem());
                			System.out.println("Bairro " + pc_control.getPessoa().getBairro());
                			pc_control.getPessoa().printData();
                			
                			//spd_control.setNome("O nome foi mudado!!!");
                			
                			//Informações básicas está funcionando
                			//spd_control.basicInfo(pc_control.getPessoa());
                			
                			spd_control.setPersonInfo(pc_control.getPessoa());
                			
                			/////////////////////////
                			
                			System.out.println("mouse clicked on pesquisar");
                			
                			
            			}else {
            				System.out.println("A tabela está vazia");
            			}
            			
            		}
            }
        );
        
		mudarValores.setOnAction((event) -> {
			//spd_control.setNome("TESTE");
			spd_control.setNome("Valor Mudado");
		});
	}
}
