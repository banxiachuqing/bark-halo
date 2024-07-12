package run.halo.bark;

import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginWrapper;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import run.halo.app.extension.Scheme;
import run.halo.app.extension.SchemeManager;
import run.halo.app.plugin.BasePlugin;
import run.halo.bark.domain.NotifyMe;

@Component
@EnableAsync
@Slf4j
public class NotifyMePlugin extends BasePlugin {
    private final SchemeManager schemeManager;

    public NotifyMePlugin(PluginWrapper wrapper, SchemeManager schemeManager) {
        super(wrapper);
        this.schemeManager = schemeManager;
        log.info("插件初始化...");
    }

    @Override
    public void start() {
        schemeManager.register(NotifyMe.class);
        log.info("插件已启动...");
    }

    @Override
    public void stop() {
        Scheme notifyMeScheme = schemeManager.get(NotifyMe.class);
        schemeManager.unregister(notifyMeScheme);
        log.info("插件已停止...");
    }
}
