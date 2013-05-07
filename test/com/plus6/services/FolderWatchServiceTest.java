package com.plus6.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Huw
 * Date: 22/04/13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class FolderWatchServiceTest {
    private FolderWatchService sut;
    @Mock
    private Path pathDependency;

    @Before
    public void setUp() throws Exception {
        sut = new FolderWatchService();
        pathDependency = mock(Path.class);


    }

    @After
    public void tearDown() throws Exception {
        sut.destroy();
        reset(pathDependency);
    }

    @Test
    public void register_should_make_a_WatchService() {
        sut.register(pathDependency);
        assertThat(sut.getWatcher(), is(WatchService.class));
    }

    @Test
    public void register_should_register_watcher_with_given_Watchable() {
        sut.register(pathDependency);
        ArgumentCaptor<WatchEvent.Kind> argument = ArgumentCaptor.forClass(WatchEvent.Kind.class);
        try {
            verify(pathDependency).register(eq(sut.getWatcher()), argument.capture(), argument.capture());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void register_should_register_watcher_for_StandardWatchEventKinds() {
        sut.register(pathDependency);
        try {
            verify(pathDependency).register(sut.getWatcher(), ENTRY_CREATE, ENTRY_MODIFY );
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }



    @Test
    public void register_should_store_created_WatchKey() {
        WatchKey key = getWatchKey();
        assertThat(sut.getKeys(), not(hasKey(key)));
        sut.register(pathDependency);
        assertThat(sut.getKeys(), hasKey(key));

    }

    @Test
    public void register_should_map_created_WatchKey_to_Path() {
        WatchKey key = getWatchKey();
        sut.register(pathDependency);
        assertThat(sut.getKeys().get(key), is(pathDependency));

    }

    @Test
    public void kill_should_prevent_run_loop() throws IOException {
        Path path = FileSystems.getDefault().getPath("C:/Temp");
        sut.register(path);
        sut.kill();
        sut.run();
        assertTrue(true);
    }

//    @Test
//    public void
    private WatchKey getWatchKey() {
        WatchKey key = mock(WatchKey.class);

        try {
            stub(pathDependency.register(any(WatchService.class), any(WatchEvent.Kind.class),any(WatchEvent.Kind.class))).toReturn(key);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return key;
    }
}
