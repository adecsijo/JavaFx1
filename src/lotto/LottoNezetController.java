package lotto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class LottoNezetController implements Initializable {

    private static final int MAX = 90;
    private static final int MIN = 1;
    private static final String WIN0 = "Eredmény: Semmid nincs!";
    private static final String WIN1 = "Eredmény: Egyesed van!";
    private static final String WIN2 = "Eredmény: Kettesed van!";
    private static final String WIN3 = "Eredmény: Hármasod van!";
    private static final String WIN4 = "Eredmény: Négyesed van!";
    private static final String WIN5 = "Eredmény: Ötösöd van!";
    private static final String WARNING1 = "Nem szerepelhet kétszer ugyanaz a szám!";
    private static final String WARNING2 = "Minden számnak 1 és 90 között kell lennie!";
    private static final String WARNING3 = "Nem adtál meg minden számot!";

    @FXML
    private Pane basePane;
    @FXML
    private Pane alertPane;
    @FXML
    private Label alertText;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label eredmeny;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private TextField input3;
    @FXML
    private TextField input4;
    @FXML
    private TextField input5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init");
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < 5) {
            generatedNumbers.add(getRandomNumber());
        }
        List<Integer> genNumberList = new ArrayList<>(generatedNumbers);
        calculate(genNumberList);
        if (!genNumberList.isEmpty()) {
            label1.setText(genNumberList.get(0).toString());
            label2.setText(genNumberList.get(1).toString());
            label3.setText(genNumberList.get(2).toString());
            label4.setText(genNumberList.get(3).toString());
            label5.setText(genNumberList.get(4).toString());
        }
    }

    @FXML
    private void okHandle(ActionEvent event) {
        basePane.setDisable(false);
        basePane.setOpacity(1);
        alertPane.setVisible(false);
        alertText.setText("");
    }

    private void calculate(List<Integer> genList) {
        Set<Integer> selNumbers = new HashSet<>();
        try {
            selNumbers.add(Integer.parseInt(input1.getText()));
            selNumbers.add(Integer.parseInt(input2.getText()));
            selNumbers.add(Integer.parseInt(input3.getText()));
            selNumbers.add(Integer.parseInt(input4.getText()));
            selNumbers.add(Integer.parseInt(input5.getText()));
            if (selNumbers.size() != 5) {
                genList.clear();
                alert(WARNING1);
                return;
            }
            if (selNumbers.stream().anyMatch(s -> s < MIN || s > MAX)) {
                genList.clear();
                alert(WARNING2);
                return;
            }
            resultCheck((int) genList.stream().filter(selNumbers::contains).count());
        } catch (Exception e) {
           genList.clear();
           alert(WARNING3);
        }
    }

    private void resultCheck(int result) {
        switch (result) {
            case 5 : eredmeny.setText(WIN5); break;
            case 4 : eredmeny.setText(WIN4); break;
            case 3 : eredmeny.setText(WIN3); break;
            case 2 : eredmeny.setText(WIN2); break;
            case 1 : eredmeny.setText(WIN1); break;
            case 0 : eredmeny.setText(WIN0); break;
        }
    }

    private void alert(String string) {
        alertPane.setVisible(true);
        basePane.setOpacity(0.3);
        basePane.setDisable(true);
        alertText.setText(string);
    }

    private int getRandomNumber() {
        return (int) (Math.random() * MAX) + MIN;
    }
}
