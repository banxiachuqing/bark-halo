package run.halo.bark.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.HttpScheme;
import lombok.extern.slf4j.Slf4j;
import run.halo.bark.domain.BarkBody;
import run.halo.bark.domain.NotifyMe;
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

    public static void push(NotifyMe setting, PushDo pushDo) {

        BarkBody body = BarkBody.builder()
            .title(pushDo.getTitle())
            .body(pushDo.getContent())
            .url("https://www.baidu.com")
            .build();


        for (String subscription : setting.getSubscriptions()) {
            String url = HttpScheme.HTTPS.toString() + "://" + setting.getServerAddress() + "/"
                + subscription;
            log.info("订阅用户:{},请求链接:{}", subscription, url);
            HttpResponse response = HttpUtil.createPost(url)
                .body(JSON.toJSONString(body))
                .timeout(1000).execute();
            log.info("订阅用户{},返回:{}", subscription, response.body());
        }
    }
}
