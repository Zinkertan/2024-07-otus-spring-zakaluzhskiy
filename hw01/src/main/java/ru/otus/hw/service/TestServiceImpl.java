package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final CsvQuestionDao csvQuestionDao;

    private void printAnswer(Answer answer, AtomicInteger answerIndex) {
        ioService.printFormattedLine("\t%d) %s", answerIndex.incrementAndGet(), answer.text());
    }

    private void printQuestion(Question question, AtomicInteger questionIndex) {
        ioService.printFormattedLine("%d. %s", questionIndex.incrementAndGet(), question.text());
        AtomicInteger answerIndex = new AtomicInteger();
        question.answers().forEach(answer -> printAnswer(answer, answerIndex));
    }

    private void printAllQuestions() {
        AtomicInteger questionIndex = new AtomicInteger();
        csvQuestionDao.findAll().forEach(question -> {
            printQuestion(question, questionIndex);
        });
    }

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        printAllQuestions();
    }

}
