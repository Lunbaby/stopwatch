package net.sasu.lib.time.stopwatch.example;

import net.sasu.lib.time.stopwatch.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultStopwatchExample {

	public static void  main(String[] args) throws InterruptedException {
		Stopwatch stopwatch = new Stopwatch().start();
		Thread.sleep(1111); //let 1.111 seconds elapse

		//prints elapsed time to default output target (system out) in format HH:mm:ss.SSS
		//this prints "00:00:01.111"
		stopwatch.stop().getElapsedTime().println();

		//or maybe you want to use a Logger (e.g. SLF4J) instead?
		//(or any method implementing java.util.function.Consumer<String>)
		Logger logger = LoggerFactory.getLogger(DefaultStopwatchExample.class);
		stopwatch.getElapsedTime().printTo(logger::info);

		//you can apply individual formatting (see Apache DurationFormatUtils for syntax)
		//this prints "1 seconds and 111 milliseconds"
		stopwatch.getElapsedTime().formatDuration("s' seconds and 'S' milliseconds'").println();

		//this prints "1.111 s"
		stopwatch.getElapsedTime().formatDuration("s'.'SSS' s'").println();
	}
	
}
