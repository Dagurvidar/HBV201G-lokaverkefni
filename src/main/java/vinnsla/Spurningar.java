package vinnsla;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class Spurningar {
    private final ObservableList<String> flokkar = FXCollections.observableArrayList();
    private final ObservableList<String> taekniSpurningar = FXCollections.observableArrayList();
    private final ObservableList<String> forritunarSpurningar = FXCollections.observableArrayList();
    private final Map<String, ObservableList<String>> spurningar = new HashMap<>();
    private final SimpleStringProperty fjoldiSvaradraSpurninga = new SimpleStringProperty("0");

    public Spurningar() {
        String flokkur1 = "Tækni";
        String flokkur2 = "forritun";
        flokkar.addAll(flokkur1, flokkur2);

        String taekniSpurning1 = "What is the programming language developed by James Gosling " +
                "at Sun Microsystems and named after the type of coffee from Indonesia?";
        String taekniSpurning2 = "One Terrabyte is how many Megabytes?";
        String taekniSpurning3 = "When a password is limited strictly to numeric characters, " +
                "the secret is often referred to as a PIN. What does that acronym stand for?";
        taekniSpurningar.addAll(taekniSpurning1, taekniSpurning2, taekniSpurning3);

        String forritunSpurning1 = "What does the \"final\" keyword do in Java?";
        String forritunSpurning2 = "Hvenær er best að nota endurkvæmni í forritun og hvenær er " +
                "betra að nota ítraða lausn?";
        String forritunSpurning3 = "How does the \"finally\" keyword work in a try-catch-finally block in Java?";
        forritunarSpurningar.addAll(forritunSpurning1, forritunSpurning2, forritunSpurning3);

        spurningar.put(flokkur1, taekniSpurningar);
        spurningar.put(flokkur2, forritunarSpurningar);
    }

    public String getFjoldiSvaradraSpurninga() {
        return fjoldiSvaradraSpurninga.get();
    }

    public SimpleStringProperty getFjoldiSvaradraSpurningaProperty() {
        return fjoldiSvaradraSpurninga;
    }

    public ObservableList<String> getSpurningarByCategory(String key) {
        return spurningar.get(key);
    }

    public ObservableList<String> getFlokkar() {
        return flokkar;
    }

    public static void main(String[] args) {
        Spurningar spurningar = new Spurningar();
        System.out.println (spurningar.getFjoldiSvaradraSpurninga());
        System.out.println (spurningar.getFjoldiSvaradraSpurninga());
        ObservableList<String> flokkur1 = spurningar.getFlokkar();
        System.out.println(flokkur1.getFirst());
        System.out.println(spurningar.getSpurningarByCategory(flokkur1.get(0)));
    }
}
