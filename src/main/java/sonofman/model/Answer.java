package sonofman.model;

import lombok.Data;

@Data
public class Answer {

    private long id;
    private String body;
    private long votes;
    private boolean isAccepted;
    private String link;
    private long creationData;
}
