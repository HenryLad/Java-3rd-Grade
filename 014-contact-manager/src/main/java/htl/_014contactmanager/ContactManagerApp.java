package htl._014contactmanager;

import htl._014contactmanager.view.ContactPresenter;
import javafx.application.Application;
import javafx.stage.Stage;

public class ContactManagerApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        ContactPresenter.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }

}
