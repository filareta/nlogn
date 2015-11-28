package mapreduce.impl.map;

import com.pcbsys.nirvana.client.*;
import data.SortingMockData;
import mapreduce.base.map.BaseMapper;

/**
 * Created by vlm on 11/21/2015.
 */
public class SortingMapper extends BaseMapper {
    int[] numbers;
    public SortingMapper() {
        super();
        maxEventsNumber = SortingMockData.NUMBER_OF_EVENTS;
        numbers = new int[maxEventsNumber + 5];
    }

    @Override
    protected void map(nConsumeEvent event) {
        int number = event.getProperties().getInt("number");
        int insertIndex = insertAt(numbers, number);

        for (int i = eventsNumber + 1; i > insertIndex; i--) {
            numbers[i] = numbers[i - 1];
        }
        numbers[insertIndex] = number;
    }

    @Override
    protected void submitResults() {
        nEventProperties props = new nEventProperties();
        props.put("numbers", numbers);
        props.put("length", SortingMockData.NUMBER_OF_EVENTS);
        nConsumeEvent event = new nConsumeEvent(props, "mapperData".getBytes());

        try {
            outputChannel.publish(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int insertAt(int[] a, int key) {
        if (eventsNumber == 0) {
            return 0;
        }

        int lo = 0;
        int hi = eventsNumber;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key == a[mid]) {
                return mid;
            }

            if (mid == eventsNumber) {
                if (a[mid] > key) {
                    return mid - 1;
                } else {
                    return mid;
                }
            }
            if (mid == 0) {
                if (a[mid] > key) {
                    return mid;
                } else {
                    return mid + 1;
                }
            }

            if (a[mid] <= key && a[mid + 1] > key) {
                return mid;
            }

            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
        }
        return 0;
    }
}
