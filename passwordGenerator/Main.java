import java.util.Random;

class PasswordGenerator {
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_-+=<>?";

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        // Add at least one character from each category
        password.append(getRandomCharacter(LOWERCASE_CHARACTERS, random));
        password.append(getRandomCharacter(UPPERCASE_CHARACTERS, random));
        password.append(getRandomCharacter(NUMBERS, random));
        password.append(getRandomCharacter(SPECIAL_CHARACTERS, random));

        // Generate remaining characters based on length
        for (int i = 4; i < length; i++) {
            String characterSet = getRandomCharacterSet(random);
            password.append(getRandomCharacter(characterSet, random));
        }

        // Shuffle the password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < password.length(); i++) {
            int randomIndex = random.nextInt(password.length());
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        return new String(passwordArray);
    }

    private static char getRandomCharacter(String characterSet, Random random) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }

    private static String getRandomCharacterSet(Random random) {
        int randomIndex = random.nextInt(4);
        switch (randomIndex) {
            case 0:
                return LOWERCASE_CHARACTERS;
            case 1:
                return UPPERCASE_CHARACTERS;
            case 2:
                return NUMBERS;
            case 3:
                return SPECIAL_CHARACTERS;
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        int passwordLength = 12;
        String password = generatePassword(passwordLength);
        System.out.println("Generated Password: " + password);
    }
}
  
