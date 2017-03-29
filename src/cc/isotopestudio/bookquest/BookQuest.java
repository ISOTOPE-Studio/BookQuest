package cc.isotopestudio.bookquest;

import cc.isotopestudio.bookquest.task.UpdateConfigTask;
import cc.isotopestudio.bookquest.util.PluginFile;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class BookQuest extends JavaPlugin {

    private static final String pluginName = "BookQuest";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("任务").append("]").append(ChatColor.RED).toString();

    public PluginFile config;
    public PluginFile questFile;

    @Override
    public void onEnable() {
        config = new PluginFile(this, "config.yml", "config.yml");

        new UpdateConfigTask().runTask(this);

        getLogger().info(pluginName + "成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

}
