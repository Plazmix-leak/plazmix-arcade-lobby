package net.plazmix.lobby;

import lombok.Getter;
import net.plazmix.lobby.listener.RewardsListener;
import net.plazmix.lobby.listener.ScoreboardListener;
import net.plazmix.lobby.listener.SpawnListener;
import net.plazmix.lobby.npc.ArcadeModeNPC;
import net.plazmix.lobby.npc.ArcadeSpectateNPC;
import net.plazmix.lobby.npc.RewardsNPC;
import net.plazmix.lobby.npc.manager.ServerNPCManager;
import net.plazmix.lobby.playertop.PlayerTopsStorage;
import net.plazmix.lobby.playertop.database.type.PlayerTopsMysqlDatabase;
import net.plazmix.lobby.playertop.pagination.PlayerTopsPaginationChanger;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/*  Leaked by https://t.me/leak_mine
    - Все слитые материалы вы используете на свой страх и риск.

    - Мы настоятельно рекомендуем проверять код плагинов на хаки!
    - Список софта для декопиляции плагинов:
    1. Luyten (последнюю версию можно скачать можно тут https://github.com/deathmarine/Luyten/releases);
    2. Bytecode-Viewer (последнюю версию можно скачать можно тут https://github.com/Konloch/bytecode-viewer/releases);
    3. Онлайн декомпиляторы https://jdec.app или http://www.javadecompilers.com/

    - Предложить свой слив вы можете по ссылке @leakmine_send_bot или https://t.me/leakmine_send_bot
*/

public final class ArcadeLobbyPlugin extends JavaPlugin {

    @Getter
    public RewardsNPC rewardsNPC;

    @Override
    public void onEnable() {
        World spawnWorld = getServer().getWorld("world");

        // World processes.
        addNpcs(spawnWorld);

        registerListeners(spawnWorld);
        enableWorldTicker(spawnWorld);

        // Add tops.
        addTops(new Location(spawnWorld, -87.5, 67, -31.5, 90, 0));
    }

    private void registerListeners(World spawnWorld) {

        // Default player actions.
        getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);
        getServer().getPluginManager().registerEvents(new RewardsListener(this), this);
        getServer().getPluginManager().registerEvents(new SpawnListener(spawnWorld), this);
    }

    private void addNpcs(World world) {
        // Daily rewards.
        ServerNPCManager.INSTANCE.register(rewardsNPC = new RewardsNPC(new Location(world, -82.5, 62, -20.5, 120, 0)));

        // Arena spectator.
        ServerNPCManager.INSTANCE.register(new ArcadeSpectateNPC(new Location(world, -121.5, 60, 10.5, -90, 0)));

        // Select game mode.
        ServerNPCManager.INSTANCE.register(new ArcadeModeNPC("§2§lBuild Battle", "buildbattle", new Location(world, -83.5, 59, 22.5, 180, 0), "ewogICJ0aW1lc3RhbXAiIDogMTYxNzAzMzcwMTA3MCwKICAicHJvZmlsZUlkIiA6ICJiNWRkZTVmODJlYjM0OTkzYmMwN2Q0MGFiNWY2ODYyMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJsdXhlbWFuIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzgwYTQxNjQ2MjNjZDI3ZDFhNmRjNmYzYzg0NGU1MTAyYzFhODA0NWM0MDM0YjIxMDVmOWM1YjMyZTRhM2Q4MjIiCiAgICB9CiAgfQp9", "duHX12epvyYiGm+0o2Z2mBytLZSGxHt65W5/tu8Cf+E4dNtbqPr/mYqs/X+JBGtKHgSZKzVDXd91VGt1gx3ylMlG1nWhoQ9Pyc0yxtnfevjamx+Xc+vgQeki+waXVlVn7eg7zs1bjkU7AKwrl1DfC1Kn3zn7+EURWSoxYlVG+YZPOW/tXhaRLWA780SlXRY3LprM1Z3v2A29kusUrCxY5jI1pWsCZvHANzoY7+jpKmwxRkuhunqNOkO/2hfkrgpXZ6BERWCGaABIX8cCe5wqpaKZMxWL6udu6o9HOVOOwYK3gA+paOM91SCGGmK96X+LOu3u8fXkZgarG9Xmh165EGMXm0o5ZwgVdVPlL5V1iiKWyUONqxo3zD8m6exiklzyULP/roWcXbIpWO7RqwWcOj41vpTib5M6bKUULkzkyTfy/CggHR/3WUprh5Ow+S1lKIg0jYrpS/zcv+2/TWoRTEXD6GU1+Pan6aAUe+KtmEJ4Hoxw0YkxO7l1sv6o5tQWJ9fXaNLDdsQVrsPB4qpISROxczrXuzXVxOvtC3qfAUXDrIrNSP9FaBDLw+o8M7fJm1LEZ23Xn/9YOLlSybtw+4N0TgsB77gJrshBaJqcd3oF1jV2KGFC2Juk4V6ybIVp7ssEnlyH6/OP3Qw3epNvxNYfkCWxCJtt3n8lpdJTZGE="));
        ServerNPCManager.INSTANCE.register(new ArcadeModeNPC("§2§lSpeed Builders", "speedbuilders", new Location(world, -87.5, 59, 23.5, 180, 0), "eyJ0aW1lc3RhbXAiOjE1ODA2NTk4NDY4NjQsInByb2ZpbGVJZCI6ImY0YzJjYWQ3MGFjZTRjZTY4Njc4NjlkMGI2NTNhOThiIiwicHJvZmlsZU5hbWUiOiJHdWFyZGlhbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjIxMDI1NDM0MDQ1YmRhNzAyNWIzZTUxNGIzMTZhNGI3NzBjNmZhYTRiYTlhZGI0YmUzODA5NTI2ZGI3N2Y5ZCJ9fX0=", "IDeoBNX1e7RoJYISkOjZhNCpHLfxlBQc0Y24edkjukqP7CMphj8mMd394sEakXb//rUktTXh16VI4oWoeOuUGOM9xr8XDyfycBiUhGp7ULQCLfgFJmw9kMON7U8eZYXIFzV7WrDW7t4W0jLaYVmBeamPSrBvGWw4/Zd3XmenDTgCB049p8Uv34hjViKjIED7e7YYklMdkODLgyJedkORXz6zQvV5ZjilpjoE/t9hfv+Yuo8a4yluRMFgFQ1uxy7l3AbXMndx6+nq28fcQpoOWlsogaGvmzbA/3fviqHdGm8KTq3gLGoXpNuygokF3iT6QqU96/VgCWqQ/++rLqNF102w9LsSploEDxVjUHmVZobL8kop22QENcTsXgBFWlncn5OC2XWLhqKqPrBphvjm6pccQ6cGIA1+d2m7FEkP/e10APgvuBT+XTiJQd8DB/n6KVwWJ+0NSf/xNb1HKbK2zheh2RJnI+HwRe21g49kSznrqhTQzLbRU8cA4ZhVEfZzWx0ZbWRnb8OR493ROVjzb+uB5XWmjLZ+jQdmA/irHAvzR4kWEOY3WGS4OKjwzTKw9dPbe/eM0D8KDDS3zKPCpxS257B07VPZmdKzmREkUuYLlleSmKCg8fzFgyXYGIrNNOOYsDnWIoClCfWBGC1JQa24v5kAHmlrtffcPc4/s0k="));
        ServerNPCManager.INSTANCE.register(new ArcadeModeNPC("§2§lSquid Game", "squid", new Location(world, -91.5, 59, 22.5, 180, 0), "eyJ0aW1lc3RhbXAiOjE1MDA5NDQ3OTI0NDYsInByb2ZpbGVJZCI6ImJiMDgwOTFhOTJlZDQzYWQ5NGYxZWRmZDc0NmIwYTJjIiwicHJvZmlsZU5hbWUiOiJJcDRuZHgiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2ZiZWIyMDU2ZDc0YmM5OTIyZmNhY2U4NGU1OTRjYTMzZjI4YWNlYjVhY2QwNGY1YWE2ZjU0N2IxM2YxMDQ3MiJ9fX0=", "Dd+cGO81SByHM/84Ot0X3VzHf5+G6ItYntCydFLcgLOV3DK4B7UQw8NhTJ+44x0gHrTSJLmewjPaLuRb7RreFwFpGZMX2pVHIHma22rrHL5BQxS96zvQCHRaTATMzAhzGRidyisZaRHj5T4TDAzcKe35FIEJRKeizoBkwLDU2KykfW+C2kwXz+f/BWwAUCdqAijcjita2ZkzL2P2ByY2VGdgR0JYG/LQbpe1BBUyGlU3QX55w2iUxHKuET8zjTaSSts+WnIHL4L2SsHeKH82Kr6AFfQgydoXW8DKiUc6Yk9ssluhGb51nfvAPb0rqAmhIA46dzr+byrLVjj5oeQKX9Mt8+DTTv0oY8/BiCp1AdVEJc7uRv32RzQJ1ytAhgW6mDdvae27mWlphoSHjc0jmKomnjdzuRAQJoa+o91Ez5haW4aXXunWRV+FhpTqWHfye69imaSfOjIReurrYRcjwf7goeDDownuhvzxpRfz2TDscbjM4hTUU+IJAd8b3AUcDXCcw9iLglzmbqJ2FxsMYwwk8qW09Xe6B96U1G4CzA9DgtIKji0nYDldfrGtEu+4FxE1m/rpCqnEAfA6dlEvZZ96X8gxdLZVMn55Lgnr+LPT8Fm89vj61DM1hgtG6bxEsgzEM8CPc4Oc+GozTIyshDZ6eUVnt8agp+77S1ZjqaA="));
    }

    private void enableWorldTicker(World world) {
        world.setPVP(false);
        world.setDifficulty(Difficulty.PEACEFUL);

        new BukkitRunnable() {

            @Override
            public void run() {
                world.setTime(12040);

                // Set world settings.
                world.setThundering(false);
                world.setStorm(false);

                world.setWeatherDuration(0);
            }

        }.runTaskTimer(this, 0, 1);
    }

    private void addTops(Location location) {
        PlayerTopsPaginationChanger paginationChanger = PlayerTopsPaginationChanger.create();

       // TntTag
       paginationChanger.addPlayerTops(PlayerTopsStorage.newBuilder()
               .setDatabaseManager(new PlayerTopsMysqlDatabase("TntTag", "Wins"))

               .setLocation(location)
               .setSkullParticle(Particle.TOTEM)

               .setLimit(10)
               .setUpdater(60)

               .setSkullTexture("")

               .setStatsName("TntTag")
               .setDescription("Топ 10 игроков, набравшие наибольшее",
                       "количество побед по данному режиму")

               .setValueSuffix("побед"));

       // SpeedBuilders
       paginationChanger.addPlayerTops(PlayerTopsStorage.newBuilder()
               .setDatabaseManager(new PlayerTopsMysqlDatabase("SpeedBuilders", "Wins"))

               .setLocation(location)
               .setSkullParticle(Particle.TOTEM)

               .setLimit(10)
               .setUpdater(60)

               .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==")

               .setStatsName("SpeedBuilders")
               .setDescription("Топ 10 игроков, набравшие наибольшее",
                       "количество побед по данному режиму")

               .setValueSuffix("побед"));

       //// Squid Game
       //paginationChanger.addPlayerTops(PlayerTopsStorage.newBuilder()
       //        .setDatabaseManager(new PlayerTopsMysqlDatabase("SquidGame", "Wins"))

       //        .setLocation(location)
       //        .setSkullParticle(Particle.TOTEM)

       //        .setLimit(10)
       //        .setUpdater(60)

       //        .setSkullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY0YmRjNmY2MDA2NTY1MTFiZWY1OTZjMWExNmFhYjFkM2Y1ZGJhYWU4YmVlMTlkNWMwNGRlMGRiMjFjZTkyYyJ9fX0=")

       //        .setStatsName("SquidGame")
       //        .setDescription("Топ 10 игроков, набравшие наибольшее",
       //                "количество побед по данному режиму")

       //        .setValueSuffix("побед"));

        // Spawn all tops.
        paginationChanger.spawn();
    }

}
