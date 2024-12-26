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
import object.Seller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class SellerController implements Initializable {
    @FXML
    private TableColumn<Seller, String> TelefonColumn;
    @FXML
    private TableColumn<Seller, String> AddressColumn;
    @FXML
    private TableColumn<Seller, String> GmailColumn;
    @FXML
    private TableColumn<Seller, String>IDColumn;
    @FXML
    private TableColumn<Seller, String> NameColumn;
    @FXML
    private TableColumn<Seller, String> PasswordColumn;
    @FXML
    private TableView<Seller> Table;

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

    //LogoutFunction
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

// Show SellerData
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TelefonColumn.setCellValueFactory(new PropertyValueFactory<>("sellerTelefon"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("sellerAddress"));
        GmailColumn.setCellValueFactory(new PropertyValueFactory<>("sellerEmail"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("sellerID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("sellerPassword"));

        ObservableList<Seller> customers = Table.getItems();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            Statement st = con.createStatement();
            String sql = "SELECT * FROM seller";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Seller_ID");
                String username = rs.getString("Seller_Name");
                String address = rs.getString("Seller_Address");
                String gmail = rs.getString("Seller_Email");
                String telefon = rs.getString("Seller_Telefon");
                String password = rs.getString("Seller_Password");
                customers.add(new Seller(id, username, address, gmail, telefon, password));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Table.setItems(customers);

//Select Show in Column
        Table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Seller>() {
            @Override
            public void onChanged(Change<? extends Seller> change) {
                Seller seller = Table.getSelectionModel().getSelectedItem();
                IDType.setText(seller.getSellerID());
                NameType.setText(seller.getSellerName());
                AddressType.setText(seller.getSellerAddress());
                TelefonType.setText(seller.getSellerTelefon());
                GmailType.setText(seller.getSellerEmail());
                PasswordType.setText(seller.getSellerPassword());
            }
        });

    }

    //ADD
    @FXML
    public void addButton () throws SQLException {
        ObservableList<Seller> sellers = Table.getItems();
        String id = IDType.getText();
        String username = NameType.getText();
        String address = AddressType.getText();
        String gmail = GmailType.getText();
        String telefon = TelefonType.getText();
        String password = PasswordType.getText();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
        PreparedStatement psAdd = con.prepareStatement("INSERT INTO Seller VALUES " +
                "(?,?,?,?,?,?)");
        psAdd.setString(1, id);
        psAdd.setString(2, username);
        psAdd.setString(3, address);
        psAdd.setString(4, gmail);
        psAdd.setString(5, telefon);
        psAdd.setString(6, password);
        psAdd.executeUpdate();

        sellers.add(new Seller(id, username, address, gmail, telefon, password));
        Table.refresh();
        con.close();

    }
//DELETE
    @FXML
    public void deleteButton () {
        ObservableList<Seller> sellers = Table.getItems();
        Seller seller = Table.getSelectionModel().getSelectedItem();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            PreparedStatement psDelete = con.prepareStatement("DELETE from Seller WHERE Seller_ID = ?");
            psDelete.setString(1, seller.getSellerID());
            psDelete.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        sellers.remove(seller);
        Table.refresh();
    }
}
