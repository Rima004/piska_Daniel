module org.example.probs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.probs to javafx.fxml;
    exports org.example.probs;
    exports org.example.probs.objects;
    opens org.example.probs.objects to javafx.fxml;
    exports org.example.probs.Controllerr;
    opens org.example.probs.Controllerr to javafx.fxml;
    exports org.example.probs.Services;
    opens org.example.probs.Services to javafx.fxml;
}