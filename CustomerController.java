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
import object.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class CustomerController implements Initializable {
    @FXML
    private TableColumn<Customer, String> TelefonColumn;
    @FXML
    private TableColumn<Customer, String> AddressColumn;
    @FXML
    private TableColumn<Customer, String> GmailColumn;
    @FXML
    private TableColumn<Customer, String> IDColumn;
    @FXML
    private TableColumn<Customer, String> NameColumn;
    @FXML
    private TableColumn<Customer, String> PasswordColumn;
    @FXML
    private TableView<Customer> Table;

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
        TelefonColumn.setCellValueFactory(new PropertyValueFactory<>("customerTelefon"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        GmailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("customerPassword"));

        ObservableList<Customer> customers = Table.getItems();
        try {
            //Class.forName("com.mysql.jdbc,Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            Statement st = con.createStatement();
            String sql = "SELECT * FROM customer";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("Customer_ID");
                String username = rs.getString("Customer_Name");
                String address = rs.getString("Customer_Address");
                String gmail = rs.getString("Customer_Email");
                String telefon = rs.getString("Customer_Telefon");
                String password = rs.getString("Customer_Password");

                customers.add(new Customer(id, username, address, gmail, telefon, password));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Table.setItems(customers);

        Table.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Customer>() {
            @Override
            public void onChanged(Change<? extends Customer> change) {
                Customer customer = Table.getSelectionModel().getSelectedItem();
                IDType.setText(customer.getCustomerID());
                NameType.setText(customer.getCustomerName());
                AddressType.setText(customer.getCustomerAddress());
                TelefonType.setText(customer.getCustomerTelefon());
                GmailType.setText(customer.getCustomerEmail());
                PasswordType.setText(customer.getCustomerPassword());
            }
        });
    }

    @FXML
    public void addButton () throws SQLException {
        ObservableList<Customer> customers= Table.getItems();
        String id = IDType.getText();
        String username = NameType.getText();
        String address = AddressType.getText();
        String gmail = GmailType.getText();
        String telefon = TelefonType.getText();
        String password = PasswordType.getText();

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
        PreparedStatement psAdd = con.prepareStatement("INSERT INTO Customer VALUES " +
                "(?,?,?,?,?,?)");
        psAdd.setString(1, id);
        psAdd.setString(2, username);
        psAdd.setString(3, address);
        psAdd.setString(4, gmail);
        psAdd.setString(5, telefon);
        psAdd.setString(6, password);
        psAdd.executeUpdate();

        customers.add(new Customer(id, username, address, gmail, telefon, password));
        Table.refresh();
        con.close();

    }

    @FXML
    public void deleteButton () {
        ObservableList<Customer> customers = Table.getItems();
        Customer customer = Table.getSelectionModel().getSelectedItem();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            PreparedStatement psDelete = con.prepareStatement("DELETE from Customer WHERE Customer_ID = ?");
            psDelete.setString(1, customer.getCustomerID());
            psDelete.executeUpdate();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        customers.remove(customer);
        Table.refresh();
    }
}


