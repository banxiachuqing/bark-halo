package run.halo.bark.domain;

import lombok.Data;

@Data
public class PostDo {
    private String title;

    private String slug;

    private String owner;

    private String createTime;
}
