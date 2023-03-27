public class PaperTechnician extends Technician implements Runnable {
    private final String name;
    private final LaserPrinter printer;

    public PaperTechnician(String name, LaserPrinter printer) {
        this.name = name;
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            printer.refillPaper();
            Technician.sleep(this.name);
        }
        System.out.println("[" + this.name + "]" + " Finished, packs of paper used: " + printer.getPaperRefilledCount());
    }

    @Override
    public String toString() {
        return "[Paper Technician] - Paper stacks replaced = " + printer.getPaperRefilledCount();
    }
}
