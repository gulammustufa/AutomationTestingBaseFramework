package utility.graphQL;

import lombok.Data;

@Data
public class GraphQLQueryBuilder {
    private String query;
    private Object variables;
}
