package baseball.dto.input;

public class ReadPlayerAnswerDto {

    private final String playerInput;

    public ReadPlayerAnswerDto(String playerInput) {
        this.playerInput = playerInput;
    }

    public String getPlayerInput() {
        return playerInput;
    }
}
