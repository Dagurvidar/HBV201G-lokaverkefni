package vinnsla;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Víðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Spurningar klasi sem geymir lista af spurningum sem eru flokkaðar í
 *            tvo flokka: Viðmótsspurningar og Almennar spurningar. Hann veitir
 *            aðferðir til að fá spurningar eftir flokki og fjölda svaraðra spurninga.
 *
 *****************************************************************************/
public class Spurningar {
    private final ObservableList<String> flokkar = FXCollections.observableArrayList();
    private final Map<String, ObservableList<String>> spurningar = new HashMap<>();
    private final SimpleStringProperty fjoldiSvaradraSpurninga = new SimpleStringProperty("0");
    /**
     * Smíðar nýjan Spurningar hlut og fyllir hann með flokkunum og spurningunum.
     */
    public Spurningar() {
        flokkar.addAll("Tækni", "Forritun");

        ObservableList<String> taekniSpurningar = FXCollections.observableArrayList(
                "What is the programming language developed by James Gosling at Sun Microsystems and named after the type of coffee from Indonesia?",
                "One Terrabyte is how many Megabytes?",
                "When a password is limited strictly to numeric characters, the secret is often referred to as a PIN. What does that acronym stand for?"
        );

        ObservableList<String> forritunarSpurningar = FXCollections.observableArrayList(
                "What does the \"final\" keyword do in Java?",
                "Hvenær er best að nota endurkvæmni í forritun og hvenær er betra að nota ítraða lausn?",
                "How does the \"finally\" keyword work in a try-catch-finally block in Java?"
        );

        spurningar.put("Tækni", taekniSpurningar);
        spurningar.put("Forritun", forritunarSpurningar);
    }


    public String getFjoldiSvaradraSpurninga() {
        return fjoldiSvaradraSpurninga.get();
    }
    /**
     * Skilar lista af spurningum fyrir tiltekinn flokk.
     *
     * @param flokkur Flokkur sem notandi hefur valið (t.d. "Viðmótsspurningar").
     * @return ObservableList með spurningum sem tilheyra þeim flokki.
     */
    public ObservableList<String> getSpurningalisti(String flokkur) {
        return spurningar.getOrDefault(flokkur, FXCollections.observableArrayList());
    }

    public SimpleStringProperty fjoldiSvaradraSpurningaProperty() {
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
        System.out.println (spurningar.getSpurningalisti("Tækni").get(0));
        System.out.println (spurningar.getSpurningalisti("Forritun").get(1));
        ObservableList<String> flokkur1 = spurningar.getFlokkar();
        System.out.println(flokkur1.get(0));
        System.out.println(spurningar.getSpurningarByCategory(flokkur1.get(0)));
    }
}
