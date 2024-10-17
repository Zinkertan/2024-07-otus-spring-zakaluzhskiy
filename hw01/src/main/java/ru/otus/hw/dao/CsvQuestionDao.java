package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {

        try (var questionInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(fileNameProvider.getTestFileName())
        ) {
            return new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(questionInputStream))
                    .withType(QuestionDto.class)
                    .withSkipLines(1)
                    .withSeparator(';')
                    .build()
                    .parse()
                    .stream()
                    .map(questionDto -> questionDto.toDomainObject())
                    .collect(Collectors.toUnmodifiableList());
        } catch (NullPointerException | IOException exception) {
            throw new QuestionReadException(exception.getMessage(), exception);
        }
    }
}
