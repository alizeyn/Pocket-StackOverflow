package model;

import lombok.Data;

@Data
public class Question {

    private long id;
    private String title;
    private String body;
    private long votes;
    private boolean isAnswered;
    private String link;
    private long creationData;
}
