package utility.graphQL;

public class GraphQLQueryBodyTemplates {
    public static String countriesQueryBody() {
        return """
                    query Countries ($countryCode: String, $currency: String){
                        countries(filter: { code: { eq: $countryCode },  currency: { eq: $currency } }) {
                            awsRegion
                            capital
                            code
                            currencies
                            currency
                            emoji
                            emojiU
                            name
                            native
                            phone
                            phones
                        }
                    }
                            
                """.stripIndent();
    }

    public static String languagesQueryBody() {
        return """
                    query Languages($languageCode: String) {
                         languages(filter: { code: { eq: $languageCode } }) {
                             code
                             name
                             native
                         }
                     }
                          
                """.stripIndent();
    }
}
