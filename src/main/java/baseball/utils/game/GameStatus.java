package baseball.utils.game;

import baseball.utils.consts.GameNumberConst;

public enum GameStatus {
    APPLICATION_START,
    GAME_PLAY,
    GAME_EXIT,
    APPLICATION_EXIT,
    APPLICATION_EXCEPTION;

    public static GameStatus findNextPlay(long strike) {
        if (strike == GameNumberConst.NUMBER_SIZE) {
            return GAME_EXIT;
        }
        return GAME_PLAY;
    }

    public boolean playable() {
        if (this == APPLICATION_EXIT || this == APPLICATION_EXCEPTION) {
            return false;
        }
        return true;
    }
}
