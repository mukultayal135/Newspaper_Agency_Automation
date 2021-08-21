

package login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class loginpageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtuid;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private Button loginbtn;
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.ERROR);
    			
    			alert.setTitle("Error...");
    			
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    @FXML
    void check(ActionEvent event) {
    	String uid=txtuid.getText();
    	String pass=txtpassword.getText();
    	if(uid.equals("admin")&&pass.equals("12345678"))
    	{
    		try{
        		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/dashboard.fxml")); 
    			Scene scene = new Scene(root);
    			Stage stage=new Stage();
    			stage.setScene(scene);
    			stage.show();
    			 

    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		   Scene scene1=(Scene)loginbtn.getScene();
			   scene1.getWindow().hide();
    	}
    	else {
    		showMsg("Incorrect Details!!");
    	}
    }

    @FXML
    void initialize() {
        assert txtuid != null : "fx:id=\"txtuid\" was not injected: check your FXML file 'loginpage.fxml'.";
        assert txtpassword != null : "fx:id=\"txtpassword\" was not injected: check your FXML file 'loginpage.fxml'.";
        assert loginbtn != null : "fx:id=\"loginbtn\" was not injected: check your FXML file 'loginpage.fxml'.";

    }
}

