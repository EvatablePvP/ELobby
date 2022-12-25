package ru.evatablepvp.elobby.command;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.evatablepvp.elobby.Main;

/**
 * Класс, отвечающий за обработку команды /lobby
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class CommandLobby implements CommandExecutor {
    private final Main main;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "Вы не игрок!");
            return true;
        }
        Player p = (Player) sender;
        p.teleport(main.getLobby());
        return true;
    }
}
