package mapreduce.base.reduce;

import com.pcbsys.nirvana.client.nConsumeEvent;
import mapreduce.base.BaseTask;

/**
 * Created by vlm on 11/21/2015.
 */
public abstract class BaseReducer extends BaseTask {
    @Override
    public void go(nConsumeEvent event) {
        synchronized (this) {
            if (eventsNumber == 0) {
                System.out.println("Reducer start -> " + System.currentTimeMillis());
            }

            reduce(event);

            eventsNumber++;
            if (eventsNumber >= maxEventsNumber) {
                submitResults();
                System.out.println("Reducer end -> " + System.currentTimeMillis());
                notifyAll();
            }
        }
    }

    public abstract void reduce(nConsumeEvent event);
}
