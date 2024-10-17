package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IOServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IOService ioService = new StreamsIOService(new PrintStream(outContent));


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void ioService_printLine_Success() {
        ioService.printLine("hello");
        assertEquals("hello" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void ioService_pintFormattedLine_Success() {
        ioService.printFormattedLine("hello %s","OTUS");
        assertEquals("hello %s".formatted("OTUS") + System.lineSeparator(), outContent.toString());
    }

}