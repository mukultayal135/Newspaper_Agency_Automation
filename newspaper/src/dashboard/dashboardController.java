package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class dashboardController {
	 @FXML
	    private Circle papercir;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void billgeneration(MouseEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("bill/billgenerator.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
    		
			
			   Scene scene1=(Scene)papercir.getScene();
			   scene1.getWindow().hide();
			 

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void customer(MouseEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customer/customerregi.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
    		
			Scene scene1=(Scene)papercir.getScene();
			scene1.getWindow().hide();
			 

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void hawker(MouseEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("hawkermanager/hawker.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
    		
			
			   Scene scene1=(Scene)papercir.getScene();
			   scene1.getWindow().hide();
			 

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void paper(MouseEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("MasterPaper/MasterPaper.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
    		
			
			   Scene scene1=(Scene)papercir.getScene();
			   scene1.getWindow().hide();
			 

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void paybill(MouseEvent event) {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("paybill/paybill.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
    		
			
			   Scene scene1=(Scene)papercir.getScene();
			   scene1.getWindow().hide();
			 

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {

    }
}
