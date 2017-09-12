package name.jikra.jutils.paging;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Simple class to handle paging of the list.<br>
 *     Example:
 * <pre>{@code
 *   Paging paging = new Paging(10, 2)
 *   return paging.getPageData(myListOfData)
 * }</pre>
 */
public class Paging {

    private final Optional<Long> recordsPerPage;

    private final Optional<Long> pageNumber;

    private long pageCount;

    private long dataCount;

    public Paging(final long recordsPerPage, final long pageNumber) {
        this.recordsPerPage = Optional.of(recordsPerPage);
        this.pageNumber = Optional.of(pageNumber);
    }

    public Paging() {
        this.recordsPerPage = Optional.empty();
        this.pageNumber = Optional.empty();
    }

    public <T> List<T> getPageData(@NotNull final List<T> data) {
        dataCount = data.size();

        // No paging was requested
        if (!recordsPerPage.isPresent() || !pageNumber.isPresent()) {
            return data;
        }

        // Invalid input
        if (recordsPerPage.get() < 1) {
            return new ArrayList<>();
        }

        long perPage = recordsPerPage.get();
        long page = pageNumber.get();
        long fragment = dataCount % perPage;
        long start = (perPage * page) - perPage;
        long end = start + perPage;

        // If requested page number was higher then possible, an empty list is returned.
        if (start <= dataCount && start >= 0) {
            end = end > dataCount ? dataCount : end;

            pageCount = (dataCount / perPage) + (fragment > 0 ? 1L : 0L);

            return data.subList((int) start, (int) end);
        }

        return new ArrayList<>();
    }

    public long getPageNumber() {
        return pageNumber.orElse(0L);
    }

    public long getPageCount() {
        return pageCount;
    }

    public long getDataCount() {
        return dataCount;
    }
}
