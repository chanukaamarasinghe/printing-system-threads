public class Technician {
    static void sleep(String name) {
        // time in minutes
        int minSleepingTime = 1;
        int maxSleepingTime = 5;

        int num = (int) (Math.random() * (maxSleepingTime - minSleepingTime) + minSleepingTime);
        try {
            Thread.sleep(num * 1000L);
            System.out.println(ConsoleColors.RED + "[" + name + "]" + " - sleeping for " + num + " second(s)"
                    + ConsoleColors.RESET);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
