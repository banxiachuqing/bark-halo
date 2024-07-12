package run.halo.bark.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @创建人
 * @创建时间 2024/7/12
 * @备注
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarkBody {

    private String body;
    private String title;
    private int badge;
    private String category;
    private String sound;
    private String icon;
    private String group;
    private String url;
}
