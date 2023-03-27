public interface ServicePrinter extends Printer {

    // from Printer:
    @Override
    void printDocument(Document document);

    // Printer constants
    int Full_Paper_Tray = 250;
    int SheetsPerPack = 50;

    int Full_Toner_Level = 500;
    int Minimum_Toner_Level = 10;


    // Technician methods
    void replaceTonerCartridge();

    void refillPaper();
}

