public class LaserPrinter implements ServicePrinter {
    private final int printerId;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int documentsPrinted = 0;
    private int paperRefilledCount = 0;
    private int tonerRefilledCount = 0;

    public LaserPrinter(int printerId, int currentPaperLevel, int currentTonerLevel) {
        this.printerId = printerId;
        this.currentPaperLevel = currentPaperLevel;
        this.currentTonerLevel = currentTonerLevel;
    }

    public int getPaperRefilledCount() {
        return paperRefilledCount;
    }

    public int getTonerRefilledCount() {
        return tonerRefilledCount;
    }

    @Override
    public String toString() {
        return ConsoleColors.BLUE + "[LaserPrinter - " + printerId + "] - Paper Level:" + currentPaperLevel + ", Toner Level:" +
                currentTonerLevel + ", Documents Printed:" + documentsPrinted + ConsoleColors.RESET;
    }

    @Override
    public synchronized void printDocument(Document document) {
        String studentName = document.getUserID();
        String documentName = document.getDocumentName();
        int numberOfPages = document.getNumberOfPages();
        System.out.println("[" + studentName + "]" + " - Preparing to print " + documentName + " (" + numberOfPages + " pages)");
        while (this.currentPaperLevel < numberOfPages || this.currentTonerLevel < numberOfPages) {

            // application will force to stop when toner is not enough to print but not low enough to refill
            if (this.currentTonerLevel < numberOfPages && this.currentTonerLevel > Minimum_Toner_Level) {
                System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK +
                        " NOT ENOUGH TONER " + ConsoleColors.RESET);
                System.out.println("replaceToner() cannot be called... \nManually exiting application ...");
                System.exit(0);
            }

            // force to stop the application if replaceTonerCartridge() has already been called 3 times
            if (this.currentTonerLevel < numberOfPages && tonerRefilledCount == 3) {
                System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK +
                        " NOT ENOUGH TONER " + ConsoleColors.RESET);
                System.out.println("replaceTonerCartridge() already called three times ... \nManually exiting application ...");
                System.exit(0);
            }

            // force to stop the application if refillPaper() has already been called 3 times
            if (this.currentPaperLevel < numberOfPages && paperRefilledCount == 3) {
                System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK +
                        " NOT ENOUGH PAPER " + ConsoleColors.RESET);
                System.out.println("refillPaper() already called three times ... \nManually exiting application ...");
                System.exit(0);
            }
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentPaperLevel -= numberOfPages;
        this.currentTonerLevel -= numberOfPages;
        this.documentsPrinted++;
        System.out.println("[" + studentName + "]" + " - Document " + documentName + " printed - " + numberOfPages + " pages");
        System.out.println(this);
        notifyAll();
    }

    @Override
    // add 50 more papers if there is space in the tray
    public synchronized void refillPaper() {
        while (this.currentPaperLevel > ((Full_Paper_Tray - SheetsPerPack))) {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentPaperLevel += SheetsPerPack;
        paperRefilledCount++;
        System.out.println(ConsoleColors.YELLOW + "[LaserPrinter] - Paper refilled" + ConsoleColors.RESET);
        System.out.println(this);
        notifyAll();
    }

    @Override
    // replace toner cartridge if toner level is below 10
    public synchronized void replaceTonerCartridge() {
        while (this.currentTonerLevel >= Minimum_Toner_Level) {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentTonerLevel = Full_Toner_Level;
        tonerRefilledCount++;
        System.out.println(ConsoleColors.YELLOW + "[LaserPrinter] - Toner refilled" + ConsoleColors.RESET);
        System.out.println(this);
        notifyAll();
    }
}
