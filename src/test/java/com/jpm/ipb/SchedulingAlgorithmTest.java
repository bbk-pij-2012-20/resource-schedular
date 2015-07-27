package com.jpm.ipb;

import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by shahin.zibaee on 23/07/2015.
 */
public class SchedulingAlgorithmTest extends TestCase {

    @Mock
    private Message mockFirstMsg;//using annotation to create a mock object rather than using mock()

    public void setUp() throws Exception {

        super.setUp();
/* what I do in main method's launcher

            ExpensiveResource expensiveResource = new ExpensiveResource();
            Gateway gateway = new GatewayImpl(expensiveResource);
            SchedulingAlgorithm schedulingAlgorithm = new SchedulingAlgorithm(gateway);

            LinkedList<Message> listOfMessages = new LinkedList<>();
            listOfMessages.add(new MessageGroup2("#1"));
            listOfMessages.add(new MessageGroup1("#2"));
            listOfMessages.add(new MessageGroup2("#3"));
            listOfMessages.add(new MessageGroup2("#4"));
            listOfMessages.add(new MessageGroup1("#5"));
            listOfMessages.add(new MessageGroup1("#6"));
            listOfMessages.add(new MessageGroup2("#7"));
            listOfMessages.add(new MessageGroup1("#8"));
            listOfMessages.add(new MessageGroup2("#9"));
            listOfMessages.add(new MessageGroup1("#10"));
            listOfMessages.add(new MessageGroup2("#11"));
            listOfMessages.add(new MessageGroup1("#12"));
            listOfMessages.add(new MessageGroup1("#13"));

            schedulingAlgorithm.schedule(listOfMessages);

*/

    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testSchedule() throws Exception {



    }

    @Test
    @SuppressWarnings("unchecked")// for LinkedList.class
    public void testHasMessagesToProcess() throws Exception {

        MockitoAnnotations.initMocks(mockFirstMsg);//using annotation to create a mock object rather than using mock()
        Message mockFirstMsg = mock(MessageGroup1.class);//using mock() to create a mock object
        LinkedList<Message> mockLinkedList = mock(LinkedList.class);//mocking a concrete linked list
        when(mockLinkedList.get(0)).thenReturn(mockFirstMsg);//stubbing

    }

}