package net.plazmix.lobby.listener;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.lobby.ArcadeLobbyPlugin;
import net.plazmix.utility.player.PlazmixUser;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffectType;
import net.plazmix.utility.BukkitPotionUtil;

@RequiredArgsConstructor
public final class SpawnListener implements Listener {

    private final World spawnWorld;

    private boolean hasGuard(@NonNull World world) {
        return world.equals(spawnWorld);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!hasGuard(event.getEntity().getWorld())) {
            return;
        }

        event.setDroppedExp(0);
        event.getDrops().clear();
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        if (!hasGuard(event.getBlock().getWorld())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            return;
        }

        if (!hasGuard(event.getEntity().getWorld())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!hasGuard(event.getPlayer().getWorld())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!hasGuard(event.getEntity().getWorld())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlazmixUser plazmixUser = PlazmixUser.of(event.getPlayer());
        event.setJoinMessage(null);

        if (!hasGuard(plazmixUser.handle().getWorld())) {
            return;
        }

        plazmixUser.handle().addAttachment(ArcadeLobbyPlugin.getPlugin(ArcadeLobbyPlugin.class), "jumppads.use", true);

        plazmixUser.handle().setLevel(plazmixUser.getLevel());
        plazmixUser.handle().setExp((float) plazmixUser.getExperience() / plazmixUser.getMaxExperience());

        plazmixUser.handle().teleport(getSpawnLocation());

        if (!PlazmixCoreApi.GROUP_API.isDefault(plazmixUser.getName())) {

            plazmixUser.handle().setAllowFlight(true);

            plazmixUser.handle().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);

            Firework firework = spawnWorld.spawn(plazmixUser.handle().getLocation(), Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            fireworkMeta.setPower(3);
            fireworkMeta.addEffect(FireworkEffect.builder()
                    .with(FireworkEffect.Type.STAR)

                    .withColor(Color.PURPLE)
                    .withColor(Color.WHITE)

                    .build());

            firework.setFireworkMeta(fireworkMeta);
        }

        plazmixUser.handle().playSound(plazmixUser.handle().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        plazmixUser.handle().sendTitle("§6§lArcade Games", "§fСборник маленьких игр большого сервера");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!hasGuard(event.getBlock().getWorld())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!hasGuard(event.getBlock().getWorld())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!hasGuard(event.getPlayer().getWorld())) {
            return;
        }

        if (event.getTo().getY() <= 0) {
            event.getPlayer().teleport(getSpawnLocation());
        }
    }

    private Location getSpawnLocation() {
        return new Location(spawnWorld, -87.5, 66, -23.5, 0, 0);
    }

}
