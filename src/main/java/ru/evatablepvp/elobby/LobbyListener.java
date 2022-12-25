/*
 * Copyright 2022 EvatablePvP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * 
 * @author iEatMeat
 */
@RequiredArgsConstructor
public class LobbyListener implements Listener {
    private final Main main;

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        e.getPlayer().teleport(main.getLobby());
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        if (main.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        if (main.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (main.getProtectedWorlds().contains(e.getPlayer().getWorld())) {
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
            if (e.getCause() == DamageCause.VOID && main.getVoidTeleports().containsKey(e.getEntity().getWorld())) {
                e.setCancelled(true);
                e.getEntity().teleport(main.getVoidTeleports().get(e.getEntity().getWorld()));
            } else if (main.getProtectedWorlds().contains(e.getEntity().getWorld())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void foodLevelChangeEvent(FoodLevelChangeEvent e) {
        if (main.getProtectedWorlds().contains(e.getEntity().getWorld())) {
            e.setFoodLevel(20);
        }
    }

    @EventHandler
    public void playerDropItemEvent(PlayerDropItemEvent e) {
        if (main.getProtectedWorlds().contains(e.getPlayer().getWorld())) {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void playerInteractEntityEvent(PlayerInteractEntityEvent e) {
        if (main.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerInteractAtEntityEvent(PlayerInteractAtEntityEvent e) {
        if (main.getProtectedWorlds().contains(e.getPlayer().getWorld())
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                        || !e.getPlayer().hasPermission("elobby.modify"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        if (main.getProtectedWorlds().contains(e.getEntity().getWorld())) {
            e.setDeathMessage(null);
            e.setDroppedExp(0);
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent e) {
        if (main.getWorldBounds().containsKey(e.getTo().getWorld())) {
            WorldBound wb = main.getWorldBounds().get(e.getTo().getWorld());
            if (!wb.isInBound(e.getTo())) {
                e.setTo(wb.getExit());
            }
        }
    }
}
