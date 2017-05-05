package cc.isotopestudio.bookquest;

import cc.isotopestudio.bookquest.command.CommandQuest;
import cc.isotopestudio.bookquest.command.CommandQuestA;
import cc.isotopestudio.bookquest.command.CommandQuestD;
import cc.isotopestudio.bookquest.listener.TaskListener;
import cc.isotopestudio.bookquest.sql.SqlManager;
import cc.isotopestudio.bookquest.task.MissionFailureTask;
import cc.isotopestudio.bookquest.task.OnlineTimeTask;
import cc.isotopestudio.bookquest.task.UpdateConfigTask;
import cc.isotopestudio.bookquest.util.PluginFile;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BookQuest extends JavaPlugin {

    public static BookQuest plugin;

    private static final String pluginName = "BookQuest";
    public static final String prefix = (new StringBuilder()).append(ChatColor.GOLD).append(ChatColor.BOLD).append("[")
            .append("任务").append("]").append(ChatColor.RED).toString();

    public PluginFile config;
    public PluginFile questFile;

    // Vault
    public static Economy econ = null;

    @Override
    public void onEnable() {
        plugin = this;

        if (!setupEconomy()) {
            getLogger().severe(pluginName + "无法加载!");
            getLogger().severe("Vault未安装！");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        config = new PluginFile(this, "config.yml", "config.yml");
        questFile = new PluginFile(this, "quest.yml", "quest.yml");
        questFile.setEditable(false);

        if (!SqlManager.init()) {
            getPluginLoader().disablePlugin(this);
            return;
        }

        this.getCommand("quest").setExecutor(new CommandQuest());
        this.getCommand("questaccept").setExecutor(new CommandQuestA());
        this.getCommand("questdecline").setExecutor(new CommandQuestD());

        Bukkit.getPluginManager().registerEvents(new TaskListener(), this);

        new UpdateConfigTask().runTask(this);
        new OnlineTimeTask().runTaskTimer(this, 60, 20 * 60);
        new MissionFailureTask().runTaskTimer(this, 60, 20);

        getLogger().info(pluginName + "1.2.0 成功加载!");
        getLogger().info(pluginName + "由ISOTOPE Studio制作!");
        getLogger().info("http://isotopestudio.cc");
    }

    @Override
    public void onDisable() {
        getLogger().info(pluginName + "成功卸载!");
    }

    // Vault API
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
