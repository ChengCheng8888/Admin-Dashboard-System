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
import object.Feedback;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class FeedbackController implements Initializable {

    @FXML
    private TableColumn<Feedback, String> FeedbackID;

    @FXML
    private TableColumn<Feedback, String> FeedbackDescription;

    @FXML
    private TableView<Feedback> Table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FeedbackID.setCellValueFactory(new PropertyValueFactory<>("feedbackID"));
        FeedbackDescription.setCellValueFactory(new PropertyValueFactory<>("feedbackDescription"));


        ObservableList<Feedback> feedback = Table.getItems();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnect", "root", "");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM feedback");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("Feedback_ID");
                String description = rs.getString("Feedback_Description");
                feedback.add(new Feedback(id, description));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML//Click to the Logout
    private Stage Logoutstage;
    private Scene Logoutscene;
    private Parent Logoutroot;

    @FXML
    public void setLogoutButton(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("FunctionPage.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource ("FunctionPage.fxml"));
        Logoutroot = loader.load();
        Logoutstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Logoutscene = new Scene(Logoutroot);
        Logoutstage.setScene(Logoutscene);
        Logoutstage.show();
        FunctionPageController functionPageController = loader.getController();
        functionPageController.setUserInformation(LoginSDPController.user);
    }
}