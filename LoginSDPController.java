package application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LoginSDPController {

    @FXML
    private TextField id;
    @FXML
    private PasswordField password;
    @FXML
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    public static String user;

    public void login() throws IOException {
        connect = database.connectDb();
        user = id.getText();
        String pass = password.getText();
        String sql = "SELECT * FROM admin WHERE Admin_Name=? and Admin_Password=?";
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, user);
            prepare.setString(2, pass);
            result = prepare.executeQuery();
            Alert alert;
            if (result.next()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FunctionPage.fxml"));
                Parent root = loader.load();
                FunctionPageController functionPageController = loader.getController();
                functionPageController.setUserInformation(user);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                id.clear();
                password.clear();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username/Password");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

