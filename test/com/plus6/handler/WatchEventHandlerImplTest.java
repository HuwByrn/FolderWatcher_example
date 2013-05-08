package com.plus6.handler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class WatchEventHandlerImplTest {

    @Mock
    private WatchEvent eventDependency;

    @Mock
    private Path pathDependency;

    private WatchEventHandlerImpl sut;

    @Before
    public void setUp() throws Exception {
        sut = new WatchEventHandlerImpl();

        eventDependency = mock(WatchEvent.class);
        pathDependency = mock(Path.class);
    }

    @After
    public void tearDown() throws Exception {
        sut = null;
        reset(eventDependency);
        reset(pathDependency);
    }

    @Test
    public void onCreate_should_only_handle_airi_file_extensions() {

       when(pathDependency.getFileName()).thenReturn( Paths.get("testfile.airi"));
       when(eventDependency.context()).thenReturn(pathDependency);

       sut.onCreate(eventDependency);

    }

    public void testOnModify() throws Exception {

    }

    public void testOnDelete() throws Exception {

    }
}
