package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import object.Order;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private TableColumn<Order, String> TransportID;

    @FXML
    private TableColumn<Order, String> ShipmentID;

    @FXML
    private TableColumn<Order, String> TransporterID ;

    @FXML
    private TableColumn<Order, String> TransportTime;

    @FXML
    private TableView<Order> Table;

    @FXML
    private TableColumn<Order, String> TransportDate;
    @FXML
    private TableColumn<Order, String> ArriveID;
    @FXML
    private TableColumn<Order, String> TransportStatus;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TransportID.setCellValueFactory(new PropertyValueFactory<>("transportID"));
        ShipmentID.setCellValueFactory(new PropertyValueFactory<>("shipmentID"));
        TransporterID.setCellValueFactory(new PropertyValueFactory<>("transportID"));
        TransportTime.setCellValueFactory(new PropertyValueFactory<>("transportTime"));
        TransportDate.setCellValueFactory(new PropertyValueFactory<>("transportDate"));
        ArriveID.setCellValueFactory(new PropertyValueFactory<>("arriveID"));
        TransportStatus.setCellValueFactory(new PropertyValueFactory<>("transportStatus"));



        ObservableList<Order> order = Table.getItems();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM transport");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("Transport_ID");
                String shipmentId = rs.getString("Shipment_ID");
                String transporterId = rs.getString("Transporter_ID");
                String transportTime= rs.getString("Transport_Time");
                String transportDate = rs.getString("Transport_Date");
                String arriveDate = rs.getString("Arrive_Date");
                String transportStatus= rs.getString("Transport_Status");
                order.add(new Order(id, shipmentId, transporterId, transportTime, transportDate,arriveDate,transportStatus));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void print(ActionEvent event) throws IOException {
        Order order = Table.getSelectionModel().getSelectedItem();

        if (order != null) {
            String id = order.getTransportID();
            String shipmentId = order.getShipmentID();
            String transporterId = order.getTransporterID();
            String transportTime = order.getTransportTime();
            String transportDate = order.getTransportDate();
            String arriveID = order.getArriveID();
            String transportStatus = order.getTransportStatus();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);

            ReceiptController receiptController = loader.getController();
            receiptController.setTransport(id, shipmentId, transporterId, transportTime, transportDate, arriveID, transportStatus);
            stage.show();
        }
    }

    @FXML//Click to the Logout
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
}
