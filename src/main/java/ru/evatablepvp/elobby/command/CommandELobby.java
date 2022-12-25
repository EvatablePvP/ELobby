package ru.evatablepvp.elobby.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.base.Joiner;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import ru.evatablepvp.elobby.Main;

/**
 * Класс, отвечающий за обработку команды /elobby
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class CommandELobby implements CommandExecutor {
    private final Main main;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("elobby.reload")) {
            main.loadConfig();
            sender.sendMessage(ChatColor.AQUA + "Конфиг перезагружен!");
            return true;
        }
        String n = main.getDescription().getName();
        String v = main.getDescription().getVersion();
        String a = Joiner.on(", ").join(main.getDescription().getAuthors());
        sender.sendMessage(ChatColor.RED + n + ChatColor.AQUA + " версии " + ChatColor.RED + v + ChatColor.AQUA + " Создатели: " + ChatColor.RED + a);
        if (sender.hasPermission("elobby.reload")) {
            sender.sendMessage(ChatColor.AQUA + "Перезагрузить конфиг: " + ChatColor.RED + "/elobby reload");
        }
        return true;
    }
}
