package guru.springframework.springaiintro.services;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.springaiintro.model.Answer;
import guru.springframework.springaiintro.model.GetCapitalRequest;
import guru.springframework.springaiintro.model.GetCapitalResponse;
import guru.springframework.springaiintro.model.Question;

/**
 * Author:john
 * Date:19/01/2025
 * Time:02:19
 */
public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getCapital(GetCapitalRequest request);

    Answer getCapitalWithInfo(GetCapitalRequest request);

    Answer getCapitalJSON(GetCapitalRequest request);

    JsonNode getCapitalJSONNode(GetCapitalRequest request);

    GetCapitalResponse getCapitalJSONSchema(GetCapitalRequest request);

}
