package baseball.view;

import baseball.dto.input.ReadPlayerAnswerDto;
import baseball.dto.input.ReadPlayerCommandDto;
import baseball.dto.output.PrintExceptionMessageDto;
import baseball.dto.output.PrintResultDto;
import baseball.view.exception.NotFoundViewException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class IOViewResolver {

    private final Map<Class<?>, Consumer<Object>> outputViewMappings = new HashMap<>();
    private final Map<Class<?>, Supplier<Object>> inputViewMappings = new HashMap<>();

    public IOViewResolver(final InputView inputView, final OutputView outputView) {
        initInputViewMappings(inputView);
        initOutputViewMappings(outputView);
    }

    private void initInputViewMappings(final InputView inputView) {
        inputViewMappings.put(ReadPlayerAnswerDto.class, inputView::readPlayerAnswer);
        inputViewMappings.put(ReadPlayerCommandDto.class, inputView::readPlayerCommand);
    }

    private void initOutputViewMappings(final OutputView outputView) {
        outputViewMappings.put(PrintResultDto.class, dto -> outputView.printResult((PrintResultDto) dto));
        outputViewMappings
            .put(PrintExceptionMessageDto.class, dto -> outputView
                .printExceptionMessage((PrintExceptionMessageDto) dto));
    }

    public <T> T inputViewResolve(final Class<T> type) {
        try {
            return type.cast(inputViewMappings.get(type).get());
        } catch (NullPointerException e) {
            throw new NotFoundViewException();
        }
    }

    public void outputViewResolve(final Object dto) {
        try {
            outputViewMappings.get(dto.getClass()).accept(dto);
        } catch (NullPointerException e) {
            throw new NotFoundViewException();
        }
    }
}
