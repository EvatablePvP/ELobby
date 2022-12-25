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

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import lombok.Value;

/**
 * Инстнация границы мира
 * 
 * @author iEatMeat
 */
@Value
public class WorldBound {
    double x1;
    double z1;
    double x2;
    double z2;
    Location exit;

    /**
     * Проверяет, находится ли данная локация внутри границы
     * 
     * @param l Данная локация
     * @return <code>true</code>, если данная локация находится внутри границы,
     *         иначе <code>false</code>
     * @throws NullPointerException Если <code>l</code> является <code>null</code>
     */
    public boolean isInBound(Location l) {
        return l.getX() >= x1 && l.getZ() >= z1 && l.getX() <= x2 && l.getZ() <= z2;
    }

    /**
     * Загружает границу мира из секции конфигурации
     * 
     * @param c Данная секция конфигурации
     * @return Загруженная граница мира
     * @throws NullPointerException Если <code>c</code> является <code>null</code>
     */
    public static WorldBound load(ConfigurationSection c) {
        double x1 = c.getDouble("x1");
        double z1 = c.getDouble("z1");
        double x2 = c.getDouble("x2");
        double z2 = c.getDouble("z2");
        Location e = c.getLocation("exit");
        return new WorldBound(x1, z1, x2, z2, e);
    }
}
