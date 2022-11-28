package baseball.dto.output;

public class PrintResultDto {

    private final long strike;
    private final long ball;

    public PrintResultDto(long strike, long ball) {
        this.strike = strike;
        this.ball = ball;
    }

    public long getStrike() {
        return strike;
    }

    public long getBall() {
        return ball;
    }
}
