package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Window;
import java.io.IOException;
import java.sql.*;

public class FunctionPageController {

    @FXML
    private Label label_welcome;
    @FXML
    private Label numberCustomer;
    @FXML
    private Label numberSeller;
    @FXML
    private Label numberTransporter;
    @FXML
    private ImageView image;

    public void setUserInformation(String user) {
        label_welcome.setText(user); // Display the username in the label
        Image image1 = new Image("C:\\Users\\user\\File PC\\Downloads\\pngegg.png");
        image.setImage(image1);
        updateTotalCustomer();
    }

    @FXML
    public void customer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setLogoutButton(ActionEvent event) throws IOException {
        // Close the current stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Check if the login stage already exists
        Stage loginStage = getExistingLoginStage();
        if (loginStage != null) {
            loginStage.show(); // Bring the existing login stage to the front
        } else {
            // Open the LoginSDP.fxml stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginSDP.fxml"));
            Parent root = loader.load();
            loginStage = new Stage();
            Scene scene = new Scene(root);
            loginStage.setScene(scene);
            loginStage.show();
        }
    }

    // Method to get the existing login stage if it exists
    private Stage getExistingLoginStage() {
        Stage existingStage = null;
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                Stage stage = (Stage) window;
                if (stage.getTitle().equals("LoginSDP")) {
                    existingStage = stage;
                    break;
                }
            }
        }
        return existingStage;
    }

    @FXML
    public void seller(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Seller.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void transporter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Transporter.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void payment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPaymentManagement.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void shipment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeliveryOrderManagement.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void feedback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void updateTotalCustomer() {
        try {
            // 连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");

            // 执行查询，获取行数
            String query = "SELECT COUNT(*) AS row_count FROM customer;";
            PreparedStatement psCount = connection.prepareStatement(query);
            ResultSet resultSet = psCount.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("row_count");
            System.out.println(count);
            numberCustomer.setText(count + "");

            query = "SELECT COUNT(*) AS row_count FROM seller;";
            psCount = connection.prepareStatement(query);
            resultSet = psCount.executeQuery();
            resultSet.next();
            count = resultSet.getInt("row_count");
            System.out.println(count);
            numberSeller.setText(count + "");                // 关闭连接

            query = "SELECT COUNT(*) AS row_count FROM transporter;";
            psCount = connection.prepareStatement(query);
            resultSet = psCount.executeQuery();
            resultSet.next();
            count = resultSet.getInt("row_count");
            System.out.println(count);
            numberTransporter.setText(count + "");

            resultSet.close();
            psCount.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



