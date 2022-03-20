module com.schoolaccount32.othellojavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.schoolaccount32.othellojavafx to javafx.fxml;
    exports com.schoolaccount32.othellojavafx;
    exports com.schoolaccount32.othellojavafx.board;
    opens com.schoolaccount32.othellojavafx.board to javafx.fxml;
    exports com.schoolaccount32.othellojavafx.Players;
    opens com.schoolaccount32.othellojavafx.Players to javafx.fxml;
}