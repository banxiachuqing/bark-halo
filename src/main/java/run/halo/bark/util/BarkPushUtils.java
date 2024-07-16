package run.halo.bark.util;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.HttpScheme;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import run.halo.bark.domain.BarkBody;
import run.halo.bark.domain.PushDo;

/**
 * Copyright (c) 2022 wly Corporation.
 *
 * @author 张杰 <zhangjie@prevailcloud.com>
 * @brief bark推送核心
 * @date 2024/07/12
 * @history
 */
@Slf4j
public class BarkPushUtils {

    public static void push(PushDo pushDo) {

        BarkBody body = BarkBody.builder()
            .title(pushDo.getTitle())
            .body(pushDo.getContent())
            .url(pushDo.getUrl())
            .build();

        for (List<String> ids : ListUtil.split(
            Arrays.asList(pushDo.getSetting().getSubscriptions()), 10)) {
            ThreadUtil.execAsync(()->{
                for (String subscription : ids) {
                    String url = HttpScheme.HTTPS + "://" + pushDo.getSetting().getServerAddress() + "/"
                        + subscription;
                    log.info("订阅用户:{},请求链接:{}", subscription, url);
                    HttpResponse response = HttpUtil.createPost(url)
                        .body(JSON.toJSONString(body))
                        .timeout(1000).execute();
                    log.info("订阅用户{},返回:{}", subscription, response.body());
                }
            });
        }
    }
}
