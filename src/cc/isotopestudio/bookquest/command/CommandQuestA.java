package cc.isotopestudio.bookquest.command;
/*
 * Created by Mars Tan on 3/29/2017.
 * Copyright ISOTOPE Studio
 */

import cc.isotopestudio.bookquest.element.Task;
import cc.isotopestudio.bookquest.util.S;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandQuestA implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("questaccept")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(S.toPrefixRed("���ִ�е�����"));
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 1) {
                player.getInventory().addItem(Task.tasks.get(args[0]).getBookItem());
                player.sendMessage(S.toPrefixGreen("�ɹ���ȡ����"));
            }
            return true;
        }
        return false;
    }
}
