package ru.evatablepvp.elobby;

import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.*;

/**
 * Основной слушатель плагина ELobby.
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class LobbyListener implements Listener {
    private final Main plugin;

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getPlayer().getWorld())) {
            if (e.getAction() == Action.PHYSICAL) {
                e.setCancelled(true);
            } else if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                    && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                            || !e.getPlayer().hasPermission("elobby.modify"))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (e.getCause() == DamageCause.VOID && plugin.getVoidTeleports().containsKey(e.getEntity().getWorld())) {
                e.setCancelled(true);
                e.getEntity().teleport(plugin.getVoidTeleports().get(e.getEntity().getWorld()));
            } else if (plugin.getProtectedWorlds().contains(e.getEntity().getWorld())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void foodLevelChangeEvent(FoodLevelChangeEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getEntity().getWorld())) {
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void playerDropItemEvent(PlayerDropItemEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getPlayer().getWorld())) {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void playerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerInteractAtEntityEvent(PlayerInteractAtEntityEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        if (plugin.getProtectedWorlds().contains(e.getEntity().getWorld())) {
            e.setDeathMessage(null);
            e.setDroppedExp(0);
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent e) {
        if (plugin.getWorldBounds().containsKey(e.getTo().getWorld())) {
            WorldBound wb = plugin.getWorldBounds().get(e.getTo().getWorld());
            if (!wb.isInBound(e.getTo())) {
                e.setTo(wb.getExit());
            }
        }
    }
}
