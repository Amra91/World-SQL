package prvi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Sql extends Application{
	
	static int broj = 0;
	static TextField tx = new TextField();
	TextArea tx_area = new TextArea();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		Pane main = new Pane();
		GridPane pane = new GridPane();
		Button submit = new Button("Submit");
		
		//Postavke gridPane razmak i padding
		pane.setHgap(10);
		pane.setVgap(10);
		pane.setPadding(new Insets(10, 10, 10, 10));
		
		//Kreiranje opcionog menija
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().add("ID");
		choiceBox.getItems().add("Country code");
		choiceBox.getItems().add("Name");
		choiceBox.getItems().add("Population");
		choiceBox.setValue("ID");
		
		pane.add(new Label("Pretrazi"), 0, 0);	//Dodavanje label-a u gridpane	
		pane.add(choiceBox, 1, 0);				//Dodavanje opcionog menija
		pane.add(new Label("Unesi"), 0, 1);		//Dodavanje label-a u gridpane	
		tx.setPrefWidth(100);					//Postavljanje sirine text field-a
		pane.add(tx,1,1);						//Dodavanje text field-a u gridpane
		pane.add(submit,0,4);					//Dodavanje dugmeta u gridpane
		
		
		//Kreiranje label-a 
		String ime_label = "Rezultati pretrage:";
		Label label = new Label(ime_label);		
		label.setTranslateX(50);				//Postavljanje na odredjenu poziciju po X osi label objekta
		label.setTranslateY(150);				//Postavljanje na odredjenu poziciju po Y osi label objekta
		
		//Postavljanje sirine i pozicije TextArea objekta
		tx_area.setMaxWidth(250);
		tx_area.setTranslateX(50);
		tx_area.setTranslateY(180);
		
		main.getChildren().add(pane);				//Dodavanje gridpane u glavni pane
		main.getChildren().addAll(tx_area,label);	//Dodavanje u glavni pane text area objekta i label(naljepnice)
		
		
		//Kreiranje listner objekta i uzimanje selekcionog broja iz menija
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
		    	  	  tx_area.setText("");
			    	  tx_area.getText();
			    	  tx.setText("");
			    	  tx.getText();
		    		  broj = (int)number2;
				 }
		   
		    });
		
		//Kreiranje handler objekta od klase Handler koju smo napravili 
		Handler handler = new Handler();
		submit.setOnAction(handler);	
		
		Scene scene = new Scene(main,400,400);
		stage.setScene(scene);
		stage.setTitle("Pretraga baze podataka");
		stage.setResizable(false);
		stage.show();
	}
	
	public static Statement getStatement() throws ClassNotFoundException, SQLException{
		Connection con = getConnection();
		Statement st = con.createStatement();
		return st;
	}
	//Metoda vraca String pretrage po imenu drzave iz databaze	
	public static String getCountry() throws ClassNotFoundException, SQLException{
		Statement st = getStatement();
		String SQL = "SELECT * FROM city WHERE CountryCode='"+tx.getText()+"'";
		ResultSet rs = st.executeQuery(SQL);
		String str = "";
			while(rs.next()){
				str =str+"ID: "+ rs.getString("ID")+" | Grad: "+rs.getString("Name")+
						" | Drzava: "+rs.getString("CountryCode")+" | Distrikt: "+rs.getString("District")+" | Populacija: "+rs.getString("Population")+"\n \n";
			}
			return str;
		}
	//Metoda vraca String pretrage po ID iz databaze		
	public static String getID() throws ClassNotFoundException, SQLException{
		Statement st = getStatement();
		String SQL = "SELECT * FROM city WHERE ID='"+tx.getText()+"'";
		ResultSet rs = st.executeQuery(SQL);
		String str = "";
			while(rs.next()){
				str =str+"ID: "+ rs.getString("ID")+"\nGrad: "+rs.getString("Name")+
						"\nDrzava: "+rs.getString("CountryCode")+"\nDistrikt: "+rs.getString("District")+"\nPopulacija: "+rs.getString("Population");
			}
			return str;
		}
	//Metoda vraca String pretrage po imenu grada iz databaze	
	public static String getName() throws ClassNotFoundException, SQLException{
		Statement st = getStatement();
		String SQL = "SELECT * FROM city WHERE Name='"+tx.getText()+"'";
		ResultSet rs = st.executeQuery(SQL);
		String str = "";
			while(rs.next()){
				str =str+"ID: "+ rs.getString("ID")+"\nGrad: "+rs.getString("Name")+
						"\nDrzava: "+rs.getString("CountryCode")+"\nDistrikt: "+rs.getString("District")+"\nPopulacija: "+rs.getString("Population");
			}
			return str;
		}
	//Metoda vraca String pretrage po broju stanovnika iz databaze	
	public static String getPopulation() throws ClassNotFoundException, SQLException{
		Statement st = getStatement();
		String SQL = "SELECT * FROM city WHERE Population='"+tx.getText()+"'";
		ResultSet rs = st.executeQuery(SQL);
		String str = "";
			while(rs.next()){
				str =str+"ID: "+ rs.getString("ID")+"\nGrad: "+rs.getString("Name")+
						"\nDrzava: "+rs.getString("CountryCode")+"\nDistrikt: "+rs.getString("District")+"\nPopulacija: "+rs.getString("Population")+"\n";
			}
			return str;
		}
	//Metoda ostvaruje konekciju sa databazom
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/world?autoReconnect=true&useSSL=false", "root", "11156744");
		return conn;
	}
	
	
	
	
	
	
	//Unutarnja klasa za upravljanje akcijom
	class Handler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {

			switch(broj){
			case 0:{
					try {
						tx_area.setText(getID());
						tx_area.getText();
					} catch (ClassNotFoundException e) {
					
					  e.printStackTrace();
					} catch (SQLException e) {
					
					  e.printStackTrace();
					}
				break;
				}
			case 1:{
					try {
						tx_area.setText(getCountry());
						tx_area.getText();
					} catch (ClassNotFoundException | SQLException e) {
						
						e.printStackTrace();
					}
					
				 break;
				}
			
			case 2:{
					try {
						tx_area.setText(getName());
						tx_area.getText();
				
					}catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
					}
				
				break;
				}
			case 3:{
				
					try {
						tx_area.setText(getPopulation());
						tx_area.getText();
				
						}catch (ClassNotFoundException | SQLException e) {
							e.printStackTrace();
						}
					
					break;
				}
			
			}
			
		}
			
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
}
