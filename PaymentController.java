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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import object.Payment;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private TableColumn<Payment, Double> AmountColumn;

    @FXML
    private TableColumn<Payment, String> PaymentIDColumn;

    @FXML
    private TableColumn<Payment, String> PaymentStatusColumn;

    @FXML
    private TableColumn<Payment, String> PaymentTypeColumn;

    @FXML
    private TableView<Payment> Table;

    @FXML
    private TableColumn<Payment, String> TradeIDColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PaymentIDColumn.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        PaymentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        PaymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        TradeIDColumn.setCellValueFactory(new PropertyValueFactory<>("tradeID"));


        ObservableList<Payment> payment = Table.getItems();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM payment");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("Payment_ID");
                double amount = rs.getDouble("Payment_Amount");
                String type = rs.getString("Payment_Type");
                String status = rs.getString("Payment_Status");
                String tradeId = rs.getString("Trade_ID");
                payment.add(new Payment(id, amount, type, status, tradeId));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void print(ActionEvent event) throws IOException {
        Payment payment = Table.getSelectionModel().getSelectedItem();

        if (payment != null) {
            String id = payment.getPaymentID();
            double amount = payment.getAmount();
            String type = payment.getPaymentType();
            String status = payment.getPaymentStatus();
            String tradeId = payment.getTradeID();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);

            ReceiptController receiptController = loader.getController();
            receiptController.setPayment(id, amount, type, status, tradeId);
            stage.show();
            Image icon = new Image("LogoSDP.png");
            stage.getIcons().add(icon);
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
