package vinnsla;

public class FeedbackService {
    public static String provideFeedback(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            return "Svarið þitt er tómt. Reyndu að útskýra betur!";
        }

        if (answer.length() < 10) {
            return "Svar þitt er heldur stutt. Reyndu að bæta við frekari útskýringum.";
        }

        if (answer.toLowerCase().contains("final")) {
            return "Gott! \"final\" í Java gerir breytur óbreytanlegar. " +
                    "Þú gætir einnig nefnt muninn áf \"final\" og \"static\".";
        }

        if (answer.toLowerCase().contains("recursion")) {
            return "Flott! Endurkvæmni er gagnleg fyrir ýmis vandamál eins og Fibonacci eða trjáleit. " +
                    "Prufaðu að útskýra grunnaðferðir betur.";
        }

        if (answer.toLowerCase().contains("exception")) {
            return "Vel gert! Try-catch er mikilvægt í villumeðhöndlun. " +
                    "Þú gætir útskýrt ýtarlegar hvernig finally blokk virkar.";
        }

        if (answer.toLowerCase().contains("1000000")) {
            return "Gott! það eru einmitt 1.000.000 Megabæt í einu Terabæti!";
        }

        return "Svar þitt lítur vel út. Reyndu samt að vera ítarlegri og bættu við dæmum.";
    }

    public static void main(String[] args) {
        String userResponse = "I am a team player and I solve problems efficiently.";
        System.out.println(provideFeedback(userResponse));
    }
}
