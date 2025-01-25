package guru.springframework.springaiintro.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.Question;
import guru.springframework.springaiintro.services.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:john
 * Date:21/01/2025
 * Time:03:26
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer getAnswer(@RequestBody Question question) {
        log.info("Received question: {}", question.question());
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest request) {
        log.info("Received capital request: {}", request);
        return openAIService.getCapital(request);
    }

    @PostMapping("/capital-info")
    public Answer getCapitalInfo(@RequestBody GetCapitalRequest request) {
        log.info("Received capital info request for: {}", request);
        return openAIService.getCapitalWithInfo(request);
    }

    @PostMapping("/capital-json")
    public Answer getCapitalJSON(@RequestBody GetCapitalRequest request) {
        log.info("Received capital JSON request for: {}", request);
        return openAIService.getCapitalJSON(request);
    }

    @PostMapping("/capital-json-node")
    public JsonNode getCapitalJSONNode(@RequestBody GetCapitalRequest request) {
        log.info("Received capital JSON node request for: {}", request);
        return openAIService.getCapitalJSONNode(request);
    }

    @PostMapping("capital-json-schema")
    public GetCapitalResponse getCapitalJSONResponse(@RequestBody GetCapitalRequest request) {
        log.info("Received capital JSON response request for: {}", request);
        return openAIService.getCapitalJSONSchema(request);
    }

}
