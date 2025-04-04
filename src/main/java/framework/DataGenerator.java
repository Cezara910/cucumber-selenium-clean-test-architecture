package framework;

import net.datafaker.Faker;
import net.datafaker.providers.base.Text;

import static net.datafaker.providers.base.Text.*;

public class DataGenerator {

    public static String generateDigitAndLetterText(int length) {
        var faker = new Faker();
        return faker.text().text(Text.TextSymbolsBuilder.builder().
                len(length)
                .with(DIGITS)
                .with(EN_LOWERCASE)
                .with(EN_UPPERCASE)
                .build());
    }
}
