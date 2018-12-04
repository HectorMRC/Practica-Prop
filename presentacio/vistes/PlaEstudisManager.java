package presentacio.vistes;

import domini.ControladorDomini;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PlaEstudisManager {
	
	@FXML private TextField nom_id, autor_id, rang1, rang2, rang3, rang4;
	@FXML private GridPane lectiu_container;
	@FXML private ListView<String> assignatures;
	@FXML private Label title;
	
	////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////  FXML ///////////////////////////////////////
	
	@FXML
	public void onCreateAssignatura() {
		Main.getInstance().newWindows("Assignatura_view.fxml", "Assignatura", 590, 720);
	}
	
	@FXML
	public void apply() {
		Main.getInstance().showWarning("Welcome to Carthage", "Penis");
	}
}
