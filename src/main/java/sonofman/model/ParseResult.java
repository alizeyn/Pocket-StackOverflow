package sonofman.model;

import lombok.Data;

import java.util.List;

@Data
public class ParseResult {

    private Question question;
    private List<Answer> answers;
}
