package ru.evatablepvp.elobby;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import lombok.Value;

/**
 * Инстнация границы мира
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
     * @param l Данная локация
     * @return <code>true</code>, если данная локация находится внутри границы, иначе <code>false</code>
     * @throws NullPointerException Если <code>l</code> является <code>null</code>
     */
    public boolean isInBound(Location l) {
        return l.getX() >= x1 && l.getZ() >= z1 && l.getX() <= x2 && l.getZ() <= z2;
    }

    /**
     * Загружает границу мира из секции конфигурации
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
