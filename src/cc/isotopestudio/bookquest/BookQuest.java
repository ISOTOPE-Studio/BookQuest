package cc.isotopestudio.bookquest;

import cc.isotopestudio.bookquest.command.CommandQuest;
import cc.isotopestudio.bookquest.listener.TaskListener;
import cc.isotopestudio.bookquest.sql.SqlManager;
import cc.isotopestudio.bookquest.task.UpdateConfigTask;
import cc.isotopestudio.bookquest.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class BookQuest extends JavaPlugin {

    public static BookQuest plugin;

    private static final String pluginName = "BookQuest";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("任务").append("]").append(ChatColor.RED).toString();

    public PluginFile config;
    public PluginFile questFile;

    @Override
    public void onEnable() {
        plugin = this;

        config = new PluginFile(this, "config.yml", "config.yml");
        questFile = new PluginFile(this, "quest.yml", "quest.yml");
        questFile.setEditable(false);

        if (!SqlManager.init()) {
            getPluginLoader().disablePlugin(this);
            return;
        }

        this.getCommand("quest").setExecutor(new CommandQuest());

        Bukkit.getPluginManager().registerEvents(new TaskListener(), this);

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
