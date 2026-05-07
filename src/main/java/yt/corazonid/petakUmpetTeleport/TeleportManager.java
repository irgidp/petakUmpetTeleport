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

    public void executeTeleport(int type, List<Player> allPlayers, Player hunter, Set<UUID> ghostPlayers) {
        switch (type) {
            case 0 -> swapAllPlayers(allPlayers, ghostPlayers);
            case 1 -> swapHidersRandom(allPlayers, hunter, ghostPlayers);
            case 2 -> swapMix(allPlayers, ghostPlayers);
            case 3 -> fakeSwap(allPlayers);
            case 4 -> fixedSwapHiders(allPlayers, hunter, ghostPlayers);
        }
    }

    // TYPE 1: Swap SEMUA Player ke Posisi Masing2 (Hunter + Ghost + Hider ALL)
    private void swapAllPlayers(List<Player> players, Set<UUID> ghostPlayers) {
        // SEMUA player bisa included (Hunter, Ghost, Hider alive)
        List<Player> allCanTeleport = new ArrayList<>(players);

        if (allCanTeleport.size() < 2) {
            Bukkit.broadcastMessage("§9[TELEPORT] §cTidak cukup player! Teleport dibatalkan.");
            return;
        }

        // Simpan posisi original setiap player
        Map<Player, Location> originalLocations = new HashMap<>();
        for (Player p : allCanTeleport) {
            originalLocations.put(p, p.getLocation().clone());
        }

        // Shuffle list player untuk swap posisi
        List<Player> shuffledPlayers = new ArrayList<>(allCanTeleport);
        Collections.shuffle(shuffledPlayers);

        // Teleport SEMUA player ke posisi player lain (rotasi)
        for (int i = 0; i < allCanTeleport.size(); i++) {
            Player currentPlayer = allCanTeleport.get(i);
            Location targetLocation = originalLocations.get(shuffledPlayers.get(i));

            currentPlayer.teleport(targetLocation);
            currentPlayer.playSound(currentPlayer.getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        Bukkit.broadcastMessage("§9[TELEPORT] §f🌪️ SEMUA PEMAIN BERTUKAR POSISI!");
    }

    // TYPE 2: Swap Hiders Alive Only (Each Other - Ghost & Hunter TIDAK ikut)
    private void swapHidersRandom(List<Player> allPlayers, Player hunter, Set<UUID> ghostPlayers) {
        List<Player> liveHiders = allPlayers.stream()
                .filter(p -> !p.equals(hunter))
                .filter(p -> !ghostPlayers.contains(p.getUniqueId()))  // EXCLUDE GHOST
                .toList();

        if (liveHiders.size() < 2) {
            Bukkit.broadcastMessage("§b[TELEPORT] §cTidak cukup hider alive! Teleport dibatalkan.");
            return;
        }

        // Simpan posisi original setiap live hider
        Map<Player, Location> originalLocations = new HashMap<>();
        for (Player p : liveHiders) {
            originalLocations.put(p, p.getLocation().clone());
        }

        // Shuffle hiders untuk menentukan target teleport
        List<Player> shuffledHiders = new ArrayList<>(liveHiders);
        Collections.shuffle(shuffledHiders);

        // Teleport setiap live hider ke posisi live hider lain (rotasi)
        for (int i = 0; i < liveHiders.size(); i++) {
            Player currentHider = liveHiders.get(i);
            Location targetLocation = originalLocations.get(shuffledHiders.get(i));

            currentHider.teleport(targetLocation);
            currentHider.playSound(currentHider.getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        Bukkit.broadcastMessage("§b[TELEPORT] §fHiders bertukar posisi satu sama lain! Ghost & Hunter tetap ditempat.");
    }

    // TYPE 2: Swap Mix - SEMUA (Dynamic - 2/3 swap, 1/3 stay)
    private void swapMix(List<Player> allPlayers, Set<UUID> ghostPlayers) {
        // SEMUA player bisa included (Hunter + Ghost + Hider)
        List<Player> allCanTeleport = new ArrayList<>(allPlayers);

        if (allCanTeleport.size() < 2) {
            Bukkit.broadcastMessage("§c[TELEPORT] §cTidak cukup player! Teleport dibatalkan.");
            return;
        }

        // Calculate jumlah player yang akan di-swap (2/3 dari SEMUA player)
        int swapCount = Math.max(2, (allCanTeleport.size() * 2 / 3));
        swapCount = Math.min(swapCount, allCanTeleport.size() - 1);

        List<Player> shuffled = new ArrayList<>(allCanTeleport);
        Collections.shuffle(shuffled);

        // Ambil player yang akan di-swap (bisa Hunter, Ghost, atau Hider)
        List<Player> toSwap = shuffled.stream().limit(swapCount).toList();

        // Simpan posisi original dari player yang akan di-swap
        Map<Player, Location> originalLocations = new HashMap<>();
        for (Player p : toSwap) {
            originalLocations.put(p, p.getLocation().clone());
        }

        // Shuffle posisi untuk randomisasi target teleport
        List<Player> shuffledSwapPlayers = new ArrayList<>(toSwap);
        Collections.shuffle(shuffledSwapPlayers);

        // Teleport ke satu sama lain (rotasi)
        for (int i = 0; i < toSwap.size(); i++) {
            Player currentPlayer = toSwap.get(i);
            Location targetLocation = originalLocations.get(shuffledSwapPlayers.get(i));

            currentPlayer.teleport(targetLocation);
            currentPlayer.playSound(currentPlayer.getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        int stayCount = allCanTeleport.size() - swapCount;
        StringBuilder sb = new StringBuilder("§c[TELEPORT] §f⚡ Swap " + swapCount + " player, " + stayCount + " stay: ");
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
                Bukkit.broadcastMessage("§9[TELEPORT] §c❌ PRENK");
                Bukkit.broadcastMessage("§9[TELEPORT] §fTidak ada teleport kali ini!");
            }
        }.runTaskLater(plugin, 40L);
    }

    // TYPE 5: Fixed Swap HIDERS ALIVE Pattern (Ghost TIDAK ikut)
    private void fixedSwapHiders(List<Player> allPlayers, Player hunter, Set<UUID> ghostPlayers) {
        List<Player> liveHiders = allPlayers.stream()
                .filter(p -> !p.equals(hunter))
                .filter(p -> !ghostPlayers.contains(p.getUniqueId()))  // EXCLUDE GHOST
                .toList();

        if (liveHiders.size() < 2) {
            Bukkit.broadcastMessage("§2[TELEPORT] §cTidak cukup hider alive! Teleport dibatalkan.");
            return;
        }

        Map<Player, Location> locs = new HashMap<>();
        liveHiders.forEach(p -> locs.put(p, p.getLocation().clone()));

        // Rotate: hider 0 → location hider 1, hider 1 → location hider 2, etc.
        for (int i = 0; i < liveHiders.size(); i++) {
            int next = (i + 1) % liveHiders.size();
            liveHiders.get(i).teleport(locs.get(liveHiders.get(next)));
            liveHiders.get(i).playSound(liveHiders.get(i).getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
        }

        Bukkit.broadcastMessage("§2[TELEPORT] §fHiders bertukar posisi (cycle)! Ghost & Hunter tetap ditempat.");
    }
}

