module org.example.utspemrogramanlanjut {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens org.example.utspemrogramanlanjut to javafx.fxml;
    exports org.example.utspemrogramanlanjut;
    exports org.example.utspemrogramanlanjut.controllers;
    opens org.example.utspemrogramanlanjut.controllers to javafx.fxml;
    exports org.example.utspemrogramanlanjut.interfaces;
    opens org.example.utspemrogramanlanjut.interfaces to javafx.fxml;
}