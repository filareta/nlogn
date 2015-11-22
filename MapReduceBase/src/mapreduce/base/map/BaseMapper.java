package mapreduce.base.map;

import com.pcbsys.nirvana.client.nConsumeEvent;
import mapreduce.base.BaseTask;

/**
 * Created by vlm on 11/21/2015.
 */
public abstract class BaseMapper extends BaseTask {
    private long startTime;
    @Override
    public void go(nConsumeEvent event) {
        synchronized (this) {
            if (eventsNumber == 0) {
                startTime = System.currentTimeMillis();
                System.out.println("Mapper start -> " + startTime);
            }

            map(event);

            //System.out.println("> " + eventsNumber);
            eventsNumber++;
            if (eventsNumber >= maxEventsNumber) {
                submitResults();
                System.out.println("Mapper end -> " + System.currentTimeMillis());
                System.out.println("Processing took -> " + (System.currentTimeMillis() - startTime));
                notifyAll();
            }
        }
    }

    protected abstract void map(nConsumeEvent event);
}