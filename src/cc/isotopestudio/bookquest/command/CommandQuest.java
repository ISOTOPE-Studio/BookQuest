package cc.isotopestudio.bookquest.command;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.gui.TaskGUI;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandQuest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("quest")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("Íæ¼ÒÖ´ÐÐµÄÃüÁî"));
                return true;
            }
            Player player = (Player) sender;
            new TaskGUI(player).open(player);
            return true;
        }
        return false;
    }
}
