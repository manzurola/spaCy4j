package edu.guym.spacyj.adapters.spacyserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.guym.spacyj.api.SpacyAdapter;
import edu.guym.spacyj.api.containers.TokenData;
import edu.guym.spacyj.api.exceptions.SpacyException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Spacy Adapter for <a href="https://github.com/neelkamath/spacy-server">spaCy server</a>.
 * <p>
 * Creates {@link TokenData} from the result of the {@code "/pos"} endpoint.
 */
public class SpacyServerAdapter implements SpacyAdapter {

    private final URI uri;
    private final HttpClient client;

    private SpacyServerAdapter(String httpProtocol, String httpHost, int httpPort) {
        this.uri = URI.create(String.format("%s://%s:%d/pos", httpProtocol, httpHost, httpPort));
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Create a SpacyServerAdapter with {@code http}, {@code localhost} and {@code 8080} as default params.
     */
    public static SpacyServerAdapter create() {
        return new SpacyServerAdapter("http", "localhost", 8000);
    }

    /**
     * Create a SpacyServerAdapter with default http protocol and custom {@code httpHost} and {@code httpPort}.
     */
    public static SpacyServerAdapter create(String httpHost, int httpPort) {
        return new SpacyServerAdapter("http", httpHost, httpPort);
    }

    /**
     * Create a SpacyServerAdapter with custom {@code httpProtocol}, {@code httpsHost} and {@code httpPort}.
     */
    public static SpacyServerAdapter create(String httpProtocol, String httpHost, int httpPort) {
        return new SpacyServerAdapter(httpProtocol, httpHost, httpPort);
    }

    @Override
    public List<TokenData> nlp(String text) throws SpacyException {
        var request = HttpRequest.newBuilder(uri)
                .header("accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(String.format("{\"text\":\"%s\"}", text)))
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fromJson(response.body());
    }

    private List<TokenData> fromJson(String body) {

        List<TokenData> result = new ArrayList<>();

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
                boolean isPunct = tagObject.get("is_punct").getAsBoolean();
                boolean likeNum = tagObject.get("like_num").getAsBoolean();

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
                        .setIsPunct(isPunct)
                        .setLikeNum(likeNum)
                        .build();

                result.add(token);
                isSentStart = false;
            }
        }

        return result;
    }
}
