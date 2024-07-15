import {definePlugin} from "@halo-dev/console-shared";
import BarkSetting from "./views/BarkSetting.vue";
import {IconPlug} from "@halo-dev/components";
import {markRaw} from "vue";

export default definePlugin({
  name: "BarkNotifyPlugin",
  components: {},
  routes: [
    {
      parentName: "Root",
      route: {
        path: "/notifyMe",
        name: "Bark推送",
        component: BarkSetting,
        meta: {
          title: "Bark推送",
          searchable: true,
          menu: {
            name: "Bark推送",
            group: "tool",
            icon: markRaw(IconPlug),
            priority: 0,
          },
        },
      },
    },
  ],
  extensionPoints: {},
});
