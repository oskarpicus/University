package validator;

import domain.Word;

public class WordValidator implements Validator<Long, Word> {
    @Override
    public void validate(Word entity) {
        String errors = "";
        if (!validateWord(entity)) {
            errors += "Word not long enough";
        }
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }

    private boolean validateWord(Word word) {
        return word.getWord().length() >= 6;
    }
}
