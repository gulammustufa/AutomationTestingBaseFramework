package utility.graphQL;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryVariables {
    private String countryCode;
    private String currency;
    private String languageCode;
}
