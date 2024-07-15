package run.halo.bark.strategy;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.halo.app.core.extension.content.Comment;
import run.halo.app.core.extension.content.Post;
import run.halo.app.extension.ExtensionClient;
import run.halo.bark.domain.NotifyMe;
import run.halo.bark.domain.PushDo;
import run.halo.bark.event.NotifyBaseEvent;
import run.halo.bark.util.BarkPushUtils;

/**
 * Copyright (c) 2022 wly Corporation.
 *
 * @author 张杰 <zhangjie@prevailcloud.com>
 * @brief 评论监听器
 * @date 2024/07/12
 * @history
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CommentStrategy implements NotifyStrategy {
    private final ExtensionClient client;
    @Override
    public void process(NotifyBaseEvent event, NotifyMe setting) {
        Comment commentInfo = (Comment) event.getExtension();
        Comment.CommentSpec commentSpec = commentInfo.getSpec();
        if (!commentSpec.getOwner().getName().equals("admin")
            && commentInfo.getMetadata().getDeletionTimestamp()
            == null) {  // 这里有点坑爹，删除文章了也发通知删除评论通知
            // 不是管理员的话就推送通知
            String postMetaName = commentSpec.getSubjectRef().getName();
            Optional<Post> postInfo = client.fetch(Post.class, postMetaName);
            postInfo.ifPresent(post -> {
                if (!commentSpec.getOwner().getName().equals(post.getSpec().getOwner())) {
                    // 文章作者自己回复的不推送通知
                    if (!commentSpec.getApproved()) { //等待审核的
                        audits(post, setting, commentSpec);
                    } else {
                        publish(post, setting, commentSpec);
                    }
                }
            });
        }
    }

    private void publish(Post post, NotifyMe setting, Comment.CommentSpec commentSpec) {
        String title = String.format("《%s》上有新评论发布", post.getSpec().getTitle());
        if (setting.getCommentStatus()) {
            if (setting.getWechatStatus()) {
                title = "你有新评论"; // 微信模板消息anpush做了限制，不能太长
            }
            String content = String.format("%s说: %s \n\n[查看评论](%s)",
                commentSpec.getOwner().getDisplayName(),
                commentSpec.getContent(),
                setting.getSiteUrl() + post.getStatus().getPermalink()
            );
            push(PushDo.builder().url(setting.getSiteUrl() + post.getStatus().getPermalink())
                .title(title).content(content).setting(setting).build());
        }
    }

    private void audits(Post post, NotifyMe setting, Comment.CommentSpec commentSpec) { // 评论审核通知
        if (setting.getCommentAuditsStatus()) {
            String title = String.format("《%s》上有新评论等待审核", post.getSpec().getTitle());
            if (setting.getWechatStatus()) {
                title = "新评论待审核"; // 微信模板消息anpush做了限制，不能太长
            }
            String content = String.format("%s说: %s \n\n[现在去审核](%s)",
                commentSpec.getOwner().getDisplayName(),
                commentSpec.getContent(),
                setting.getSiteUrl() + "/console/comments"
            );

            push(PushDo.builder().url(setting.getSiteUrl() + "/console/comments")
                .title(title).content(content).setting(setting).build());
        }
    }

    private void push(PushDo pushDo) { // 推送
        BarkPushUtils.push(pushDo);
    }
}
