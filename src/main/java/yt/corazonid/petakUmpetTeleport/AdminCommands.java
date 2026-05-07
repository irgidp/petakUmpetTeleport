package yt.corazonid.petakUmpetTeleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import yt.corazonid.petakUmpetTeleport.PetakUmpetTeleport;

import java.util.Map;
import java.util.UUID;

public class AdminCommands implements CommandExecutor {
    private final PetakUmpetTeleport plugin;

    public AdminCommands(PetakUmpetTeleport plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()) return true;

        GameManager gm = plugin.getGameManager();

        if (label.equalsIgnoreCase("regis")) {
            if (gm.isGameRunning()) {
                sender.sendMessage("§cTidak bisa melakukan registrasi saat game berlangsung!");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("§cGunakan: /regis <nama>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                gm.regis(target);
                sender.sendMessage("§a" + target.getName() + " berhasil terdaftar!");
            } else {
                sender.sendMessage("§cPlayer tidak online.");
            }
        }

        else if (label.equalsIgnoreCase("unregis")) {
            if (gm.isGameRunning()) {
                sender.sendMessage("§cTidak bisa unregis saat game berlangsung!");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("§cGunakan: /unregis <nama>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                gm.unregis(target);
                sender.sendMessage("§e" + target.getName() + " telah dihapus dari daftar.");
            }
        }

        else if (label.equalsIgnoreCase("listplayer")) {
            sender.sendMessage("§6§lDAFTAR PESERTA PETAK UMPET:");
            for (Player p : gm.getParticipants()) {
                sender.sendMessage("§7- §f" + p.getName());
            }
        }

        else if (label.equalsIgnoreCase("endgame")) {
            if (!sender.isOp()) return true;

            gm.setGameRunning(false);
            Bukkit.broadcastMessage("§c§lGAME TELAH BERAKHIR!");
            showLeaderboard();
        }

        else if (label.equalsIgnoreCase("listscore")) {
            showLeaderboard();
        }

        else if (label.equalsIgnoreCase("resetgame")) {
            if (!sender.isOp()) return true;
            gm.resetGameData();
            sender.sendMessage("§a§lRESET! §fSemua skor dan sejarah Hunter telah dihapus.");
            Bukkit.broadcastMessage("§c§lGAME DIRESET! Siap untuk ronde baru.");
        }

        else if (label.equalsIgnoreCase("tpinfo")) {
            showTeleportInfo(sender);
        }


        return true;
    }

    private void showLeaderboard() {
        GameManager gm = plugin.getGameManager();
        Bukkit.broadcastMessage("§8==============================");
        Bukkit.broadcastMessage("§6§lLEADERBOARD LAPER GANG");
        Bukkit.broadcastMessage("§8==============================");

        gm.getScores().entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                    int score = entry.getValue();
                    Bukkit.broadcastMessage("§e" + name + ": §f" + score + " Poin");
                });

        Bukkit.broadcastMessage("§8==============================");
    }

    private void showTeleportInfo(CommandSender sender) {
        sender.sendMessage("§6§l========== TIPE-TIPE TELEPORT ==========");
        sender.sendMessage("§b[0] §fSwap Semua Player");
        sender.sendMessage("    §7→ Semua pemain (seeker & hiders) di-swap ke lokasi random");
        sender.sendMessage("");
        sender.sendMessage("§b[1] §fRandom Swap Hiders Only");
        sender.sendMessage("    §7→ Hanya hiders yang di-teleport ke lokasi random");
        sender.sendMessage("    §7→ Seeker tetap di posisinya");
        sender.sendMessage("");
        sender.sendMessage("§b[2] §fMix Swap (4 random, 2 stay)");
        sender.sendMessage("    §7→ 4 pemain random di-teleport, 2 tetap di posisinya");
        sender.sendMessage("");
        sender.sendMessage("§b[3] §fFake Swap (No Teleport)");
        sender.sendMessage("    §7→ Terlihat seperti ada teleport tapi sebenarnya tidak");
        sender.sendMessage("    §7→ Efek visual & suara hanya, semua tetap di posisi");
        sender.sendMessage("");
        sender.sendMessage("§b[4] §fFixed Swap Hiders Pattern");
        sender.sendMessage("    §7→ Hiders bertukar posisi mengikuti pola cycle");
        sender.sendMessage("    §7→ Contoh: A→B, B→C, C→D, D→A");
        sender.sendMessage("§6§l=========================================");
    }
}

