package dtos;

import java.io.Serializable;
import java.util.Objects;

public class AnswerDTO implements Serializable {
    private Integer indexRound;
    private String word;

    public AnswerDTO() {
    }

    public AnswerDTO(Integer indexRound, String word) {
        this.indexRound = indexRound;
        this.word = word;
    }

    public Integer getIndexRound() {
        return indexRound;
    }

    public void setIndexRound(Integer indexRound) {
        this.indexRound = indexRound;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDTO answerDTO = (AnswerDTO) o;
        return Objects.equals(indexRound, answerDTO.indexRound) &&
                Objects.equals(word, answerDTO.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexRound, word);
    }
}
