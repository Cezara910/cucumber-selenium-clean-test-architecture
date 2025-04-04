package framework;

import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ContainerManager {
    private static final Set<BrowserWebDriverContainer<?>> containers = new HashSet<>();

    private final static DockerImageName ARM_CHROME_IMAGE = DockerImageName.parse("seleniarm/standalone-chromium");
    private static final DockerImageName CHROME_IMAGE = DockerImageName.parse("selenium/standalone-chrome");

    public static BrowserWebDriverContainer<?> provideContainer(BrowserType browserType) {
        BrowserWebDriverContainer<?> container = null;
        if (browserType == BrowserType.CHROME) {
            if (resolveCPUType().contains("M1"))
                container =  new BrowserWebDriverContainer<>(ARM_CHROME_IMAGE.asCompatibleSubstituteFor(CHROME_IMAGE));
        }
        else container = new BrowserWebDriverContainer<>();
        containers.add(container);
        return container;
    }
    public static void stopContainers(){
        containers.forEach(BrowserWebDriverContainer::stop);
        containers.clear();
    }
    private static String resolveCPUType(){
        if(System.getProperty("os.name").contains("Mac OS")){
            String details;
            ProcessBuilder pb = new ProcessBuilder("sysctl", "-n", "machdep.cpu.brand_string");
            try {
                Process p = pb.start();
                try(BufferedReader br = p.inputReader()) {
                    details = br.readLine();
                }
                int status = p.waitFor();
                if (status == 0) {
                    // Command succeeded.
                }
                else {
                    // log fail
                }
                return details;
            }
            catch (InterruptedException | IOException x) {
                x.printStackTrace();
            }
        };
        return "not ARM64 ARCHITECTURE";
    }
}
