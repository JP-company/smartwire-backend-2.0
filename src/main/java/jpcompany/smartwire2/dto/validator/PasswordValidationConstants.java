package jpcompany.smartwire2.dto.validator;

public final class PasswordValidationConstants {
    static final int PASSWORD_MINIMUM_LENGTH = 10;
    static final int PASSWORD_MAXIMUM_LENGTH = 20;
    static final int COMPANY_NAME_MAXIMUM_LENGTH = 20;
    static final String BLANK = " ";
    static final String AT = "@";
    static final String DOT = ".";

    enum Ascii {
        MINIMUM_RANGE(33),
        MAXIMUM_RANGE(126),
        LOWERCASE_MINIMUM_RANGE(97),
        LOWERCASE_MAXIMUM_RANGE(122),
        UPPERCASE_MINIMUM_RANGE(65),
        UPPERCASE_MAXIMUM_RANGE(90),
        NUMBER_MINIMUM_RANGE(48),
        NUMBER_MAXIMUM_RANGE(57);

        public static final int TOTAL_NUMBERS_OF_CHARACTER_TYPE = 4;
        private static final String LOWERCASE = "lowercase";
        private static final String UPPERCASE = "uppercase";
        private static final String NUMBER = "number";
        private static final String SPECIAL_CHARACTER = "special character";

        private final int index;

        Ascii(int index) {
            this.index = index;
        }

        public static String classifyWhichCharacter(int passwordCharacter) {
            if (isLowercase(passwordCharacter)) {
                return LOWERCASE;
            }
            if (isUppercase(passwordCharacter)) {
                return UPPERCASE;
            }
            if (isNumber(passwordCharacter)) {
                return NUMBER;
            }
            return SPECIAL_CHARACTER;
        }

        public static boolean isNotInRange(int asciiCode) {
            return asciiCode < MINIMUM_RANGE.index  && asciiCode < MAXIMUM_RANGE.index;
        }

        private static boolean isLowercase(int asciiCode) {
            return asciiCode >= LOWERCASE_MINIMUM_RANGE.index
                    && asciiCode <= LOWERCASE_MAXIMUM_RANGE.index;
        }

        private static boolean isUppercase(int asciiCode) {
            return asciiCode >= UPPERCASE_MINIMUM_RANGE.index
                    && asciiCode <= UPPERCASE_MAXIMUM_RANGE.index;
        }

        private static boolean isNumber(int asciiCode) {
            return asciiCode >= NUMBER_MINIMUM_RANGE.index
                    && asciiCode <= NUMBER_MAXIMUM_RANGE.index;
        }
    }
}
