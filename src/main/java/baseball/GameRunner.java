package baseball;

import baseball.controller.GameController;
import baseball.utils.game.GameStatus;
import baseball.view.IOViewResolver;
import baseball.view.InputView;
import baseball.view.OutputView;

public final class GameRunner {

    private GameRunner() {
    }

    public static void run(GameStatus gameStatus) {
        GameController controller = initGameController();

        while (gameStatus.playable()) {
            gameStatus = controller.process(gameStatus);
        }
    }

    private static GameController initGameController() {
        InputView inputView = new InputView(System.out::print);
        OutputView outputView = new OutputView(System.out::println);

        return new GameController(new IOViewResolver(inputView, outputView));
    }
}
