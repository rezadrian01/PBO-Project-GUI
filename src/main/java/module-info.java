module org.example.utspemrogramanlanjut {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.utspemrogramanlanjut to javafx.fxml;
    exports org.example.utspemrogramanlanjut;
    exports org.example.utspemrogramanlanjut.controller;
    opens org.example.utspemrogramanlanjut.controller to javafx.fxml;
}