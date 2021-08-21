
package customer;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import jdbcc.database_connection;

public class customerregiController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtcontact;

    @FXML
    private TextField txtaddress;

    @FXML
    private ComboBox<String> comboarea;

    @FXML
    private ComboBox<String> combohawker;

    @FXML
    private ListView<String> listavail;
    @FXML
    private DatePicker date;
    @FXML
    private ListView<String> listsel;
    @FXML
    private ListView<String> listavailprice;
    @FXML
    private ListView<String> listprice;
    
    PreparedStatement pstmt;
    Connection con;
    @FXML
    void selarea(ActionEvent event) {
    	combohawker.getItems().clear();
    	try
    	{
    		String selarea=comboarea.getSelectionModel().getSelectedItem();
    		pstmt=con.prepareStatement("select hname from hawkers where find_in_set(?,sareas)");
    		pstmt.setString(1,selarea);
    		ResultSet table=pstmt.executeQuery();
    		ArrayList<String>availhawker=new ArrayList<String>();
    		while(table.next())
    		{
    			availhawker.add(table.getString("hname"));
    					
    		}
    		combohawker.getItems().addAll(availhawker);
    		
        	
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    }
    @FXML
    void addpaper(MouseEvent event) {
    	if(event.getClickCount()==2)
    	{
        	ObservableList<Integer>selindx=listavail.getSelectionModel().getSelectedIndices();
        	for(Integer sel:selindx)
        	{
        		
    				listsel.getItems().add(listavail.getItems().get(sel));
    				listprice.getItems().add(listavailprice.getItems().get(sel));
        	}	
    	}
    }
    void showMsg(String msg)
    {
    	
    			Alert alert = new Alert(AlertType.ERROR);
    			
    			alert.setTitle("Error...");
    			
    			alert.setContentText(msg);

    			alert.showAndWait();
    }
    @FXML
    void dodelete(ActionEvent event) {
    	try {
			pstmt=con.prepareStatement("delete from customer where mobile=?");
			pstmt.setString(1,txtcontact.getText());
			int count=pstmt.executeUpdate();
			if(count==0)
				showMsg("No record Found");
			else
				showMsg("Record Deleted");
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void donew(ActionEvent event) {
    	txtaddress.setText("");
    	txtcontact.setText("");
    	txtname.setText("");
    	date.getEditor().clear();
    	comboarea.getSelectionModel().clearSelection();
    	combohawker.getSelectionModel().clearSelection();
    	listavail.getItems().clear();
    	listavailprice.getItems().clear();
    	listprice.getItems().clear();
    	listsel.getItems().clear();
    }

    @FXML
    void doregister(ActionEvent event) {
    	ObservableList<String> all=	listsel.getItems();
    	ObservableList<String> all_price=listprice.getItems();
    	String paper_sel="";
    	for(String sel:all)
    		paper_sel=sel+",";
    	paper_sel=paper_sel.substring(0,paper_sel.length()-1);
    	float total=0.0f;
    	for(String sel:all_price)
    		total=total+Float.parseFloat(sel);
    	try {
			pstmt=con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?,0,0)");
			pstmt.setString(1,txtname.getText());
			pstmt.setString(2,txtcontact.getText());
			pstmt.setString(3,txtaddress.getText());
			pstmt.setString(4,comboarea.getSelectionModel().getSelectedItem());
			pstmt.setString(5,combohawker.getSelectionModel().getSelectedItem());
			pstmt.setString(6,paper_sel);
			pstmt.setFloat(7,total);
			pstmt.setDate(8,Date.valueOf(date.getValue()));
			pstmt.executeUpdate();
			showMsg("Record Saved");
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }

    @FXML
    void dosearch(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
    	
    	dialog.setHeaderText("Seach Entry");
    	dialog.setContentText("Enter Mobile Number:");
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent())
    	{
	    	try{
				pstmt=con.prepareStatement("select * from customer where mobile=?");
				pstmt.setString(1,result.get());
				ResultSet table=pstmt.executeQuery();
				while(table.next())
				{
					 txtname.setText(table.getString("cname"));
					 txtaddress.setText(table.getString("address"));
					 txtcontact.setText(table.getString("mobile"));
					 comboarea.setValue(table.getString("area"));
					 combohawker.setValue(table.getString("hawker"));
					 date.setValue(LocalDate.parse(table.getString("dos")));
					 String []ary=table.getString("selpaper").split(",");
					 for(String ref:ary)
					 {
						 listsel.getItems().add(ref);
					 }
					 
				}
	    	}
	    	catch(Exception exp)
			{ 
				exp.printStackTrace();    	
			
    	}
	}
    }

    @FXML
    void doupdate(ActionEvent event) {
    	
    }

    @FXML
    void initialize() {
    	con=database_connection.doConnect();
		if(con==null)
			System.out.println("Not Coonected");
		else
			System.out.println("Connected");
    	ArrayList<String>areas=new ArrayList<String>(Arrays.asList("Patel Nagar","Bharat Nagar","Green City","Model Town","Ganesh Nagar"));
    	comboarea.getItems().addAll(areas);
    	
    	ArrayList<String>availpaper=new ArrayList<String>();
    	ArrayList<String>availprice=new ArrayList<String>();

    	try {
			pstmt=con.prepareStatement("select * from paper");
			ResultSet table=pstmt.executeQuery();
			while(table.next())
			{
				availpaper.add(table.getString("paper"));
				availprice.add(table.getString("price"));
						
			}
			listavail.getItems().addAll(availpaper);
			listavailprice.getItems().addAll(availprice);
			listavail.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} 
    	catch (Exception e) {
			
			e.printStackTrace();
		}

    }
}

