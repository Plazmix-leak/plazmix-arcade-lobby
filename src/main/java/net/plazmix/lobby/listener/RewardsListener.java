package net.plazmix.lobby.listener;

import lombok.RequiredArgsConstructor;
import net.plazmix.coreconnector.module.type.rewards.CoreRewardsPassEvent;
import net.plazmix.lobby.ArcadeLobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.plazmix.utility.FireworkExplosion;

@RequiredArgsConstructor
public final class RewardsListener implements Listener {

    private final ArcadeLobbyPlugin plugin;

    @EventHandler
    public void onPassedReward(CoreRewardsPassEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);

        Bukkit.getScheduler().runTask(plugin, () -> FireworkExplosion.spawn(plugin.getRewardsNPC().getHandle().getLocation(), FireworkEffect.builder()
                        .with(FireworkEffect.Type.STAR)

                        .withColor(Color.AQUA, Color.WHITE, Color.PURPLE, Color.RED)
                        .build(),

                Bukkit.getOnlinePlayers().toArray(new Player[0])));
    }

}
