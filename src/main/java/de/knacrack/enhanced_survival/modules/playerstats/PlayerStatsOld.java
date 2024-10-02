package de.knacrack.enhanced_survival.modules.playerstats;

import de.knacrack.enhanced_survival.Main;
import de.knacrack.enhanced_survival.utils.Group;
import de.knacrack.enhanced_survival.utils.UUIDFetcher;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PlayerStatsOld {

    private static final HashMap<String, PlayerStatsOld> cache = new HashMap<>();
    public static final HashMap<String, PermissionAttachment> permissionCache = new HashMap<>();

    public static Collection<PlayerStatsOld> getAllStats() {
        return cache.values();
    }

    public static void dispose(String uuid) {
        PlayerStatsOld playerStatsOld = cache.getOrDefault(uuid, null);

        if(playerStatsOld != null) {
            playerStatsOld.save();
        }

        cache.remove(uuid);
        permissionCache.remove(uuid);
        Bukkit.getConsoleSender().sendMessage("Cache size: " + cache.size());
    }

    public static PlayerStatsOld getCharacter(String uuid) {
        if(!cache.containsKey(uuid)) {
            PlayerStatsOld playerStatsOld = new PlayerStatsOld(uuid).load();
            cache.put(uuid, playerStatsOld);
            return playerStatsOld;
        }

        return cache.get(uuid);
    }

    public static PlayerStatsOld getCharacter(UUID uuid) {
        if(uuid == null) return null;

        return getCharacter(uuid.toString());
    }

    public static PlayerStatsOld getCharacter(@NotNull Player player) {
        return getCharacter(player.getUniqueId().toString());
    }

    public static PlayerStatsOld getCharacter(@NotNull OfflinePlayer player) {
        return getCharacter(player.getUniqueId().toString());
    }

    public static PlayerStatsOld getCharacter(@NotNull CommandSender player) {
        return getCharacter(((Player) player).getUniqueId().toString());
    }

    private final String uuid;
    private Group group;

    @Getter @Setter
    private double playerMaxHealth = 20f;
    private double coins = 0d;

    PlayerStatsOld(String uniqueId) {
        this.uuid = uniqueId;
    }

    public PlayerStatsOld getStats() {
        return this;
    }

    private PlayerStatsOld load() {
        PlayerStatsOld playerStatsOld = this;

        File file = getFile();
        if(!file.exists()) {
            return playerStatsOld;
        }

        try {
            FileReader fileReader = new FileReader(file);
            playerStatsOld = Main.GSON.fromJson(fileReader, getClass());
            fileReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return playerStatsOld;
    }

    public void save() {
        File file = getFile();

        if(file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            Main.GSON.toJson(this, fileWriter);

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static File getDirectory() {
        return new File(Main.getInstance().getDataFolder().getPath() + "/stats/");
    }

    private File getFile() {
        File directory = getDirectory();
        if(!directory.mkdirs()) {
            directory.mkdir();
        }

        return new File(directory.getPath(), this.uuid + ".json");
    }

    public String getCustomName() {
        return "§r" + getGroup().getPrefix() + getName() + "§r" + getGroup().getSuffix();
    }

    private String getName() {
        UUID uniqueId = UUID.fromString(uuid);

        Player player = Bukkit.getPlayer(uniqueId);
        if(player != null) return player.getName();

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
        if(offlinePlayer.getName() != null) return offlinePlayer.getName();

        return UUIDFetcher.getName(uniqueId);
    }

    public Group getGroup() {
        if(this.group == null) return Group.PLAYER;
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
        save();
    }

    public double getCoins() {
        return this.coins;
    }

    public void setCoins(double coins) {
        if(coins < 0) return;

        this.coins = coins;
        save();
    }

    public void addCoins(double coins) {
        setCoins(this.coins + coins);
        save();
    }

    public void removeCoins(double coins) {
        setCoins(this.coins - coins);
        save();
    }

    public boolean hasEnoughCoins(double coins) {
        return coins > 0 && coins > getCoins();
    }

    public void resetCoins() {
        coins = 0.0;
        save();
    }

    public static enum StatisticCategory {

        WOODCUTTING('\u0100'),
        MINING('\u0101'),
        COMBAT('\u0102'),
        ENDURANCE('\u0103'),
        BUILDING('\u0104'),
        MOVEMENT('\u0105'),
        FARMING('\u0106'),
        EXCAVATION('\u0107'),
        COOKING('\u0108');

        private final char character;
        StatisticCategory(char character) {
            this.character = character;
        }

        public char getCharacter() {
            return this.character;
        }
    }

    private long xp_woodcutting = 0L, xp_mining = 0L, xp_combat = 0L, xp_endurance = 0L, xp_building = 0L, xp_movement = 0L, xp_farming = 0L, xp_excavation = 0L, xp_cooking = 0L;

    public void setXP(StatisticCategory category, long amount) {
        if(amount < 0) return;

        switch (category) {
            case BUILDING -> {
                xp_building = amount;
            }

            case COMBAT -> {
                xp_combat = amount;
            }

            case COOKING -> {
                xp_cooking = amount;
            }

            case ENDURANCE -> {
                xp_endurance = amount;
            }

            case EXCAVATION -> {
                xp_excavation = amount;
            }

            case FARMING -> {
                xp_farming = amount;
            }

            case MINING -> {
                xp_mining = amount;
            }

            case MOVEMENT -> {
                xp_movement = amount;
            }

            case WOODCUTTING -> {
                xp_woodcutting = amount;
            }
        }
    }

    public long getXP(StatisticCategory category) {
        switch (category) {
            case BUILDING -> {
                return xp_building;
            }

            case COMBAT -> {
                return xp_combat;
            }

            case COOKING -> {
                return xp_cooking;
            }

            case ENDURANCE -> {
                return xp_endurance;
            }

            case EXCAVATION -> {
                return xp_excavation;
            }

            case FARMING -> {
                return xp_farming;
            }

            case MINING -> {
                return xp_mining;
            }

            case MOVEMENT -> {
                return xp_movement;
            }

            case WOODCUTTING -> {
                return xp_woodcutting;
            }
        }

        return -1L;
    }

    public void addXP(StatisticCategory category, long amount) {
        setXP(category, getXP(category) + amount);
    }

    public void removeXP(StatisticCategory category, long amount) {
        setXP(category, getXP(category) + amount);
    }

    public void resetXP(StatisticCategory category) {
        setXP(category, 0L);
    }

    public boolean hasEnoughXP(StatisticCategory category, long amount) {
        return amount > 0 && getXP(category) >= amount;
    }

    // maxXP = 10000;
    // maxLevel = 100;
    // f(x) = (maxXP / maxLevel) * x
    private static final int MAX_LEVEL = 100;
    private static final long MAX_XP = 10000L;

    // we don't want to calculate that number again and again.
    private static final long A = MAX_XP / MAX_LEVEL;

    public int getLevel(StatisticCategory category) {
        int level = 0;

        while(true) {
            if(level == MAX_LEVEL) break;
            level++;

            long xpNeeded = (A * level) * level;
            if(xpNeeded > getXP(category)) break;
        }

        return level;
    }

    public boolean canLevelup(StatisticCategory category) {
        return getLevel(category) < 100;
    }

    public long getRequiredXP(StatisticCategory category) {
        int level = getLevel(category);
        return getRequiredXP(level);
    }

    public long getRequiredXP(long level) {
        if(level == MAX_LEVEL) return 0;
        return (A * level) * level;
    }

    public void setLevel(StatisticCategory category, long level) {
        setXP(category, getRequiredXP(level));
    }

    public long getCurrentXP(StatisticCategory category) {
        return getXP(category) - getRequiredXP(getLevel(category));
    }
}
