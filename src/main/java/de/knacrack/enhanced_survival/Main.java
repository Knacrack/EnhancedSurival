package de.knacrack.enhanced_survival;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.knacrack.enhanced_survival.commands.list.*;
import de.knacrack.enhanced_survival.items.ICustomItem;
import de.knacrack.enhanced_survival.items.list.*;
import de.knacrack.enhanced_survival.items.recipes.RegisterRecipes;
import de.knacrack.enhanced_survival.items.recipes.furnace.RawCopperBlockToCopperBlockRecipe;
import de.knacrack.enhanced_survival.items.recipes.furnace.RawGoldBlockToGoldBlockRecipe;
import de.knacrack.enhanced_survival.items.recipes.furnace.RawIronBlockToIronBlockRecipe;
import de.knacrack.enhanced_survival.items.recipes.furnace.RottenFleshToLeatherRecipe;
import de.knacrack.enhanced_survival.items.recipes.stonecutter.CobblestoneToGravel;
import de.knacrack.enhanced_survival.items.recipes.stonecutter.DirtToGravelRecipe;
import de.knacrack.enhanced_survival.items.recipes.stonecutter.GravelToSandRecipe;
import de.knacrack.enhanced_survival.items.recipes.workbench.BackpackRecipe;
import de.knacrack.enhanced_survival.listener.list.*;
import de.knacrack.enhanced_survival.modules.backpack.Backpack;
import de.knacrack.enhanced_survival.modules.playerstats.PlayerStats;
import de.knacrack.enhanced_survival.modules.playerstats.PlayerStatsOld;
import de.knacrack.enhanced_survival.modules.scoreboard.ServerScoreboard;
import de.knacrack.enhanced_survival.utils.listeners.Listener;
import de.knacrack.enhanced_survival.utils.runnables.PlayerStatsSaveRunnable;
import de.knacrack.enhanced_survival.utils.runnables.PlayerUpdateHealthRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    private static Main instance;

    private static Logger log = Logger.getLogger(Main.class.getSimpleName());

    private static Set<ICustomItem> customItemSet = new HashSet<>();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Override
    public void onEnable() {
        instance = this;

        // Listener
        Listener listener;

        new PlayerJoinListener();
        new PlayerQuitListener();
        new PlayerDieListener();

        new PlayerDamageListener();
        new PlayerChatListener();
        new EntityDestroyFarmlandListener();
        new EntityDamageListener();
        new SnowmanTurretListener();

        // Commands
        new InvseeCommand();
        new ScytheCommand();
        new GameModeCommand();
        new HealCommand();
        new EnderchestCommand();
        new IpCommand();
        new CombineEnchantmentCommand();
        new GiveCustomItem();

        new RankCommand();
        new MoneyCommand();
        new FlyCommand();

        //Items
        //Register.getInstance().enable(new Scythe());
        customItemSet.add(new FruitOfGaia());
        customItemSet.add(new AssassinsDagger());
        customItemSet.add(new Gram());
        customItemSet.add(new ScrollOfHomecoming());
        customItemSet.add(new ScrollOfCreation());
        customItemSet.add(new ScrollOfTheVoid());
        customItemSet.add(new de.knacrack.enhanced_survival.items.list.Backpack());
        customItemSet.add(new RespawnHeart());
        customItemSet.add(new TimberAxe());
        customItemSet.add(new Pokeball());

        new Backpack();

        //Recipes
        // TODO: Add "new BackpackRecipe()"
        new RegisterRecipes(new RottenFleshToLeatherRecipe(), new CobblestoneToGravel(), new DirtToGravelRecipe(), new GravelToSandRecipe(), new RawCopperBlockToCopperBlockRecipe(), new RawGoldBlockToGoldBlockRecipe(), new RawIronBlockToIronBlockRecipe());
        //Register.getInstance().enable(new RottenFleshToLeatherRecipe(), new CobblestoneToGravel(), new DirtToGravelRecipe(), new GravelToSandRecipe(), new RawCopperBlockToCopperBlockRecipe(), new RawIronBlockToIronBlockRecipe(), new RawGoldBlockToGoldBlockRecipe());

        //PlayerStats, Scoreboard & Runnables
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStats.getCharacter(player); // loading player stats
        }

        new ServerScoreboard();

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new PlayerUpdateHealthRunnable(), 0, 5);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new PlayerStatsSaveRunnable(), 0, 20 * 60 * 5); // auto save every 5min
    }


    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.closeInventory();
            PlayerStats.getCharacter(player).save(); // save player stats
        }
    }


    public static Main getInstance() {
        return instance;
    }

    public static final Set<ICustomItem> getCustomItems() {
        return customItemSet;
    }

    public static final Map<String, ICustomItem> getCustomItemMap() {
        Map<String, ICustomItem> outVar = new HashMap<>();
        for (ICustomItem item : customItemSet) {
            outVar.put(item.getKey().getKey(), item);
        }
        return outVar;
    }
}
