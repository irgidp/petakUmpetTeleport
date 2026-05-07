package yt.corazonid.petakUmpetTeleport;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import yt.corazonid.petakUmpetTeleport.PetakUmpetTeleport;

import java.util.Random;

public class GameLoopTask extends BukkitRunnable {
    private final PetakUmpetTeleport plugin;
    private int totalSeconds = 300; // 5 Menit

    public GameLoopTask(PetakUmpetTeleport plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        GameManager gm = plugin.getGameManager();
        if (!gm.isGameRunning() || totalSeconds <= 0) {
            this.cancel();
            Bukkit.broadcastMessage("§6§lWAKTU HABIS! Game Selesai.");
            return;
        }

        // Tampilkan Timer di Action Bar setiap detik
        String timeFormatted = String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
        Bukkit.getOnlinePlayers().forEach(p ->
                p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        new net.md_5.bungee.api.chat.TextComponent("§6§lWAKTU BERMAIN: §e" + timeFormatted)));

        // Trigger teleport setiap menit (5:00, 4:00, 3:00, 2:00, 1:00)
        if (totalSeconds % 60 == 0 && totalSeconds > 0) {
            triggerTeleport();
        }

        totalSeconds--;
    }

    private void triggerTeleport() {
        TeleportManager tm = plugin.getTeleportManager();
        GameManager gm = plugin.getGameManager();

        // Broadcast warning
        Bukkit.broadcastMessage("§9§l[TELEPORT] §fDimulai dalam 3 detik...");

        // 3-second countdown
        new BukkitRunnable() {
            int count = 3;
            @Override
            public void run() {
                if (count > 0) {
                    Bukkit.getOnlinePlayers().forEach(p -> {
                        p.sendTitle("§c" + count, "", 0, 21, 0);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1f, 1.5f);
                    });
                    count--;
                } else {
                    this.cancel();
                    // EXECUTE TELEPORT - Random type
                    int randomType = new Random().nextInt(5);
                    String typeInfo = switch (randomType) {
                        case 0 -> "Swap Semua Player";
                        case 1 -> "Random Swap Hiders Only";
                        case 2 -> "Mix Swap (4 random, 2 stay)";
                        case 3 -> "Fake Swap (No Teleport)";
                        case 4 -> "Fixed Swap Hiders Pattern";
                        default -> "Unknown";
                    };
                    tm.executeTeleport(randomType, gm.getParticipants(), gm.getHunter(), plugin.getGameListener().getGhostPlayers());
                    plugin.getLogger().info("Teleport Type #" + randomType + ": " + typeInfo);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}

