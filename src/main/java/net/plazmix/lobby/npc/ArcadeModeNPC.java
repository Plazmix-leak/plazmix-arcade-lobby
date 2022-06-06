package net.plazmix.lobby.npc;

import lombok.NonNull;
import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.holographic.ProtocolHolographic;
import net.plazmix.holographic.updater.SimpleHolographicUpdater;
import net.plazmix.protocollib.entity.impl.FakePlayer;
import net.plazmix.utility.NumberUtil;
import net.plazmix.utility.mojang.MojangSkin;
import net.plazmix.utility.player.PlazmixUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.UUID;

public class ArcadeModeNPC extends ServerPlayerNPC {

    private final String name;
    private final String serversPrefix;

    public ArcadeModeNPC(String name, String serversPrefix, Location location, String texture, String signature) {
        super(new MojangSkin(RandomStringUtils.random(5), UUID.randomUUID().toString(), texture, signature, System.currentTimeMillis()), location);

        this.name = name;
        this.serversPrefix = serversPrefix;
    }

    @Override
    public void onReceive(@NonNull FakePlayer fakePlayer) {
        addHolographicLine(name);
        addHolographicLine("");

        addLocalizedLine("HOLOGRAM_LOBBY_MODE_NPC_LINE_1");
        addLocalizedLine("HOLOGRAM_LOBBY_MODE_NPC_LINE_2");
        addLocalizedLine("HOLOGRAM_LOBBY_MODE_NPC_LINE_3");

        holographic.setHolographicUpdater(20, new SimpleHolographicUpdater(holographic) {

            @Override
            public void accept(ProtocolHolographic protocolHolographic) {
                protocolHolographic.setLangHolographicLine(3, localizationPlayer -> replacePlaceholders(localizationPlayer.getMessageText("HOLOGRAM_LOBBY_MODE_NPC_LINE_2")));
                protocolHolographic.setLangHolographicLine(4, localizationPlayer -> replacePlaceholders(localizationPlayer.getMessageText("HOLOGRAM_LOBBY_MODE_NPC_LINE_3")));
            }

            private String replacePlaceholders(String text) {
                return text.replace("%online%", NumberUtil.spaced(PlazmixCoreApi.getOnlineByServersPrefixes(serversPrefix)))
                        .replace("%servers%", NumberUtil.spaced(PlazmixCoreApi.getConnectedServersCount(serversPrefix)));
            }
        });

        fakePlayer.setGlowingColor(ChatColor.getByChar(name.charAt(1)));
        fakePlayer.setClickAction(player -> PlazmixCoreApi.dispatchCommand(player, "gameselector " + serversPrefix));

        enableAutoLooking(10);
    }

}
