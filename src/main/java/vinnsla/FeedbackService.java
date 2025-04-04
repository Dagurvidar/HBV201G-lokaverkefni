package vinnsla;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FeedbackService {
    private final String apiToken = "";
    private final String model = "gpt2";
    private final HttpClient client = HttpClient.newHttpClient();


    public FeedbackService() {

    }

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

    public String getApiToken() {
        return apiToken;
    }

    public String provideFeedback(String userAnswer) {
        String json = this.createJson(userAnswer);
        if (json.isBlank()) {
            return "Please provide an answer so that we may give you the best feedback we can";
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api-inference.huggingface.co/models/" + this.model))
                    .header("Authorization", "Bearer " + this.apiToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: " + response.body());
            return response.body();
        } catch (IllegalArgumentException e) {
            System.err.println("Ekki náðist í api model" + e.getMessage());
        } catch (UncheckedIOException e) {
            System.err.println("Failed to create http client" + e);
        } catch (IOException e) {
            System.err.println("Failed to communicate with client (sending or recieving): " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Operation was interrupted" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Gangi þér vel að laga þennan félagi: " + e);
        }

        return "Svar þitt lítur vel út. Reyndu samt að vera ítarlegri og bættu við dæmum.";
    }

    public String getValueFromJson(String jsonString) {
        try {
            System.out.println(jsonString);
            JSONArray array = new JSONArray(jsonString);
            JSONObject obj = array.getJSONObject(0);
            return obj.getString("generated_text");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Failed to parse feedback.";
        }
    }

    public static void main(String[] args) {
        FeedbackService service = new FeedbackService();
        String prompt = "The future of AI is";
        String feedback = service.provideFeedback(prompt);
        System.out.println(feedback);

        String extracted = service.getValueFromJson(feedback);
        System.out.println(extracted);
    }
}
