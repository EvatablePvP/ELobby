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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import ru.evatablepvp.elobby.command.CommandELobby;
import ru.evatablepvp.elobby.command.CommandLobby;

import java.util.Map;
import java.util.Set;

/**
 * Основной класс плагина ELobby.
 * 
 * @author iEatMeat
 */
@Getter
public class Main extends JavaPlugin {
    private Location lobby;
    private Set<World> protectedWorlds = Sets.newHashSet();
    private Map<World, Location> voidTeleports = Maps.newHashMap();
    private Map<World, WorldBound> worldBounds = Maps.newHashMap();

    @Override
    public void onEnable() {
        loadConfig();
        getCommand("elobby").setExecutor(new CommandELobby(this));
        getCommand("lobby").setExecutor(new CommandLobby(this));
        getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
    }

    /**
     * Загружает конфигурацию с диска, сохраняя стандартную конфигурацию, если
     * необходимо
     */
    public void loadConfig() {
        saveDefaultConfig();
        reloadConfig();
        lobby = getConfig().getLocation("lobby");
        protectedWorlds = Sets.newHashSet();
        for (String s : getConfig().getStringList("protectedWorlds")) {
            protectedWorlds.add(getServer().getWorld(s));
        }
        ConfigurationSection vtcs = getConfig().getConfigurationSection("voidTeleports");
        voidTeleports = Maps.newHashMap();
        for (String s : vtcs.getKeys(false)) {
            voidTeleports.put(getServer().getWorld(s), vtcs.getLocation(s));
        }
        ConfigurationSection wbcs = getConfig().getConfigurationSection("worldBounds");
        worldBounds = Maps.newHashMap();
        for (String s : wbcs.getKeys(false)) {
            worldBounds.put(getServer().getWorld(s), WorldBound.load(wbcs.getConfigurationSection(s)));
        }
    }
}
