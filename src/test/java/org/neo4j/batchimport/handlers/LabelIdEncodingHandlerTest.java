package org.neo4j.batchimport.handlers;

import static java.util.Arrays.copyOf;
import static org.junit.Assert.assertEquals;
import static org.neo4j.batchimport.handlers.LabelIdEncodingHandler.LABEL_BITS;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Michael Hunger @since 02.11.13
 */
@Ignore
public class LabelIdEncodingHandlerTest {
    @Test
    public void testEncodeLabels() throws Exception {
        int[] ids = new int[] {3,2,6,4,150,2,0,0};
        assertEquals(-1, LabelIdEncodingHandler.encodeLabels(copyOf(ids, 8), 8));
        assertEquals(-1, LabelIdEncodingHandler.encodeLabels(copyOf(ids, 5), 5));
        int c=1;
        assertEquals((long)c << LABEL_BITS | 3L, LabelIdEncodingHandler.encodeLabels(copyOf(ids, c), c));
        c = 2;
        assertEquals((long)c << LABEL_BITS | 2L << LABEL_BITS/c | 3L, LabelIdEncodingHandler.encodeLabels(copyOf(ids, c), c));
        assertEquals(275416351747L, LabelIdEncodingHandler.encodeLabels(copyOf(ids, 4), 4));
    }

    @Test(timeout = 250)
    public void testPerformance() throws Exception {
        int[] ids = new int[] {3,2,6,4,150,2,0,0};
        long time=System.currentTimeMillis();
        for (int i=0;i<1_0000_000;i++) {
        	LabelIdEncodingHandler.encodeLabels(ids, 4);
        }
        time = System.currentTimeMillis()-time;
        System.out.println("time = " + time);
    }
}
