package baseball.domain.number;

import baseball.utils.consts.GameNumberConst;
import baseball.utils.message.ExceptionMessageUtil;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseBallNumbers {

    private static final String SPLIT_SEPARATOR = "";

    private final List<BaseBallNumber> numbers;

    public BaseBallNumbers() {
        this.numbers = createComputerAnswer();
    }

    public BaseBallNumbers(String playerInput) {
        List<BaseBallNumber> playerAnswer = createPlayerAnswer(playerInput);

        validatePlayerAnswer(playerAnswer);
        this.numbers = playerAnswer;
    }

    private List<BaseBallNumber> createComputerAnswer() {
        List<Integer> randomNumbers = createRandomNumbers();

        return IntStream.range(0, GameNumberConst.NUMBER_SIZE)
                .mapToObj(index -> new BaseBallNumber(randomNumbers.get(index), index))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Integer> createRandomNumbers() {
        List<Integer> randomNumbers = new ArrayList<>();

        while (randomNumbers.size() < GameNumberConst.NUMBER_SIZE) {
            addRandomNumbers(randomNumbers);
        }
        return Collections.unmodifiableList(randomNumbers);
    }

    private void validatePlayerAnswer(List<BaseBallNumber> playerAnswer) {
        if (playerAnswer.size() != GameNumberConst.NUMBER_SIZE) {
            throw new IllegalArgumentException(ExceptionMessageUtil.WRONG_SIZE.findFullMessage());
        }
    }

    private List<BaseBallNumber> createPlayerAnswer(String playerInput) {
        AtomicInteger index = new AtomicInteger();

        return Arrays.stream(playerInput.split(SPLIT_SEPARATOR))
                .distinct()
                .map(this::mapToNumber)
                .map(number -> new BaseBallNumber(number, index.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private int mapToNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessageUtil.WRONG_NUMBER.findFullMessage(), e);
        }
    }

    private void addRandomNumbers(List<Integer> randomNumbers) {
        int newNumbers = Randoms.pickNumberInRange(GameNumberConst.MIN_VALUE, GameNumberConst.MAX_VALUE);

        if (!randomNumbers.contains(newNumbers)) {
            randomNumbers.add(newNumbers);
        }
    }

    public long calculateStrike(BaseBallNumbers playerAnswer) {
        return playerAnswer.numbers.stream()
                .filter(this::isStrike)
                .count();
    }

    public long calculateBall(BaseBallNumbers playerAnswer) {
        return playerAnswer.numbers.stream()
                .filter(this::isBall)
                .count();
    }

    private boolean isStrike(BaseBallNumber playerNumber) {
        return numbers.stream()
                .anyMatch(computerNumber -> computerNumber.equals(playerNumber));
    }

    private boolean isBall(BaseBallNumber playerNumber) {
        return numbers.stream()
                .anyMatch(computerNumber -> computerNumber.equalsOnlyNumber(playerNumber));
    }
}
