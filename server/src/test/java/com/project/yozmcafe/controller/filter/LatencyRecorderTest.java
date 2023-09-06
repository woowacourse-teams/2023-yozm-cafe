package com.project.yozmcafe.controller.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.runAsync;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LatencyRecorderTest {

    private final LatencyRecorder latencyRecorder = new LatencyRecorder();

    @Test
    @DisplayName("start를 호출하지 않고 getDurationSeconds을 호출하면 숫자 파싱할 때 예외가 발생한다")
    void notStart() {
        assertThatThrownBy(latencyRecorder::getDurationSeconds)
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("start를 호출하고 getDurationSeconds을 호출하면 예외가 발생하지 않는다")
    void started() {
        //given
        latencyRecorder.start();

        //when, then
        assertDoesNotThrow(latencyRecorder::getDurationSeconds);
    }

    @Test
    @DisplayName("다른 스레드에서 start를 호출해도 영향을 받지 않아서 예외가 발생한다")
    void multiThreads() throws InterruptedException {
        //given
        startInOtherThread();

        //when
        final CompletableFuture<Void> completableFuture = runAsync(notThrowIfStarted(latencyRecorder));

        //when
        assertThatThrownBy(completableFuture::get)
                .isInstanceOf(ExecutionException.class);
    }

    @Test
    @DisplayName("start와 getDurationSeconds 사이의 시간을 계산할 수 있다")
    void getDurationSeconds() {
        //given
        latencyRecorder.start();

        //when
        System.out.println("시간 차이 만들기");
        final double durationSeconds = latencyRecorder.getDurationSeconds();

        //then
        assertThat(durationSeconds).isNotZero();
    }

    private void startInOtherThread() throws InterruptedException {
        final Thread thread = new Thread(latencyRecorder::start);
        thread.start();
        thread.join();
    }

    private static Runnable notThrowIfStarted(final LatencyRecorder latencyRecorder) {
        return latencyRecorder::getDurationSeconds;
    }
}
