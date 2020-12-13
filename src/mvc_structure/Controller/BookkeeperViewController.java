package mvc_structure.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import mvc_structure.*;
import mvc_structure.Model.Book;
import mvc_structure.Model.Discount;


public class BookkeeperViewController implements Initializable {

    //initialising for TestFX
    public ObservableList<Book> booksList =
            FXCollections.observableArrayList(
                    new Book("Moby Dick", 1851, 15.20),
                    new Book("The Terrible Privacy of Maxwell Sim", 2010, 13.14),
                    new Book("Still Life With Woodpecker", 1980, 11.05),
                    new Book("Sleeping Murder", 1976, 10.24),
                    new Book("Three Men in a Boat", 1889, 12.87),
                    new Book("The Time Machine", 1895, 10.43),
                    new Book("The Caves of Steel", 1954, 8.12),
                    new Book("Idle Thoughts of an Idle Fellow", 1886, 7.32),
                    new Book("A Christmas Carol", 1843, 4.23),
                    new Book("A Tale of Two Cities", 1859, 6.32),
                    new Book("Great Expectations", 1861, 13.21)
            );
    public ObservableList<Discount> discountsPerBookList =
            FXCollections.observableArrayList(
                    new Discount(">", 2000, 10)
            );
    public ObservableList<Discount> discountsOnTotalList =
            FXCollections.observableArrayList(
                    new Discount(">", 30, 5)
            );
    public ObservableList<Book> shoppingList = FXCollections.observableArrayList();

    public void transferLists(ObservableList<Book> booksList, ObservableList<Discount> discountsPerBookList,
                              ObservableList<Discount> discountsOnTotalList, ObservableList<Book>  shoppingList) {
        this.booksList = booksList;
        this.discountsPerBookList = discountsPerBookList;
        this.discountsOnTotalList = discountsOnTotalList;
        this.shoppingList = shoppingList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            setupBooksList();
            setupDiscountsPerBookList();
            setupDiscountsOnTotalList();
        });
    }

    @FXML private HBox view;
    @FXML private Label msg;
    @FXML private TextField newTitle, newYear, newPrice,
                            newBookCondition, newBookConditionValue, newBookAmount,
                            newTotalCondition, newTotalConditionValue, newTotalAmount;

    @FXML private TableView<Book> availableBooks;
    @FXML private TableColumn<Book, String> bookTitleCol;
    @FXML private TableColumn<Book, Integer> bookYearCol;
    @FXML private TableColumn<Book, Double> bookPriceCol;

    @FXML private TableView<Discount> discountsPerBook;
    @FXML private TableColumn<Discount, String> conditionBookCol;
    @FXML private TableColumn<Discount, Double> conditionValueBookCol;
    @FXML private TableColumn<Discount, Integer> amountBookCol;

    @FXML private TableView<Discount> discountsOnTotal;
    @FXML private TableColumn<Discount, String> conditionTotalCol;
    @FXML private TableColumn<Discount, Double> conditionValueTotalCol;
    @FXML private TableColumn<Discount, Integer> amountTotalCol;

    @FXML
    void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mvc_structure/View/HomeView.fxml"));
        Parent root = loader.load();

        HomeViewController homeViewController = loader.getController();
        homeViewController.transferLists(booksList, discountsPerBookList, discountsOnTotalList, shoppingList);

        Stage primaryStage = (Stage) view.getScene().getWindow();
        primaryStage.setScene(new Scene(root, 1200, 830));
        primaryStage.show();
    }

    @FXML
    void exit(ActionEvent e) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = a.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        if (button == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void addBook(ActionEvent e) {
        String title = newTitle.getText();
        String year = newYear.getText();
        String price = newPrice.getText();

        boolean success = true;
        if(title.isEmpty()) {
            msg.setText("Add Book: Title cannot be empty.");
            success=false;
        } else if(year.isEmpty()) {
            msg.setText("Add Book: Year cannot be empty.");
            success=false;
        } else if(!isNumeric(year,'i')) {
            msg.setText("Add Book: Please enter a valid year > 0.");
            success=false;
        } else if(Integer.parseInt(year) <= 0) {
            msg.setText("Add Book: Please enter a valid year > 0.");
            success=false;
        } else if(price.isEmpty()) {
            msg.setText("Add Book: Price cannot be empty.");
            success=false;
        } else if(!isNumeric(price, 'd')) {
            msg.setText("Add Book: Please enter a valid price >= 0.");
            success=false;
        } else if(Double.parseDouble(price) < 0) {
            msg.setText("Add Book: Please enter a valid price >= 0.");
            success=false;
        }

        if(success) {
            Book b = new Book(title, Integer.parseInt(year), Double.parseDouble(price));
            booksList.add(b);
            availableBooks.getItems().add(b);
            availableBooks.refresh();
            newTitle.clear();
            newYear.clear();
            newPrice.clear();
        }
    }

    @FXML
    void addBookDiscount(ActionEvent e) {
        String condition = newBookCondition.getText();
        String conditionValue = newBookConditionValue.getText();
        String amount = newBookAmount.getText();

        boolean success = true;
        if(condition.isEmpty() || !condition.matches("<|<=|>|>=|==")) {
            msg.setText("Add Per Book Discount: Condition must be <, <=, >, >= or ==.");
            success=false;
        } else if(conditionValue.isEmpty()) {
            msg.setText("Add Per Book Discount: Condition value cannot be empty.");
            success=false;
        } else if(!isNumeric(conditionValue,'i')) {
            msg.setText("Add Per Book Discount: Please enter a valid integer condition year > 0.");
            success=false;
        } else if(Integer.parseInt(conditionValue) <= 0) {
            msg.setText("Add Per Book Discount: Please enter a valid integer condition year > 0.");
            success=false;
        } else if(amount.isEmpty()) {
            msg.setText("Add Per Book Discount: Amount cannot be empty.");
            success=false;
        } else if(!isNumeric(amount, 'i')) {
            msg.setText("Add Per Book Discount: Please enter a valid integer amount >= 0 and <=100.");
            success=false;
        } else if(Integer.parseInt(amount) < 0 || Integer.parseInt(amount)>100) {
            msg.setText("Add Per Book Discount: Please enter a valid integer amount >= 0 and <=100.");
            success=false;
        }

        if(success) {
            Discount d = new Discount(condition, Integer.parseInt(conditionValue), Integer.parseInt(amount));
            discountsPerBookList.add(d);
            discountsPerBook.getItems().add(d);
            discountsPerBook.refresh();
            newBookCondition.clear();
            newBookConditionValue.clear();
            newBookAmount.clear();
        }
    }

    @FXML
    void addTotalDiscount(ActionEvent e) {
        String condition = newTotalCondition.getText();
        String conditionValue = newTotalConditionValue.getText();
        String amount = newTotalAmount.getText();

        boolean success = true;
        if(condition.isEmpty() || !condition.matches("<|<=|>|>=|==")) {
            msg.setText("Add On Total Discount: Condition must be <, <=, >, >= or ==.");
            success=false;
        } else if(conditionValue.isEmpty()) {
            msg.setText("Add On Total Discount: Condition value cannot be empty.");
            success=false;
        } else if(!isNumeric(conditionValue,'d')) {
            msg.setText("Add On Total Discount: Please enter a valid condition price > 0.");
            success=false;
        } else if(Double.parseDouble(conditionValue) <= 0) {
            msg.setText("Add On Total Discount: Please enter a valid condition price > 0.");
            success=false;
        } else if(amount.isEmpty()) {
            msg.setText("Add On Total Discount: Amount cannot be empty.");
            success=false;
        } else if(!isNumeric(amount, 'i')) {
            msg.setText("Add On Total Discount: Please enter a valid integer amount >= 0 and <=100.");
            success=false;
        } else if(Integer.parseInt(amount) < 0 || Integer.parseInt(amount)>100) {
            msg.setText("Add On Total Discount: Please enter a valid integer amount >= 0 and <=100.");
            success=false;
        }

        if(success) {
            Discount d = new Discount(condition, Double.parseDouble(conditionValue), Integer.parseInt(amount));
            discountsOnTotalList.add(d);
            discountsOnTotal.getItems().add(d);
            discountsOnTotal.refresh();
            newTotalCondition.clear();
            newTotalConditionValue.clear();
            newTotalAmount.clear();
        }
    }

    @FXML
    void removeBook(ActionEvent e) {
        int selectedBook = availableBooks.getSelectionModel().getSelectedIndex();
        Book removedBook = availableBooks.getSelectionModel().getSelectedItem();
        try {
            booksList.remove(selectedBook);
            availableBooks.getItems().remove(selectedBook);
            List<Book> toRemove = new ArrayList<>();
            for(Book b: shoppingList) {
                if(b.getBookTitle().equals(removedBook.getBookTitle())) {
                    toRemove.add(b);
                }
             }
            for(Book b: toRemove) {
                shoppingList.remove(b);
            }
            availableBooks.refresh();
            msg.setText("Remove Book: Removed book successfully.");
        } catch(IndexOutOfBoundsException ex) {
            msg.setText("Remove Book: Please select a book to remove.");
        }
    }


    @FXML
    void removeBookDiscount(ActionEvent e) {
        int selectedBook = discountsPerBook.getSelectionModel().getSelectedIndex();
        try {
            discountsPerBookList.remove(selectedBook);
            discountsPerBook.getItems().remove(selectedBook);
            discountsPerBook.refresh();
            msg.setText("Remove Per Book Discount: Removed discount successfully.");
        } catch(IndexOutOfBoundsException ex) {
            msg.setText("Remove Per Book Discount: Please select a discount to remove.");
        }
    }

    @FXML
    void removeTotalDiscount(ActionEvent e) {
        int selectedBook = discountsOnTotal.getSelectionModel().getSelectedIndex();
        try {
            discountsOnTotalList.remove(selectedBook);
            discountsOnTotal.getItems().remove(selectedBook);
            discountsOnTotal.refresh();
            msg.setText("Remove On Total Discount: Removed discount successfully.");
        } catch(IndexOutOfBoundsException ex) {
            msg.setText("Remove On Total Discount: Please select a discount to remove.");
        }
    }

    private static boolean isNumeric(String s, char type) {
        try {
            if(type == 'i') {
                Integer.parseInt(s);
            } else if(type == 'd') {
                Double.parseDouble(s);
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private void setupBooksList() {
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookTitleCol.setOnEditCommit(t -> {
            if(!t.getNewValue().isEmpty()) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setBookTitle(t.getNewValue());
            } else {
                msg.setText("Modify Book Title: Please enter a valid title.");
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setBookTitle(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        bookYearCol.setCellValueFactory(new PropertyValueFactory<>("bookYear"));
        bookYearCol.setCellFactory(TextFieldTableCell.forTableColumn(
                new IntegerStringConverter(){
                    public Integer fromString(String s) {
                        try {
                            return Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            msg.setText("Modify Book Year: Please enter a valid year > 0.");
                        }
                        return -1;
                    }

                    public String toString(Integer i) {
                        return Integer.toString(i);
                    }
                }
                )
        );
        bookYearCol.setOnEditCommit(t -> {
            if(t.getNewValue() > 0) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setBookYear(t.getNewValue());
            } else {
                msg.setText("Modify Book Year: Please enter a valid year > 0.");
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setBookYear(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        bookPriceCol.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        bookPriceCol.setOnEditCommit(t -> {
            if(t.getNewValue() >= 0) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setBookPrice(t.getNewValue());
            } else {
                msg.setText("Modify Book Price: Please enter a valid price >= 0.");
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setBookPrice(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        bookPriceCol.setCellFactory(col -> new TextFieldTableCell<Book, Double>(
                new DoubleStringConverter(){
                    public Double fromString(String s) {
                        try {
                            return Double.parseDouble(s);
                        } catch (NumberFormatException e) {
                            msg.setText("Modify Book Price: Please enter a valid price >= 0.");
                        }
                        return -1.0;
                    }

                    public String toString(Double d) {
                        return Double.toString(d);
                    }
                }
        ) {
            @Override
            public void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        availableBooks.getItems().setAll(booksList);
        availableBooks.requestFocus();
        availableBooks.getSelectionModel().select(0);
        availableBooks.getFocusModel().focus(0);
        availableBooks.setPlaceholder(new Label("You have no books available"));
        availableBooks.setOnMouseClicked(e -> availableBooks.refresh());
    }

    private void setupDiscountsPerBookList() {
        conditionBookCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        conditionBookCol.setCellFactory(TextFieldTableCell.forTableColumn());
        conditionBookCol.setOnEditCommit(t -> {
            if(t.getNewValue().matches("<|<=|>|>=|==")) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setCondition(t.getNewValue());
            } else {
                msg.setText("Modify Discount Condition: Please enter <, <=, >, >= or ==.");
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setCondition(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        conditionValueBookCol.setCellValueFactory(new PropertyValueFactory<>("conditionValue"));
        conditionValueBookCol.setOnEditCommit(t -> {
            if(t.getNewValue() >= 0) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setConditionValue(t.getNewValue());
            } else {
                msg.setText("Modify Discount Condition Value: Please enter a valid value > 0.");
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setConditionValue(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        conditionValueBookCol.setCellFactory(col -> new TextFieldTableCell<Discount, Double>(
                new DoubleStringConverter(){
                    public Double fromString(String s) {
                        try {
                            return Double.parseDouble(s);
                        } catch (NumberFormatException e) {
                            msg.setText("Modify Discount Condition Value: Please enter a valid value > 0.");
                        }
                        return -1.0;
                    }

                    public String toString(Double d) {
                        return Double.toString(d);
                    }
                }
        ) {
            @Override
            public void updateItem(Double conditionValue, boolean empty) {
                super.updateItem(conditionValue, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.0f",conditionValue));
                }
            }
        });
        amountBookCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountBookCol.setOnEditCommit(t -> {
            if(t.getNewValue() > 0 && t.getNewValue()<=100) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setAmount(t.getNewValue());
            } else {
                msg.setText("Modify Discount Amount: Please enter a valid amount <= 100, and > 0.");
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setAmount(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        amountBookCol.setCellFactory(col -> new TextFieldTableCell<Discount, Integer>(
                new IntegerStringConverter(){
                    public Integer fromString(String s) {
                        try {
                            return Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            msg.setText("Modify Discount Amount: Please enter a valid amount <= 100, and > 0.");
                        }
                        return -1;
                    }

                    public String toString(Integer i) {
                        return Integer.toString(i);
                    }
                }
        ) {
            @Override
            public void updateItem(Integer amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%d%%",amount));
                }
            }
        });
        discountsPerBook.getItems().setAll(discountsPerBookList);
        discountsPerBook.setPlaceholder(new Label("You have no discounts per book available"));
        discountsPerBook.setOnMouseClicked(e -> discountsPerBook.refresh());
    }

    private void setupDiscountsOnTotalList() {
        conditionTotalCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        conditionTotalCol.setCellFactory(TextFieldTableCell.forTableColumn());
        conditionTotalCol.setOnEditCommit(t -> {
            if(t.getNewValue().matches("<|<=|>|>=|==")) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setCondition(t.getNewValue());
            } else {
                msg.setText("Modify Discount Condition: Please enter <, <=, >, >= or ==.");
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setCondition(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        conditionValueTotalCol.setCellValueFactory(new PropertyValueFactory<>("conditionValue"));
        conditionValueTotalCol.setOnEditCommit(t -> {
            if(t.getNewValue() > 0) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setConditionValue(t.getNewValue());
            } else {
                msg.setText("Modify Discount Condition Value: Please enter a valid value > 0.");
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setConditionValue(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        conditionValueTotalCol.setCellFactory(col -> new TextFieldTableCell<Discount, Double>(
                new DoubleStringConverter(){
                    public Double fromString(String s) {
                        try {
                            return Double.parseDouble(s);
                        } catch (NumberFormatException e) {
                            msg.setText("Modify Discount Condition Value: Please enter a valid value > 0.");
                        }
                        return -1.0;
                    }

                    public String toString(Double d) {
                        return Double.toString(d);
                    }
                }
        ) {
            @Override
            public void updateItem(Double conditionValue, boolean empty) {
                super.updateItem(conditionValue, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f",conditionValue));
                }
            }
        });
        amountTotalCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountTotalCol.setOnEditCommit(t -> {
            if(t.getNewValue() > 0 && t.getNewValue()<=100) {
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount(t.getNewValue());
            } else {
                msg.setText("Modify Discount Amount: Please enter a valid amount <= 100, and > 0.");
                (t.getTableView().getItems().get(t.getTablePosition().getRow())).setAmount(t.getOldValue());
                t.getTableView().refresh();
            }
        });
        amountTotalCol.setCellFactory(col -> new TextFieldTableCell<Discount, Integer>(
                new IntegerStringConverter(){
                    public Integer fromString(String s) {
                        try {
                            return Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            msg.setText("Modify Discount Amount: Please enter a valid amount <= 100, and > 0.");
                        }
                        return -1;
                    }

                    public String toString(Integer i) {
                        return Integer.toString(i);
                    }
                }
        ) {
            @Override
            public void updateItem(Integer amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%d%%",amount));
                }
            }
        });
        discountsOnTotal.getItems().setAll(discountsOnTotalList);
        discountsOnTotal.setPlaceholder(new Label("You have no discounts on total available"));
        discountsOnTotal.setOnMouseClicked(e -> discountsOnTotal.refresh());
    }

}
