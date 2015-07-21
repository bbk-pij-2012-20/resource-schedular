package com.jpm.ipb;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerTest {

    @Mock
    private Message mockFirstMsg;//using annotation to create a mock object rather than using mock()

    @Before
    public void setUp() throws Exception {



    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    @SuppressWarnings("unchecked")
    public void testHasMessagesToProcess() throws Exception {

        MockitoAnnotations.initMocks(mockFirstMsg);//using annotation to create a mock object rather than using mock()
        Message mockFirstMsg = mock(MessageGroup1.class);//using mock() to create a mock object
        LinkedList<Message> mockLinkedList = mock(LinkedList.class);//mocking a concrete linked list
        when(mockLinkedList.get(0)).thenReturn(mockFirstMsg);//stubbing

    }

    @Test
    public void testSchedule() throws Exception {



    }
}