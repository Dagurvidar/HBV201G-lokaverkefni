package vinnsla;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class FeedbackService {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    public static String provideFeedback(String answer) throws IOException {
        if (answer == null || answer.trim().isEmpty()) {
            return "Svarið þitt er tómt. Reyndu að útskýra betur!";
        }
        return getAIResponse(answer);
    }


    public static String getAIResponse(String answer) throws IOException {
        // setur upp HTTP-client svo hægt sé að senda á HTTP-server
        OkHttpClient client = new OkHttpClient();

        // fyrirspurnin sem verður send á bakendann - aðlagaðu að þínum þörfum
        String json = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": [ {" +
                "\"role\": \"system\",\"content\": \"You are an AI that provides feedback on job interview answers.\"" +
                "},{\"role\": \"user\"," +
                " \"content\": \"Give feedback on this job interview answer: " + answer + "\"" +
                "} ], \"max_tokens\": 100 }";

        // fyrirspurninni pakkað í body sem verður hluti af request
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        // beiðnin sjálf
        Request request = new Request.Builder()
                .url(API_URL)  // "https://api.openai.com/v1/chat/completions"; en best að hafa þetta efst sem fasta
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY) // API_KEY er fasti með þínum lykli
                .build();

        // Beiðnin send til HTTP-clients-ins
        try (Response response = client.newCall(request).execute()) {
            // Athuga hvort beiðnin tókst
            if (!response.isSuccessful()) {
                throw new RuntimeException("Villa í svari" + response.code()); // hér mætti birta þetta í viðmótinu, aðlagaðu
            }
            String responseBody = response.body().string();
            // Búa til JSON hlut með svarinu sem fékkst frá HTTP-client-inum
            JSONObject jsonSvar = new JSONObject(responseBody);
            // afpakka json svarið þannig að þú fáir innihaldið (content) - skoðaðu hvernig jsonSvar lítur út til að skilja
            // og senda til viðmótsins
            return jsonSvar.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content");
        }
    }

    public static void main(String[] args) {
        try {
            String userResponse = "I am a team player and I solve problems efficiently.";
            System.out.println(provideFeedback(userResponse));
        } catch (IOException e) {
            System.err.println("Villa við að fá svar frá AI: " + e.getMessage());
        }
    }
}
