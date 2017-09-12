package name.jikra.jutils.paging;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PagingTest {
    private List<Integer> data;

    @Before
    public void createData() {
        data = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33);
    }

    @Test
    public void testCorrectFullPage() {
        Paging paging = new Paging(10, 2);
        List<Integer> pageData = paging.getPageData(data);

        assertTrue(!pageData.isEmpty());
        assertEquals(pageData.size(), 10);
        assertEquals(pageData.get(0).intValue(), 11);
    }

    @Test
    public void testCorrectLastPage() {
        Paging paging = new Paging(10, 4);
        List<Integer> pageData = paging.getPageData(data);

        assertFalse(pageData.isEmpty());
        assertEquals(pageData.size(), 3);
        assertEquals(pageData.get(0).intValue(), 31);
    }

    @Test
    public void testIncorrectRequestedPageNumberIsHigherThenTheLastPageNumber() {
        Paging paging = new Paging(10, 5);
        List<Integer> pageData = paging.getPageData(data);

        assertTrue(pageData.isEmpty());
    }

    @Test
    public void testIncorrectRequestedPageNumberIsZero() {
        Paging paging = new Paging(10, 0);
        List<Integer> pageData = paging.getPageData(data);

        assertTrue(pageData.isEmpty());
    }

    @Test
    public void testIncorrectRequestedPageNumberIsLowerThenZero() {
        Paging paging = new Paging(10, -1);
        List<Integer> pageData = paging.getPageData(data);

        assertTrue(pageData.isEmpty());
    }

    @Test
    public void testIncorrectRequestedRppIsLowerThenOne() {
        Paging paging = new Paging(0, 1);
        List<Integer> pageData = paging.getPageData(data);

        assertTrue(pageData.isEmpty());
    }

    @Test
    public void testIncorrectRequestedRppIsHigherThenDataSize() {
        Paging paging = new Paging(50, 1);
        List<Integer> pageData = paging.getPageData(data);

        // Full stack of data expected
        assertFalse(pageData.isEmpty());
        assertEquals(pageData.size(), 33);
        assertEquals(pageData.get(pageData.size() - 1).intValue(), 33);
    }

    @Test
    public void testEmptyData() {
        Paging paging = new Paging(10, 1);
        List<Integer> pageData = paging.getPageData(new ArrayList<Integer>());

        assertTrue(pageData.isEmpty());
    }
}
