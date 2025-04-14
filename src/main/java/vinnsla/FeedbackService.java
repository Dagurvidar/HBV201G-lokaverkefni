package vinnsla;


import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class FeedbackService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
  
    public static String provideFeedback(String question,String answer ) throws IOException {
        if (answer == null || answer.trim().isEmpty()) {
            return "Your answer is empty!";
        }
        return getAIResponse(question, answer);
    }


    public static String getAIResponse(String question, String answer) throws IOException {
        OkHttpClient client = new OkHttpClient();

        answer = answer.trim().replace("\n", " ");
        question = question.trim().replace("\n", " ");

        String json = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [ {" +
                "\"role\": \"system\",\"content\": \"You are an AI that provides short feedback on job interview answers. Don't exceed 120 tokens\"" +
                "},{\"role\": \"user\"," +
                " \"content\": \"The interview question was: '" + question + "'. The candidate answered: '" + answer + "'. Give short constructive feedback.\"" +
                "} ], \"max_tokens\": 120 }";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Response error: " + response.code());
            }
            String responseBody = response.body().string();
            JSONObject jsonSvar = new JSONObject(responseBody);
            return jsonSvar.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content");
        }
    }


    public static ObservableList<String> generateAIQuestions(String topic) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String prompt = "Generate 5 job interview questions related to: " + topic;

        String json = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [ {" +
                "\"role\": \"system\",\"content\": \"You are an AI that generates concise, clear job interview questions only. Return only a numbered list without explanations.\"" +
                "},{\"role\": \"user\"," +
                " \"content\": \"" + prompt + "\"" +
                "} ], \"max_tokens\": 300 }";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Error from AI: " + response.code());
            }

            String responseBody = response.body().string();
            JSONObject jsonSvar = new JSONObject(responseBody);
            String content = jsonSvar.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            // Parse the numbered list of questions into individual strings
            String[] lines = content.split("\\n");
            ObservableList<String> questions = FXCollections.observableArrayList();

            for (String line : lines) {
                // Remove "1. ", "2. ", etc.
                questions.add(line.replaceFirst("^\\d+\\.\\s*", "").trim());
            }

            return questions;
        }
    }




    public static void main(String[] args) {
        System.out.println("Loaded API Key: " + API_KEY);  // DEBUGGING LINE
        try {
            String userquestion = "Why do you think you are a fit for this job";
            String userResponse = "I am a team player and I solve problems efficiently.";
            System.out.println("User input: " + userResponse);
            String feedback = provideFeedback(userquestion, userResponse);
            System.out.println("AI Feedback: " + feedback);
        } catch (IOException e) {
            System.err.println("Error fetching response from AI: " + e.getMessage());
        }
    }
}
