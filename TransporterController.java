package application;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import object.Transporter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class TransporterController implements Initializable {
    @FXML
    private TableColumn<Transporter, String> TelefonColumn;
    @FXML
    private TableColumn<Transporter, String> AddressColumn;
    @FXML
    private TableColumn<Transporter, String> GmailColumn;
    @FXML
    private TableColumn<Transporter, String> IDColumn;
    @FXML
    private TableColumn<Transporter, String> NameColumn;
    @FXML
    private TableColumn<Transporter, String> PasswordColumn;
    @FXML
    private TableView<Transporter> Table;
    @FXML
    private TextField AddressType;
    @FXML
    private TextField IDType;
    @FXML
    private TextField TelefonType;
    @FXML
    private TextField GmailType;
    @FXML
    private TextField NameType;
    @FXML
    private TextField PasswordType;

    private Stage Logoutstage;
    private Scene Logoutscene;
    private Parent Logoutroot;

    @FXML
    public void setLogoutButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FunctionPage.fxml"));
        Logoutroot = loader.load();
        Logoutstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Logoutscene = new Scene(Logoutroot);
        Logoutstage.setScene(Logoutscene);
        Logoutstage.show();
        FunctionPageController functionPageController = loader.getController();
        functionPageController.setUserInformation(LoginSDPController.user);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TelefonColumn.setCellValueFactory(new PropertyValueFactory<>("transporterTelefon"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("transporterAddress"));
        GmailColumn.setCellValueFactory(new PropertyValueFactory<>("transporterEmail"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("transporterID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("transporterName"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("transporterPassword"));

        ObservableList<Transporter> transporter = Table.getItems();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            Statement st = con.createStatement();
            String sql = "SELECT * FROM transporter";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Transporter_ID");
                String username = rs.getString("Transporter_Name");
                String address = rs.getString("Transporter_Address");
                String gmail = rs.getString("Transporter_Email");
                String telefon = rs.getString("Transporter_Telefon");
                String password = rs.getString("Transporter_Password");

                transporter.add(new Transporter(id, username, address, gmail, telefon, password));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Table.setItems(transporter);
        //Select Show in Column
        Table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Transporter>() {
            @Override
            public void onChanged(Change<? extends Transporter> change) {
                Transporter transporter = Table.getSelectionModel().getSelectedItem();
                IDType.setText(transporter.getTransporterID());
                NameType.setText(transporter.getTransporterName());
                AddressType.setText(transporter.getTransporterAddress());
                TelefonType.setText(transporter.getTransporterTelefon());
                GmailType.setText(transporter.getTransporterEmail());
                PasswordType.setText(transporter.getTransporterPassword());
            }
        });
    }

    @FXML
    public void addButton () throws SQLException {
        ObservableList<Transporter> transporters = Table.getItems();
        String id = IDType.getText();
        String username = NameType.getText();
        String address = AddressType.getText();
        String gmail = GmailType.getText();
        String telefon = TelefonType.getText();
        String password = PasswordType.getText();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
        PreparedStatement psAdd = con.prepareStatement("INSERT INTO Transporter VALUES " +
                "(?,?,?,?,?,?)");
        psAdd.setString(1, id);
        psAdd.setString(2, username);
        psAdd.setString(3, address);
        psAdd.setString(4, gmail);
        psAdd.setString(5, telefon);
        psAdd.setString(6, password);
        psAdd.executeUpdate();

        transporters.add(new Transporter(id, username, address, gmail, telefon, password));
        Table.refresh();
        con.close();
    }



    @FXML
    public void deleteButton () {
        ObservableList<Transporter> sellers = Table.getItems();
        Transporter transporter = Table.getSelectionModel().getSelectedItem();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            PreparedStatement psDelete = con.prepareStatement("DELETE from Transporter WHERE Seller_ID = ?");
            psDelete.setString(1, transporter.getTransporterID());
            psDelete.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        sellers.remove(transporter);
        Table.refresh();
    }
}
