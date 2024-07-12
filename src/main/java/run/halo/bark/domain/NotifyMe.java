package run.halo.bark.domain;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;

@Data
@EqualsAndHashCode(callSuper = true)
@GVK(kind = "NotifyMe", group = "run.halo.bark.notifyme",
    version = "v1alpha1", singular = "notifyme", plural = "notifymes")
public class NotifyMe extends AbstractExtension {

    @Schema(requiredMode = REQUIRED, minLength = 10)
    private String apiKey;

    @Schema(requiredMode = REQUIRED, minLength = 4)
    private String channel;

    @Schema(requiredMode = REQUIRED)
    private String siteUrl;  //站点文章的路径

    @Schema(defaultValue = "false")
    private Boolean status; // 通知总开关

    @Schema(defaultValue = "false")
    private Boolean wechatStatus; // 微信标记

    @Schema(defaultValue = "true")
    private Boolean commentStatus; // 评论通知开关

    @Schema(defaultValue = "false")
    private Boolean commentAuditsStatus; // 评论审核通知

    @Schema(defaultValue = "false")
    private Boolean postStatus; // 发布文章开关

    @Schema(defaultValue = "false")
    private Boolean postAuditsStatus; // 文章审核通知开关

    @Schema(defaultValue = "false")
    private Boolean postDelStatus; // 文章删除通知开关

}
