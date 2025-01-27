# Reproducer for Quarkus Graphql concurrent requests issue

This is a reproducer for a issue with Quarkus and Smallrye Graphql 
where the number of max concurrent requests seems to be the number of 
event loop threads.

## Running 

* Run `quarkus:dev` in the third-party folder.
* Run `mvn package &&  java -jar target/quarkus-app/quarkus-run.jar > out` in the server folder.
* Run `quarkus:dev` in the client folder.

## Observations
* The log contains messages like `2025-01-27 20:46:17,009 INFO  [org.acm.HelloGraphQLResource] (executor-thread-2) sayHello2 Name X current: {Y}`
  where Y is the number of event loop threads, `quarkus.vertx.event-loops-pool-size=Y`
* Thread dumps show in most cases (`ps aux | grep quarkus-app`, then `kill -3 PID`)
    ```
    "vert.x-eventloop-thread-0" #34 [151768] prio=5 os_prio=0 cpu=703,24ms elapsed=60,20s tid=0x000071b664e706e0 nid=151768 runnable  [0x000071b637cfd000]
       java.lang.Thread.State: RUNNABLE
	    at sun.nio.ch.EPoll.wait(java.base@21.0.4/Native Method)
	    at sun.nio.ch.EPollSelectorImpl.doSelect(java.base@21.0.4/EPollSelectorImpl.java:121)
	    at sun.nio.ch.SelectorImpl.lockAndDoSelect(java.base@21.0.4/SelectorImpl.java:130)
	    - locked <0x0000000709f52e38> (a io.netty.channel.nio.SelectedSelectionKeySet)
	    - locked <0x0000000709f50118> (a sun.nio.ch.EPollSelectorImpl)
	    at sun.nio.ch.SelectorImpl.select(java.base@21.0.4/SelectorImpl.java:142)
	    at io.netty.channel.nio.SelectedSelectionKeySetSelector.select(SelectedSelectionKeySetSelector.java:62)
	    at io.netty.channel.nio.NioEventLoop.select(NioEventLoop.java:883)
	    at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:526)
	    at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:997)
	    at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	    at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	    at java.lang.Thread.runWith(java.base@21.0.4/Thread.java:1596)
	    at java.lang.Thread.run(java.base@21.0.4/Thread.java:1583)
    ```
    A sample log is available in server/out.
