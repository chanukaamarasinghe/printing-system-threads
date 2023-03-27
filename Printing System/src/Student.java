public class Student implements Runnable {
    private final String name;
    private final Printer printer;

    public Student(String name, Printer printer) {
        this.name = name;
        this.printer = printer;
    }

    @Override
    public void run() {
        Document[] documents = new Document[5];
        for (int i = 1; i <= 5; i++) {
            int minPages = 10;
            int maxPages = 15;

            int numberOfPages = (int) (Math.random() * (maxPages - minPages) + minPages);
            documents[i - 1] = new Document(this.name, "CW_" + i, numberOfPages);
        }
        int numberOfPages = 0;

        for (Document document : documents) {
            printer.printDocument(document);
            numberOfPages += document.getNumberOfPages();

            int minSleepingTime = 1;
            int maxSleepingTime = 5;
            int num = (int) (Math.random() * (maxSleepingTime - minSleepingTime) + minSleepingTime);

            try {
//                System.out.println("[" + Thread.currentThread().getName() + "]" + " - Pausing work for " + num + " seconds");
                Thread.sleep(num * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(ConsoleColors.GREEN + "[" + this.name + "]" + " - Completed Printing All Documents: " +
                "5 Documents, " + numberOfPages + " pages" + ConsoleColors.RESET);

    }
}
