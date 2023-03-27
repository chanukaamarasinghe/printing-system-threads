public class PrintingSystem {
    public static void main(String[] args) throws InterruptedException {
        LaserPrinter laserPrinter = new LaserPrinter(001, 250, 250);

        System.out.println(laserPrinter);

        ThreadGroup studentThreadGroup = new ThreadGroup("Student Thread Group");
        ThreadGroup technicianThreadGroup = new ThreadGroup("Technician Thread Group");

        Thread[] students = new Thread[4];

        for (int i = 0; i < 4; i++) {
            Runnable runnableStudent = new Student("Student " + (i + 1), laserPrinter);
            students[i] = new Thread(studentThreadGroup, runnableStudent, "Student " + (i + 1));
        }

        Runnable runnablePaperTechnician = new PaperTechnician("Paper Technician", laserPrinter);
        Runnable runnableTonerTechnician = new TonerTechnician("Toner Technician", laserPrinter);
        Thread paperTechnician = new Thread(technicianThreadGroup, runnablePaperTechnician, "Paper Technician");
        Thread tonerTechnician = new Thread(technicianThreadGroup, runnableTonerTechnician, "Toner Technician");

        for (Thread thread : students) {
            thread.start();
        }

        paperTechnician.start();
        tonerTechnician.start();

        for (Thread thread : students) {
            thread.join();
        }

        System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BLACK
                + "ALL DOCUMENTS COMPLETED PRINTING"
                + ConsoleColors.RESET);

        System.out.println(laserPrinter);
        System.out.println(runnablePaperTechnician);
        System.out.println(runnableTonerTechnician);

        if (paperTechnician.isAlive() || tonerTechnician.isAlive()) {
            System.out.println("Technicians are still running. Manually stopping work...");
            System.exit(0);
        }
    }
}