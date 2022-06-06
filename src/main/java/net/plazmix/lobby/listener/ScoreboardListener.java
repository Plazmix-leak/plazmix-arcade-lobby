package net.plazmix.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.plazmix.lobby.scoreboard.ArcadeScoreboard;

public final class ScoreboardListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        new ArcadeScoreboard(event.getPlayer());
    }

}
