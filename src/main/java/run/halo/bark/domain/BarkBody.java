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
    @Builder.Default
    private String sound = "shake";
    @Builder.Default
    private String icon="https://photo.tuchong.com/18681/f/26970923.jpg";
    private String group;
    private String url;
}
