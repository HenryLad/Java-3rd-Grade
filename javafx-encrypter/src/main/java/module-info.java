module htl.javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.javafx to javafx.fxml;
    exports htl.javafx;
}