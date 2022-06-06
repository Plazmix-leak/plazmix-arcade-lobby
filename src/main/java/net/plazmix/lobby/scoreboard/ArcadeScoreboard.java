package net.plazmix.lobby.scoreboard;

import lombok.NonNull;
import net.plazmix.core.PlazmixCoreApi;
import net.plazmix.utility.player.LocalizationPlayer;
import net.plazmix.utility.player.PlazmixUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.plazmix.scoreboard.BaseScoreboardBuilder;
import net.plazmix.scoreboard.BaseScoreboardScope;
import net.plazmix.scoreboard.ScoreboardDisplayAnimation;
import net.plazmix.scoreboard.animation.ScoreboardDisplayCustomAnimation;
import net.plazmix.utility.NumberUtil;

import java.util.LinkedList;
import java.util.List;

public class ArcadeScoreboard {

    public ArcadeScoreboard(@NonNull Player player) {
        PlazmixUser plazmixUser = PlazmixUser.of(player);

        BaseScoreboardBuilder scoreboardBuilder = BaseScoreboardBuilder.newScoreboardBuilder();
        scoreboardBuilder.scoreboardScope(BaseScoreboardScope.PROTOTYPE);

        scoreboardBuilder.scoreboardDisplay(getScoreboardDisplay(plazmixUser.localization()));
        scoreboardBuilder.scoreboardUpdater((baseScoreboard, player1) -> {

            baseScoreboard.setScoreboardDisplay(getScoreboardDisplay(plazmixUser.localization()));

            List<String> scoreboardLineList = getScoreboardLines(plazmixUser);

            for (String scoreboardLine : scoreboardLineList) {
                baseScoreboard.setScoreboardLine(scoreboardLineList.size() - scoreboardLineList.indexOf(scoreboardLine), player, scoreboardLine);
            }

        }, 10);

        scoreboardBuilder.build().setScoreboardToPlayer(player);
    }

    private ScoreboardDisplayAnimation getScoreboardDisplay(@NonNull LocalizationPlayer localizationPlayer) {
        ScoreboardDisplayCustomAnimation displayCustomAnimation = new ScoreboardDisplayCustomAnimation();

        for (String scoreboardDisplay : localizationPlayer.getMessageList("ARCADE_BOARD_TITLE"))
            displayCustomAnimation.addTextToAnimation(ChatColor.translateAlternateColorCodes('&', scoreboardDisplay));

        return displayCustomAnimation;
    }

    private List<String> getScoreboardLines(@NonNull PlazmixUser plazmixUser) {
        List<String> scoreboardLineList = new LinkedList<>();

        for (String scoreboardLine : plazmixUser.localization().getMessageList("ARCADE_BOARD_LINES")) {
            scoreboardLine = scoreboardLine
                    .replace("%coins%", NumberUtil.spaced(plazmixUser.getCoins()))
                    .replace("%plazma%", NumberUtil.spaced(plazmixUser.getGolds()))
                    .replace("%level%", NumberUtil.spaced(plazmixUser.getLevel()))
                    .replace("%online%", NumberUtil.spaced(PlazmixCoreApi.getGlobalOnline()))
                    .replace("%server_name%", PlazmixCoreApi.getCurrentServerName());

            scoreboardLineList.add(ChatColor.translateAlternateColorCodes('&', scoreboardLine));
        }

        return scoreboardLineList;
    }

}