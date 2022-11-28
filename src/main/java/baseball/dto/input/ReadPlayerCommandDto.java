package baseball.dto.input;

public class ReadPlayerCommandDto {

    private final String playerCommand;

    public ReadPlayerCommandDto(String playerCommand) {
        this.playerCommand = playerCommand;
    }

    public String getPlayerCommand() {
        return playerCommand;
    }
}
