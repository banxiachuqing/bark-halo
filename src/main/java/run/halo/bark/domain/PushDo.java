package run.halo.bark.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushDo {
    private NotifyMe setting;
    private String title;
    private String content;
    private String url;
}
