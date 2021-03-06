Resource Scheduler
---

This is an exercise set by a work colleague, designed to aid learning how to apply Java concurrency to 'real-world' multi-threading problem.


The description of the exercise is as follows:

<h3>Current System</h3>

You are working on a system which uses a single, very expensive, external/3rd party resource to perform some, potentially very time consuming,
operations on messages that you send to it. You are supplied with the Gateway and Message interfaces describing how to interact with the
external resource:

<ul>
<li>send messages to be processed by calling the Gateway's send(Message msg) method:</li>
</ul>

    public interface Gateway {

        public void send(Message msg)

    }
<ul>
<li>when a Message has completed processing, its completed() method will be called:</li>
</ul>
    public interface Message {

        public void completed()

    }

</ul>

<h3>Task</h3>

The number of these external resources has just been increased to allow more messages to be processed. However, as these resources are very
expensive, we want to make sure that they are not idle when messages are waiting to be processed. You should implement a class or classes
that:

<ul>
<li>can be configured with the number of resources available</li>
<li>receives Messages (and queues them up if they cannot be processed yet)</li>
<li>as available resources permit (or as they become available), sends the 'correct' message to the Gateway</li>
</ul>

<h4>Selecting the right message</h4>

<ul>
<li>Messages to the Gateway have a logical grouping and several Messages form a "group" (messages have a group ID).</li>
<li>Messages are not guaranteed to be delivered in their groups. I.E. you might get messages from group2 before you are finished with group1</li>
<li>Where possible, the message groups should not be interleaved...except where resources are idle and other work can be done.</li>
<li>The priority in which to process groups is defined by the order in which you receive the first message from the group</li>
</ul>

This is captured as a set of behaviors below:

<h4>Forwarding</h4>

The class you write must forward Messages via the Gateway interface when resources are available:
<ul>
<li>For a single resource, when one message is received, that message is sent to the gateway</li>
<li>For two resources, when two messages are received, both messages are sent to the gateway</li>
</ul>

<h4>Queuing</h4>

When no resources are available, messages should not be sent to the Gateway
<li>For a single resource, when two messages are received, only the first message is sent to the gateway</li>

<h4>Responding</h4>

As messages are completed, if there are queued messages, they should be processed
<li>Same as the queuing above, but after the first message is completed, the second message is sent to the gateway</li>

<h4>Prioritising</h4>

If there are messages belonging to multiple groups in the queue, as resources become available, we want to prioritise messages from groups
already started.

For a single resource, messages received:
<ol>
    <li>message1 (group2)</li>
    <li>message2 (group1)</li>
    <li>message3 (group2)</li>
    <li>message4 (group3)</li>
</ol>
message1 (group2) was received first so will be processed first</li>

As messages complete, the order they are sent to the gateway should be:
<ol>
    <li>message1</li>
    <li>message3 (it's part of group2, which is already "in-progress")</li>
    <li>message2</li>
    <li>message4</li>
</ol>

<h3>Extra credit</h3>

Please extend your solution to include at least one of the following features:

<h4>Cancellation</h4>

It should be possible to tell the scheduler that a group of messages has now been cancelled. Once cancelled, no further messages from that
group should sent to the Gateway.

<h4>Alternative Message Prioritisation</h4>

It should be possible to use different Message prioritisation algorithms to select the next Message from the queue. Invent a new strategy and
allow the resource scheduler to be run with this or the original algorithm easily.

<h4>Termination Messages</h4>

When a Termination Message is received, that means that it is the last Message in that group (not all groups have the same number of
messages). If further Messages belonging to that group are received, an error should be raised.

-------
<h2>My solution</h2>

I have separated out responsibility to the following classes:
<ul>
<li>SchedulingAlgorithm</li>
<li>Gateway</li>
<li>Message</li>
<li>ExpensiveResource</li>
</ul>

<b>SchedulingAlgorithm</b></br> is responsible for deciding which message is sent to the resource(s), via Gateway.

<b>Gateway</b></br> is responsible for facilitating asynchronous processing of messages sent from SchedulingAlgorithm by creating a threadpool and execute message processing in separate tasks.

<b>Message</b></br> represents each message and stores information about which group each message belongs to.

<b>ExpensiveResource</b></br> represents the resource(s) which process the message(s) and should not be left idle while there are still messages to be processed. It is responsible for updating the status of the resources in the SchedulingAlgorithm.
