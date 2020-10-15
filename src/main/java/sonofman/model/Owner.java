package sonofman.model;

import lombok.Data;

@Data
public class Owner {

    private long reputation;
    private long userId;
    private String link;
    private String displayName;
    private String profileImage;
}
