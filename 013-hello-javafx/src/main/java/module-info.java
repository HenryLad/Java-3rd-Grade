module htl.sigma {
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.sigma to javafx.fxml;
    exports htl.sigma;
}