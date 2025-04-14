package vinnsla;


import io.github.cdimascio.dotenv.Dotenv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
/******************************************************************************
 *  Nafn    : Rúnar Þór Árnason og Dagur Ingi Viðar
 *  T-póstur: rta3@hi.is, div6@hi.is
 *
 *  Lýsing  : Þessi klasi sér um samskipti við OpenAI API til að:
 *            1. Gefa notanda viðbrögð við svörum hans við atvinnuviðtalsspurningum.
 *            2. Búa til nýjar spurningar út frá notendaþema með hjálp AI.
 *            Nýtir OpenAI GPT-3.5 Turbo API með JSON skilaboðasniði og OkHttp
 *            fyrir HTTP-samskipti.
 *
 *****************************************************************************/
public class FeedbackService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * Skilar stuttu viðbragði við svörum notanda með hjálp AI.
     *
     * @param question Spurning sem notandi svaraði
     * @param answer   Svar sem notandi gaf
     * @return Stutt og uppbyggilegt viðbragð frá AI
     * @throws IOException ef eitthvað fer úrskeiðis í samskiptum við API
     */
    public static String provideFeedback(String question,String answer ) throws IOException {
        if (answer == null || answer.trim().isEmpty()) {
            return "Your answer is empty!";
        }
        return getAIResponse(question, answer);
    }

    /**
     * Sendir spurningu og svar til OpenAI API og sækir viðbragð frá gervigreind.
     *
     * @param question Spurning úr viðtali
     * @param answer   Svar notanda
     * @return Stutt viðbragð frá AI byggt á svari
     * @throws IOException ef netvilla eða villa í svari frá API
     */
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
                throw new RuntimeException("Villa í svari" + response.code());
            }
            String responseBody = response.body().string();
            JSONObject jsonSvar = new JSONObject(responseBody);
            return jsonSvar.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content");
        }
    }


    /**
     * Býr til nýjan spurningaflokk út frá umræðuefni sem notandi gefur.
     * Nýtir OpenAI til að fá 5 spurningar í listaformi.
     *
     * @param topic Efni eða þema fyrir spurningarnar
     * @return Listi af spurningum sem AI bjó til
     * @throws IOException ef villa kemur upp í samskiptum við API
     */
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
    /**
     * Debug-aðferð til að prófa samskipti við AI í keyrslu.
     *
     * @param args Engin notkun
     */
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
