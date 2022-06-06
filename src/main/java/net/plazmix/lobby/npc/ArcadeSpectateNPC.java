package net.plazmix.lobby.npc;

import lombok.NonNull;
import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.inventory.impl.BaseSimpleInventory;
import net.plazmix.protocollib.entity.impl.FakePlayer;
import net.plazmix.utility.ItemUtil;
import net.plazmix.utility.mojang.MojangSkin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ArcadeSpectateNPC extends ServerPlayerNPC {

    public ArcadeSpectateNPC(Location location) {
        super(new MojangSkin("arcade_spectate",
                UUID.randomUUID().toString(),
                "ewogICJ0aW1lc3RhbXAiIDogMTYwNTk4ODkzNzM4OCwKICAicHJvZmlsZUlkIiA6ICJmZDg0ZjhkN2UyZmY0YTAwOWE2YWJlNGNhOGZlYWI4ZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJGQVpFX0dGcmVkZHkiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY4YzYwODQyNDA0NWVkNDYxZWRmOGUxMmY5ZDE1NGNjOGU2MzEzOWVhMjE1MDdlMDk1OTQxODc2ZmYzN2RhNSIKICAgIH0KICB9Cn0=",
                "j21sKnj+IF67bB0NOR3gRdv+03fd0BBBe4vFO4zt55Yn/4658rHNdkOjnArEa+miDumkpNeV9k2BQ1ef9MPIQbEeadEhCoWYjsVe6TiJqYiOIRoUEu9oXDR5cxNus/GX73QgSfaITZPMKfTqazYbH7Ej23jmhVyHF1Vyx/V9VJ2HhdKA5yva/RGfF3dfxI3iIxUJ19h3Xzy3ihyxbRKyxIKlh46ETHRiGAm6T8/525gMN6NevSRgseVLnVVdQyKT4zwsvmJRRbhjthXME52JWATuaePR4ETfeBfFrabuNEzMq1RWGEALXHfjng3lKnDHB1paVHdyKpj/8uFlrrEqbX7Om1MAhk2eHxi6r9WGVckm/YudPCJuJXiKn3Ji/Of0U5Hj48GGVCkggyN46oYya51mKswdWe/bCrY89p7dIlCriGOC97J9m75W+hwEXZ80cgjVHIFj6fjTAEXbARPhrWQvXSYY/GQjecu8mu6pKTbHPy9Kj2wXQNeQ6pmWFgCMkwbbQC/uCgXoEkuE2AfOr8/DeSPSLYoWkBKU+0AeZxEU6uGQ6x56//1jTrNUmzQOq4uaHXmQZTddGwIrfT2W8sTOQuHwT5ckC6pvR0bb67J/P2IxP5nYnYHmjdHHuVhVk82U7+AMyJy5nTchasSpLZzlz/Y8fsqnG2SV09ODvcg=",
                System.currentTimeMillis()), location);
    }

    @Override
    public void onReceive(@NonNull FakePlayer fakePlayer) {
        addHolographicLine("§e§lНаблюдатель");
        addHolographicLine("§7Нажмите, чтобы перейти наблюдать");
        addHolographicLine("§7за запущенными аренами");

        fakePlayer.setClickAction(player -> new ArcadeSpectateSelectModeInventory().openInventory(player));
        enableAutoLooking();
    }

    private static class ArcadeSpectateSelectModeInventory extends BaseSimpleInventory {

        public ArcadeSpectateSelectModeInventory() {
            super("Наблюдатель", 5);
        }

        @Override
        public void drawInventory(Player player) {
            setClickItem(21, ItemUtil.newBuilder(Material.WORKBENCH)
                            .setName("§e§lBuild Battle")
                            .addLore("§7▸ Нажмите, чтобы посмотреть!")
                            .build(),

                    (player1, inventoryClickEvent) -> PlazmixCoreApi.dispatchCommand(player, "gamespectator buildbattle"));

            setClickItem(23, ItemUtil.newBuilder(Material.SKULL_ITEM)
                            .setDurability(3)
                            .setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJkNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ==")

                            .setName("§e§lSpeed Builders")
                            .addLore("§7▸ Нажмите, чтобы посмотреть!")
                            .build(),

                    (player1, inventoryClickEvent) -> PlazmixCoreApi.dispatchCommand(player, "gamespectator speedbuilders"));

            setClickItem(25, ItemUtil.newBuilder(Material.SKULL_ITEM)
                            .setDurability(3)
                            .setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDY0YmRjNmY2MDA2NTY1MTFiZWY1OTZjMWExNmFhYjFkM2Y1ZGJhYWU4YmVlMTlkNWMwNGRlMGRiMjFjZTkyYyJ9fX0=")

                            .setName("§e§lSquid Game")
                            .addLore("§7▸ Нажмите, чтобы посмотреть!")
                            .build(),

                    (player1, inventoryClickEvent) -> PlazmixCoreApi.dispatchCommand(player, "gamespectator squid"));
        }

    }

}
