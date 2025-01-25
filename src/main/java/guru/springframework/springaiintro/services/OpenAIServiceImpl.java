package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalRequest;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalWithInfoRequest;

    @Value("classpath:templates/get-capital-JSON.st")
    private Resource getGetCapitalInJSONRequest;

    @Value("classpath:templates/get-capital-json-schema.st")
    private Resource getCapitalJSONSchemaRequest;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String getAnswer(String question) {
        log.info("Getting Answer");
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        log.info("Getting Answer for Question: {}", question);
        return new Answer(getAnswer(question.question()));
    }

    @Override
    public Answer getCapital(GetCapitalRequest request) {
        log.info("Getting Capital for: {}", request);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalRequest);
        Prompt prompt = promptTemplate.create(Map.of("countryOrState", request.countryOrState()));
        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest request) {
        log.info("Getting Capital with Info for: {}", request);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoRequest);
        Prompt prompt = promptTemplate.create(Map.of("countryOrState", request.countryOrState()));
        ChatResponse response = chatModel.call(prompt);
        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getCapitalJSON(GetCapitalRequest request) {
        log.info("Getting Capital in JSON for: {}", request);
        PromptTemplate promptTemplate = new PromptTemplate(getGetCapitalInJSONRequest);
        Prompt prompt = promptTemplate.create(Map.of("countryOrState", request.countryOrState()));
        ChatResponse response = chatModel.call(prompt);
        String content = response.getResult().getOutput().getContent();
        log.info("Capital JSON: {}", content);
        String capital;
        try {
            String json = content.substring(content.indexOf("{") - 1, content.indexOf("}") + 1);
            log.info("Parsed JSON: {}", json);
            JsonNode jsonNode = objectMapper.readTree(json);
            capital = jsonNode.get("name").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Answer(capital);
    }

    @Override
    public JsonNode getCapitalJSONNode(GetCapitalRequest request) {
        log.info("Getting Capital in JSON Node for: {}", request);
        PromptTemplate promptTemplate = new PromptTemplate(getGetCapitalInJSONRequest);
        Prompt prompt = promptTemplate.create(Map.of("countryOrState", request.countryOrState()));
        ChatResponse response = chatModel.call(prompt);
        String content = response.getResult().getOutput().getContent();
        log.info("Capital JSON Node: {}", content);

        String json = content.substring(content.indexOf("{") - 1, content.indexOf("}") + 1);
        try {
            return objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GetCapitalResponse getCapitalJSONSchema(GetCapitalRequest request) {
        BeanOutputConverter<GetCapitalResponse> output = new BeanOutputConverter<>(GetCapitalResponse.class);
        String schema = output.getJsonSchema();
        log.info("Schema: {}", schema);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalJSONSchemaRequest);
        Prompt prompt = promptTemplate.create(Map.of("countryOrState", request.countryOrState(), "format", schema));
        log.info("Calling with prompt: {}", prompt);
        ChatResponse response = chatModel.call(prompt);
        String content = response.getResult().getOutput().getContent();
        log.info("Capital JSON Response: {}", content);
        return output.convert(content);
    }

}