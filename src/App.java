import javafx.application.Application;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.io.FileNotFoundException;

public class App extends Application {
    TableView<Apotek> tableView = new TableView<Apotek>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Data Obat");
        TableColumn<Apotek, String> columnKode = new TableColumn<>("Kode");
        columnKode.setCellValueFactory(new PropertyValueFactory<>("Kode"));

        TableColumn<Apotek, String> columnNama = new TableColumn<>("Nama");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));

        TableColumn<Apotek, String> columnHarga = new TableColumn<>("Harga");
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("Harga"));

        tableView.getColumns().add(columnKode);
        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnHarga);

        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Add Data");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> add());

        Button button2 = new Button("Delete");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> delete());

        Button button3 = new Button("Edit");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> edit());

        Button button4 = new Button("Refresh");
        toolBar.getItems().add(button4);
        button4.setOnAction(e -> re());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from obat");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Apotek(rs.getString("Kode"), rs.getString("Nama"), rs.getString("Harga")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Add Data");

        TextField KodeField = new TextField();
        TextField NamaField = new TextField();
        TextField HargaField = new TextField();
        
        Label labelKode = new Label("Kode");
        Label labelNama = new Label("Nama");
        Label labelHarga = new Label("Harga");

        VBox hbox1 = new VBox(5, labelKode, KodeField);
        VBox hbox2 = new VBox(5, labelNama, NamaField);
        VBox hbox3 = new VBox(5, labelHarga, HargaField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into obat SET Kode='%s', Nama='%s', Harga='%s'";
                sql = String.format(sql, KodeField.getText(), NamaField.getText(), HargaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField NoField = new TextField();
        Label labelNo = new Label("Nama Obat");

        VBox hbox1 = new VBox(5, labelNo, NoField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from obat WHERE nama='%s'";
                sql = String.format(sql, NoField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Data");

        TextField KodeField = new TextField();
        TextField HargaField = new TextField();
        Label labelKode = new Label("Kode");
        Label labelHarga = new Label("Harga");

        VBox hbox1 = new VBox(5, labelKode, KodeField);
        VBox hbox2 = new VBox(5, labelHarga, HargaField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE obat SET Harga ='%s' WHERE Kode='%s'";
                sql = String.format(sql, HargaField.getText(), KodeField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from obat");
            while (rs.next()) {
                tableView.getItems().addAll(new Apotek(rs.getString("Kode"), rs.getString("Nama"), rs.getString("Harga")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE obat DROP Kode";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE obat ADD Kode INT NOT NULL PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}