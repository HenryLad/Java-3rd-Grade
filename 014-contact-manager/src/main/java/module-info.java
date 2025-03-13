module htl._014contactmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens htl._014contactmanager to javafx.fxml;
    exports htl._014contactmanager;
}