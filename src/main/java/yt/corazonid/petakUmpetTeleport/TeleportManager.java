package yt.corazonid.petakUmpetTeleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import yt.corazonid.petakUmpetTeleport.PetakUmpetTeleport;

import java.util.*;

public class TeleportManager {
    private final PetakUmpetTeleport plugin;
    private final Random random = new Random();

    public TeleportManager(PetakUmpetTeleport plugin) {
        this.plugin = plugin;
    }

    public void executeTeleport(int type, List<Player> allPlayers, Player hunter) {
        switch (type) {
            case 0 -> swapAllPlayers(allPlayers);
            case 1 -> swapHidersRandom(allPlayers, hunter);
            case 2 -> swapMix(allPlayers);
            case 3 -> fakeSwap(allPlayers);
            case 4 -> fixedSwapHiders(allPlayers, hunter);
        }
    }

    // TYPE 1: Swap Semua Player
    private void swapAllPlayers(List<Player> players) {
        List<Location> spawns = getRandomLocations(players.size());

        for (int i = 0; i < players.size(); i++) {
            players.get(i).teleport(spawns.get(i));
            players.get(i).playSound(players.get(i).getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        Bukkit.broadcastMessage("§9[TELEPORT] §fSemua player di-swap ke lokasi random!");
    }

    // TYPE 2: Swap Hiders Random Only
    private void swapHidersRandom(List<Player> allPlayers, Player hunter) {
        List<Player> hiders = allPlayers.stream()
                .filter(p -> !p.equals(hunter))
                .toList();

        List<Location> spawns = getRandomLocations(hiders.size());

        for (int i = 0; i < hiders.size(); i++) {
            hiders.get(i).teleport(spawns.get(i));
            hiders.get(i).playSound(hiders.get(i).getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        Bukkit.broadcastMessage("§b[TELEPORT] §fHiders di-teleport ke lokasi random!");
    }

    // TYPE 3: Swap Mix (4 random, 2 stay)
    private void swapMix(List<Player> allPlayers) {
        List<Player> shuffled = new ArrayList<>(allPlayers);
        Collections.shuffle(shuffled);

        List<Player> toSwap = shuffled.stream().limit(4).toList();
        List<Location> spawns = getRandomLocations(4);

        for (int i = 0; i < toSwap.size(); i++) {
            toSwap.get(i).teleport(spawns.get(i));
            toSwap.get(i).playSound(toSwap.get(i).getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        StringBuilder sb = new StringBuilder("§c[TELEPORT] §fSwap 4 player: ");
        toSwap.forEach(p -> sb.append(p.getName()).append(", "));
        Bukkit.broadcastMessage(sb.toString());
    }

    // TYPE 4: Fake Swap (No teleport, just effects)
    private void fakeSwap(List<Player> allPlayers) {
        // Visual/audio effect
        for (Player p : allPlayers) {
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
            p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 20, 0, false, false));
        }

        Bukkit.broadcastMessage("§e[TELEPORT] §fTelah dimulai...");

        // Delayed message
        new org.bukkit.scheduler.BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("§9[TELEPORT] §c❌ FALSE ALARM!");
                Bukkit.broadcastMessage("§9[TELEPORT] §fTidak ada teleport kali ini!");
            }
        }.runTaskLater(plugin, 40L);
    }

    // TYPE 5: Fixed Swap Hiders Pattern
    private void fixedSwapHiders(List<Player> allPlayers, Player hunter) {
        List<Player> hiders = allPlayers.stream()
                .filter(p -> !p.equals(hunter))
                .toList();

        if (hiders.size() < 2) return;

        Map<Player, Location> locs = new HashMap<>();
        hiders.forEach(p -> locs.put(p, p.getLocation().clone()));

        // Rotate: player 0 → location player 1, player 1 → location player 2, etc.
        for (int i = 0; i < hiders.size(); i++) {
            int next = (i + 1) % hiders.size();
            hiders.get(i).teleport(locs.get(hiders.get(next)));
            hiders.get(i).playSound(hiders.get(i).getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        Bukkit.broadcastMessage("§2[TELEPORT] §fHiders bertukar posisi (cycle)!");
    }

    // Helper: Generate random spawn locations
    public List<Location> getRandomLocations(int count) {
        List<Location> locations = new ArrayList<>();
        World world = Bukkit.getWorlds().get(0);

        for (int i = 0; i < count; i++) {
            // Random within -500 to 500 X and Z, Y = highest block
            int x = random.nextInt(1000) - 500;
            int z = random.nextInt(1000) - 500;
            int y = world.getHighestBlockYAt(x, z);

            locations.add(new Location(world, x + 0.5, y + 1, z + 0.5));
        }

        return locations;
    }
}

