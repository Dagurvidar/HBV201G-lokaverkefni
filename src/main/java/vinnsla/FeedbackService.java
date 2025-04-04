package vinnsla;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FeedbackService {

    public String createJson(String prompt) {
        if (prompt.isBlank()) {
            System.err.println("No prompt provided");
            return "";
        }
        String question = "What was the first programming language ever made";
        String userAnswer = "Uhm, I don't really know";

        String newPrompt = String.format(
                "Question: %s\\nAnswer: %s\\nABOVE IS A PROPOSED INTERVIEW QUESTION AND AN ANSWER PROVIDED BY A USER. PLEASE GIVE THE USER FEEDBACK ON HIS ANSWER, WHETHER IT IS GOOD OR NOT",
                question.replace("\"", "\\\""),
                userAnswer.replace("\"", "\\\"")
        );

        return """
                {
                    "inputs": "%s",
                    "parameters": {
                        "max_new_tokens": 100,
                        "temperature": 0.7,
                        "top_p": 0.9,
                        "do_sample": true
                    }
                }
                """.formatted(newPrompt.replace("\"", "\\\""));
    }

    public String provideFeedback(String userAnswer) {

        return "Svar þitt lítur vel út. Reyndu samt að vera ítarlegri og bættu við dæmum.";
    }

    public static void main(String[] args) {
    }
}
