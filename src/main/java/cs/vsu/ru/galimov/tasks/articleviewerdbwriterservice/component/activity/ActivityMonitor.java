package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.component.activity;

import org.springframework.stereotype.Component;

@Component
public class ActivityMonitor {

    private long lastActivityTimestamp = System.currentTimeMillis();

    public void updateLastActivity() {
        this.lastActivityTimestamp = System.currentTimeMillis();
    }

    public boolean isInactive(long timeoutMillis) {
        return System.currentTimeMillis() - lastActivityTimestamp > timeoutMillis;
    }
}
