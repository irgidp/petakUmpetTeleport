package yt.corazonid.petakUmpetTeleport;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PetakUmpetTeleport extends JavaPlugin implements Listener {
    private GameManager gameManager;
    private TeleportManager teleportManager;
    private GameListener gameListener;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager();
        this.teleportManager = new TeleportManager(this);

        // Register Commands
        getCommand("regis").setExecutor(new AdminCommands(this));
        getCommand("unregis").setExecutor(new AdminCommands(this));
        getCommand("listplayer").setExecutor(new AdminCommands(this));
        getCommand("gacha").setExecutor(new GameCommands(this));
        getCommand("start").setExecutor(new GameCommands(this));
        getCommand("nextround").setExecutor(new GameCommands(this));
        if (getCommand("resetgame") != null) getCommand("resetgame").setExecutor(new AdminCommands(this));
        if (getCommand("endgame") != null) getCommand("endgame").setExecutor(new AdminCommands(this));
        if (getCommand("listscore") != null) getCommand("listscore").setExecutor(new AdminCommands(this));
        if (getCommand("tpinfo") != null) getCommand("tpinfo").setExecutor(new AdminCommands(this));

        this.gameListener = new GameListener(this);
        getServer().getPluginManager().registerEvents(gameListener, this);
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("PetakUmpet Teleport Enabled!");
    }

    public GameManager getGameManager() { return gameManager; }
    public TeleportManager getTeleportManager() { return teleportManager; }
    public GameListener getGameListener() { return gameListener; }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
