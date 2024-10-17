package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestServiceImplTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private IOService ioService = new StreamsIOService(new PrintStream(outContent));

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void executeTestWrongDataThrowsException() {
        TestService service = new TestServiceImpl(
                ioService,
                new CsvQuestionDao(new AppProperties("wrongPath.json")));
        assertThrows(QuestionReadException.class, service::executeTest);
    }

    @Test
    void executeTestSuccess() {
        TestService service = new TestServiceImpl(new StreamsIOService(new PrintStream(outContent)),
                new CsvQuestionDao(new AppProperties("testsQuestions.csv")));
        service.executeTest();
        assertTrue(outContent.toString().trim().contains("Please answer the questions below"));
    }
}