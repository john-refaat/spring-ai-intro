package guru.springframework.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.List;

/**
 * Author:john
 * Date:25/01/2025
 * Time:03:53
 */
public record GetCapitalResponse(@JsonPropertyDescription("This is the city name") String name,
                                 @JsonPropertyDescription("Size By Population") String population,
                                 @JsonPropertyDescription("This is the country's official language") String language,
                                 @JsonPropertyDescription("This is the country's official currency") String currency,
                                 @JsonPropertyDescription("Touristic attractions") List<String> attractions,
                                 @JsonPropertyDescription("A fun fact about the city") String funFact) {
}
