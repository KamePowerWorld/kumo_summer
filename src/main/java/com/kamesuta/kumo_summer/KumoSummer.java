package com.kamesuta.kumo_summer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public final class KumoSummer extends JavaPlugin {
    // テレポート機能が有効かどうか
    private boolean tpEnabled = true;
    // テレポート先のプレイヤー名
    private String kumoPlayerName;
    // 透明効果の持続時間（秒）
    private int invisibilityDuration;

    @Override
    public void onEnable() {
        // デフォルトの設定ファイルを保存
        saveDefaultConfig();
        // 設定値を読み込む
        loadConfigValues();
        getLogger().info("KumoSummerプラグインが有効になりました！");
    }

    @Override
    public void onDisable() {
        getLogger().info("KumoSummerプラグインが無効になりました！");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("kumo")) {
            // /kumoコマンドの処理
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (tpEnabled) {
                    Player kumoPlayer = Bukkit.getPlayer(kumoPlayerName);
                    if (kumoPlayer != null && kumoPlayer.isOnline()) {
                        player.teleport(kumoPlayer.getLocation());
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisibilityDuration * 20, 1));
                        player.sendMessage("kumo_0621にテレポートし、透明効果が付与されました。");
                    } else {
                        player.sendMessage("kumo_0621は現在オフラインです。");
                    }
                } else {
                    player.sendMessage("現在、kumo_0621へのテレポートは無効になっています。");
                }
            } else {
                sender.sendMessage("このコマンドはプレイヤーのみ使用できます。");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("kumo_on")) {
            // /kumo_onコマンドの処理
            if (sender.hasPermission("kumo.admin")) {
                tpEnabled = true;
                sender.sendMessage("kumo_0621へのテレポートが有効になりました。");
            } else {
                sender.sendMessage("このコマンドを使用する権限がありません。");
            }
            return true;
        } else if (command.getName().equalsIgnoreCase("kumo_off")) {
            // /kumo_offコマンドの処理
            if (sender.hasPermission("kumo.admin")) {
                tpEnabled = false;
                sender.sendMessage("kumo_0621へのテレポートが無効になりました。");
            } else {
                sender.sendMessage("このコマンドを使用する権限がありません。");
            }
            return true;
        }
        return false;
    }

    // 設定値を読み込むメソッド
    private void loadConfigValues() {
        kumoPlayerName = getConfig().getString("kumo.playerName");
        invisibilityDuration = getConfig().getInt("kumo.invisibilityDuration");
    }
}
