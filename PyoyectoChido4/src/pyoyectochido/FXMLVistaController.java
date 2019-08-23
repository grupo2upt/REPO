
package pyoyectochido;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.scene.control.TableColumn;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;

public class FXMLVistaController implements Initializable {
Connection conn=null;
private Connection conexion;
    
    String driver = "org.postgresql.Driver";
    String ruta = "jdbc:postgresql://172.16.0.68:5432/ciscoDB";
    String user = "postgres";
    String password = "12345";
    
    @FXML
    private ObservableList<ObservableList> data;
    @FXML
    private Label label;
    @FXML
    private TextField but;
    @FXML
    private Button button;
    @FXML
    private TableView Tabla1;
    @FXML
    private TextField edad;
    @FXML
    private TextField color;
    @FXML
    private TextField deporte;
    @FXML
    private TextField but1;
    @FXML
    private TextField edad1;
    @FXML
    private TextField color1;
    @FXML
    private TextField deporte1;
    @FXML
    private TextField but11;
    @FXML
    public void handleButtonActionn(ActionEvent event) throws ClassNotFoundException, SQLException {
        //aun no sirve
        Statement st;
        Class.forName(driver);
        //Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
        // consulta=(Statement) conne.createStatement();
        //consulta.executeUpdate("insert into usuarios(Nombre,Edad,Color,Deporte) values('"+but.getText()+"',"+edad.getText()+",'"+color.getText()+"','"+deporte.getText()+"')");  
        conn=DriverManager.getConnection(ruta,user,password);
        st=conn.createStatement();
            //SQL FOR SELECTING ALL OF CUSTOMER
        String SQL = "INSERT INTO usuarios(nombre, edad, color, deporte) VALUES ('"
                                           +but.getText()+"', '"
                                           +(edad.getText())+"', '"
                                           +color.getText()+"', '"
                                           +deporte.getText()+"');";
        st.execute(SQL);
        conn.close();
        JOptionPane.showMessageDialog(null,"Registro Guardado");
        limpiar();
    }
    
    @FXML
    public void handleButtonActionM(ActionEvent event) throws ClassNotFoundException, SQLException {
        //falta por checar
        Class.forName(driver);
        Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
        Statement consulta=(Statement) conne.createStatement();
        consulta.executeUpdate("update usuarios set Edad= "+edad1.getText()+",Color= '"+color1.getText()+"',Deporte='"+deporte1.getText()+"' where Nombre='"+but1.getText()+"'");   
        JOptionPane.showMessageDialog(null,"Registro Modificado");
        limpiar();
     
    }
    
    public void handleButtonActionE(ActionEvent event) throws ClassNotFoundException, SQLException {
        //falta por checar
        Class.forName(driver);
        Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
        Statement consulta=(Statement) conne.createStatement();
        consulta.executeUpdate("delete from usuarios where Nombre='"+but11.getText()+"'");
        JOptionPane.showMessageDialog(null,"Registro Eliminado");
        limpiar();
     
    }
    
    public void handleButtonActionC(ActionEvent event) throws ClassNotFoundException, SQLException {
        Connection c ;
        Tabla1.getColumns().clear();
          data = FXCollections.observableArrayList();
          try{
            Class.forName(driver);
            Connection conne=(Connection) DriverManager.getConnection(ruta,user,password);
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from usuarios";
            //ResultSet
            ResultSet rs = conne.createStatement().executeQuery(SQL);
     
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
                Tabla1.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }

            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            Tabla1.setItems(data);
          }catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
    }

    public void limpiar()
    {
        but.setText("");
        edad.setText("");
        color.setText("");
        deporte.setText("");
        but1.setText("");
       edad1.setText("");
        color1.setText("");
        deporte1.setText("");
        but11.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
    }
}


