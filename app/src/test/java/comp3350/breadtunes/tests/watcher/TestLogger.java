package comp3350.breadtunes.tests.watcher;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

public abstract class TestLogger {
    @Rule
    public MethodRule watchman = new TestWatchman() {
        @Override
        public void starting(FrameworkMethod method) {
            System.out.println("\nStarting " + method.getName());
        }

        @Override
        public void succeeded(FrameworkMethod method) {
            System.out.println("PASS: " + method.getName());
        }

        @Override
        public void failed(Throwable e, FrameworkMethod method) {
            System.out.println("FAIL: " + method.getName() + ", Exception: " + e.getClass().getSimpleName());
        }
    };
}
