
package hawkermanager;
import java.io.File;
import jdbcc.*;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdbcc.database_connection;

public class hawkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView photo;

    @FXML
    private TextField txtphoto;

    @FXML
    private TextField txtarea;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtcontact;

    @FXML
    private ComboBox<String> comboarea;

    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> comboname;

    @FXML
    void btnbrowse(ActionEvent event) {
    	Stage stage = new Stage();
    	FileChooser fil_chooser = new FileChooser();
		File file = fil_chooser.showOpenDialog(stage);
		FileInputStream input=null;
		try{
			 input=new FileInputStream(file);
		}
		catch(Exception exp)
		{
			
		}
		Image image=new Image(input);
		photo.setImage(image);
		txtphoto.setText(file.getAbsolutePath());
    }

    @FXML
    void dodelete(ActionEvent event) {
    	try{
    		pstmt=con.prepareStatement("delete from hawkers where hname=?");
    		pstmt.setString(1,comboname.getEditor().getText());
    		int count=pstmt.executeUpdate();
    		if(count==0)
    			showMsg("Invalid Name");
    		else
    			{
    				showMsg(count+" Records Deleted");
    				fillname();
    			
    			}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }

    @FXML
    void donew(ActionEvent event) {
    	str="";
    	txtaddress.clear();
    	txtarea.clear();
    	txtcontact.clear();
    	txtphoto.clear();
    	date.getEditor().clear();
    }
    Connection con;
    PreparedStatement pstmt;
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.ERROR);
    			
    			alert.setTitle("Error...");
    			
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    void fillname()
    {
    	comboname.getItems().clear();
    	ArrayList<String> names=new ArrayList<String>();
    	try{
			pstmt=con.prepareStatement("select hname from hawkers");
			ResultSet table=pstmt.executeQuery();
		
		while(table.next())
		{
			String namee=table.getString("hname");
			names.add(namee);
			
		}
		comboname.getItems().addAll(names);
		}
		catch(Exception exp)
		{ exp.printStackTrace();    			
		}
    	
    }
    @FXML
    void dorecord(ActionEvent event) {
    	try {
    		pstmt=con.prepareStatement("insert into hawkers values(?,?,?,?,?,?)");
    		pstmt.setString(1,comboname.getEditor().getText());
    		pstmt.setString(2,txtphoto.getText());
    		pstmt.setString(3,txtaddress.getText());
    		pstmt.setString(4,txtcontact.getText());
    		pstmt.setDate(5,Date.valueOf(date.getValue()));
    		pstmt.setString(6,txtarea.getText());
    		
    		pstmt.executeUpdate();
    		showMsg("Record Saved");
    		fillname();
    	}
    	catch(SQLIntegrityConstraintViolationException exp)
    	{
    		showMsg("Dupliacte Entry for name");
    		
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }

    @FXML
    void doupdate(ActionEvent event) {
    	try {
    		pstmt=con.prepareStatement("update hawkers set picpath=?,address=?,mobile=?,doj=?,sareas=? where hname=?");
    		pstmt.setString(1,txtphoto.getText());
    		pstmt.setString(2,txtaddress.getText());
    		pstmt.setString(3,txtcontact.getText());
    		pstmt.setDate(4,Date.valueOf(date.getValue()));
    		pstmt.setString(5,txtarea.getText());
    		pstmt.setString(6,comboname.getEditor().getText());
    		pstmt.executeUpdate();
    		showMsg("Record Saved");
    		fillname();
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }
    @FXML
    void search(ActionEvent event) {
    	try{
			pstmt=con.prepareStatement("select * from hawkers where hname=?");
			pstmt.setString(1,comboname.getEditor().getText());
			ResultSet table=pstmt.executeQuery();
			while(table.next())
			{
				 txtaddress.setText(table.getString("address"));
				 txtarea.setText(table.getString("sareas"));
				 txtcontact.setText(table.getString("mobile"));
				 txtphoto.setText(table.getString("picpath"));
				 date.setValue(LocalDate.parse(table.getString("doj")));
				 Image image=new Image(txtphoto.getText());
				 System.out.println();
				 photo.setImage(image);
			}
		}
		catch(Exception exp)
		{ 
			exp.printStackTrace();    	
		}
    }
    String str="";
    @FXML
    
    void addarea(ActionEvent event) {
    	str=str+comboarea.getSelectionModel().getSelectedItem()+",";
    	txtarea.clear();
    	txtarea.setText(str.substring(0,str.length()-1));
    }
    @FXML
    void initialize() {
    	ArrayList<String>areas=new ArrayList<String>(Arrays.asList("Patel Nagar","Bharat Nagar","Green City","Model Town","Ganesh Nagar"));
    	comboarea.getItems().addAll(areas);
    	con=database_connection.doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");
		fillname();

    }
}


