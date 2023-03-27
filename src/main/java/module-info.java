module com.bernardispa.blackjack {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bernardispa.blackjack to javafx.fxml;
    exports com.bernardispa.blackjack;
}
