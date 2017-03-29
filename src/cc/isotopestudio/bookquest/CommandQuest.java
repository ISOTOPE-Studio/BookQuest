package cc.isotopestudio.bookquest;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandQuest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("quest")) {
            Player player = (Player) sender;
//            if (!player.hasPermission("evo.admin")) {
//                player.sendMessage(S.toPrefixRed("你没有权限"));
//                return true;
//            }
            if (args.length < 1) {
                return true;
            }
            if (args[0].equalsIgnoreCase("set") && args.length >= 3) {
                return true;
            }

            return true;
        }
        return false;
    }
}
