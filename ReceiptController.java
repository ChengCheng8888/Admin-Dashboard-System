package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    @FXML
    private Text text;


    @FXML
    void print(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null){
            printerJob.printPage(text);
            printerJob.endJob();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPayment(String id, double amount, String type, String status, String tradeId) {
        text.setText(
                "                                  Report"+"\n"+
                "*****************************************************\n" +
                        "Payment ID\t\t: " + id + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Amount\t\t\t: " + "RM "+amount  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Payment Type\t\t: " + type  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Payment Status\t: " + status  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Trade ID\t\t\t: " + tradeId + "\n" +
                        "*****************************************************"
        );
    }


    public void setTransport (String id, String shipmentId, String transporterId, String transportTime, String transportDate,String arriveDate,String transportStatus){
    {
        text.setText(
                "                                  Report"+"\n"+
                        "*****************************************************\n" +
                        "Transport ID\t\t\t: " + id + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Shipment ID\t\t\t: " + shipmentId  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Transporter ID\t\t\t: " + transporterId  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Transport Time\t\t: " + transportTime  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Transport Date\t\t: " + transportDate  + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Arrive Date\t\t\t: " + arriveDate + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "Transport Status\t\t: " + transportStatus + "\n" +
                        "--------------------------------------------------------"+"\n"+
                        "*****************************************************"
        );
    }
    }
}
