package baseball.domain.number;

import baseball.domain.number.exception.WrongGeneratorException;
import baseball.utils.consts.GameNumberConst;
import baseball.utils.generator.BaseBallNumberGenerator;
import baseball.utils.message.ExceptionMessageUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseBallNumbers {

    private static final String SPLIT_SEPARATOR = "";

    private final List<BaseBallNumber> numbers;

    public BaseBallNumbers(BaseBallNumberGenerator generator) {
        this.numbers = createComputerAnswer(generator);
    }

    public BaseBallNumbers(String playerInput) {
        List<BaseBallNumber> playerAnswer = createPlayerAnswer(playerInput);

        validatePlayerAnswer(playerAnswer);
        this.numbers = playerAnswer;
    }

    private List<BaseBallNumber> createComputerAnswer(BaseBallNumberGenerator generator) {
        List<Integer> randomNumbers = createRandomNumbers(generator);

        return IntStream.range(0, GameNumberConst.NUMBER_SIZE)
                .mapToObj(index -> new BaseBallNumber(randomNumbers.get(index), index))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<Integer> createRandomNumbers(BaseBallNumberGenerator generator) {
        List<Integer> randomNumbers = new ArrayList<>();

        while (randomNumbers.size() < GameNumberConst.NUMBER_SIZE) {
            randomNumbers.add(createUniqueNumber(randomNumbers, generator));
        }
        return randomNumbers;
    }

    private Integer createUniqueNumber(List<Integer> randomNumbers, BaseBallNumberGenerator generator) {
        int randomNumber = createValidRandomNumber(generator);

        while (randomNumbers.contains(randomNumber)) {
            randomNumber = createValidRandomNumber(generator);
        }
        return randomNumber;
    }

    private Integer createValidRandomNumber(BaseBallNumberGenerator generator) {
        int randomNumber = generator.generate();

        validateRandomNumber(randomNumber);
        return randomNumber;
    }

    private void validateRandomNumber(int randomNumber) {
        if (!(GameNumberConst.MIN_VALUE <= randomNumber && randomNumber <= GameNumberConst.MAX_VALUE)) {
            throw new WrongGeneratorException();
        }
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

    public long calculateStrike(final BaseBallNumbers playerAnswer) {
        return playerAnswer.numbers.stream()
                .filter(this::isStrike)
                .count();
    }

    public long calculateBall(final BaseBallNumbers playerAnswer) {
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
