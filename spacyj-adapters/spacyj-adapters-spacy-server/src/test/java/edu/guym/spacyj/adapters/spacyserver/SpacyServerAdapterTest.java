package edu.guym.spacyj.adapters.spacyserver;

import com.google.gson.*;
import edu.guym.spacyj.api.containers.TokenData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SpacyServerAdapterTest {

    @Test
    void name() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
                URI.create("http://127.0.0.1:8080/pos"))
                .header("accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\n" +
                                "  \"text\": \"  They  ate the pizza with anchovies. And they liked it.  \"\n" +
                                "}"
                ))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<TokenData> tokenData = parseResponse(response.body());

        System.out.println(tokenData);
    }

    private List<TokenData> parseResponse(String body) {

        List<TokenData> result = new ArrayList<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement json = JsonParser.parseString(body);

        JsonArray data = json.getAsJsonObject().get("data").getAsJsonArray();
        for (JsonElement sentence : data) {
            String text = sentence.getAsJsonObject().get("text").getAsString();
            JsonArray tags = sentence.getAsJsonObject().get("tags").getAsJsonArray();
            boolean isSentStart = true;
            for (JsonElement spacyToken : tags) {
                JsonObject tagObject = spacyToken.getAsJsonObject();

                String tokenText = tagObject.get("text").getAsString();
                String whitespaceAfter = tagObject.get("whitespace").getAsString();
                int index = tagObject.get("index").getAsInt();
                int charOffset = tagObject.get("char_offset").getAsInt();
                int endOffset = charOffset + tokenText.length();
                String tag = tagObject.get("tag").getAsString();
                String pos = tagObject.get("pos").getAsString();
                int headIndex = tagObject.get("head_index").getAsInt();
                String dep = tagObject.get("dep").getAsString();
                String lemma = tagObject.get("lemma").getAsString();

                TokenData token = TokenData.builder()
                        .setText(tokenText)
                        .setAfter(whitespaceAfter)
                        .setBeginOffset(charOffset)
                        .setEndOffset(endOffset)
                        .setPos(pos)
                        .setTag(tag)
                        .setHead(headIndex)
                        .setDependency(dep)
                        .setIndex(index)
                        .setLemma(lemma)
                        .setSentenceStart(isSentStart)
                        .build();

                result.add(token);
                isSentStart = false;
            }
        }

        return result;
    }
}
