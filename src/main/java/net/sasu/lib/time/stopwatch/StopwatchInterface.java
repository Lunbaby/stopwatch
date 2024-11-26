package net.sasu.lib.time.stopwatch;

import net.sasu.lib.time.elapsedTime.ElapsedTime;
import net.sasu.lib.time.stopwatch.state.StopwatchState;

import java.time.Duration;
import java.time.Instant;
import java.time.InstantSource;

/**
 * Stopwatch for measuring elapsed time and providing ElapsedTime objects,
 * which can be used to output the elapsed time in a human-readable format.
 * <p>
 * For special use cases and easy testing the InstantSource is configurable.
 * @param <StopwatchType> The implementing class
 */
public interface StopwatchInterface<StopwatchType extends StopwatchInterface<StopwatchType>> {

    /**
     * Starts the stopwatch. Sets the start time and transitions the stopwatch state to STARTED.
     *
     * @return the current instance of the stopwatch for method chaining
     */
    StopwatchType start();

    /**
     * Stops the stopwatch. Sets the stop time and transitions the stopwatch state to FINISHED.
     *
     * @return the current instance of the stopwatch for method chaining
     */
    StopwatchType stop();

    /**
     * Gets the start time of the stopwatch.
     *
     * @return the {@link Instant} representing the time the stopwatch was started
     */
    Instant getStartTime();

    /**
     * Gets the stop time of the stopwatch.
     *
     * @return the {@link Instant} representing the time the stopwatch was stopped
     */
    Instant getStopTime();

    /**
     * Gets the time source used by this stopwatch.
     *
     * @return the {@link InstantSource} used to measure time
     */
    InstantSource getInstantSource();

    /**
     * Gets the current state of the stopwatch.
     *
     * @return the {@link StopwatchState} indicating the stopwatch's current state
     */
    StopwatchState getState();

    /**
     * Calculates and retrieves the elapsed time as an {@link ElapsedTime} object.
     * <p>
     * The result depends on the current state of the stopwatch:
     * <ul>
     *     <li>If {@code INITIALIZED}, the elapsed time is zero.</li>
     *     <li>If {@code STARTED}, the elapsed time is calculated up to the current time.</li>
     *     <li>If {@code FINISHED}, the elapsed time is calculated from the start to the stop time.</li>
     * </ul>
     *
     * @return an {@link ElapsedTime} object representing the elapsed time
     */
    default ElapsedTime getElapsedTime() {
        return switch (this.getState()) {
            case INITIALIZED -> new ElapsedTime(Duration.ZERO);
            case STARTED -> new ElapsedTime(getDurationUntilNow());
            case FINISHED -> new ElapsedTime(getDurationUntilStopTime());
        };
    }

    /**
     * Gets the current time from the stopwatch's time source.
     *
     * @return the {@link Instant} representing the current time
     */
    default Instant getNow() {
        return this.getInstantSource().instant();
    }

    /**
     * Retrieves the elapsed time in nanoseconds.
     *
     * @return the elapsed time in nanoseconds
     */
    default long getElapsedTimeNanos() {
        return this.getElapsedTime().getDuration().toNanos();
    }

    /**
     * Calculates the duration from the start time to the current time.
     *
     * @return the {@link Duration} from the start time to now
     */
    private Duration getDurationUntilNow() {
        return Duration.between(this.getStartTime(), Instant.now());
    }

    /**
     * Calculates the duration from the start time to the stop time.
     *
     * @return the {@link Duration} from the start time to the stop time
     */
    private Duration getDurationUntilStopTime() {
        return Duration.between(this.getStartTime(), this.getStopTime());
    }
}
