package by.training.epam.analyzer.start;


import by.training.epam.analyzer.bean.Node;
import by.training.epam.analyzer.exception.HandlingException;
import by.training.epam.analyzer.service.AnalyseService;
import by.training.epam.analyzer.service.factory.ServiceFactory;


public class Main {

    private static String noteFile = "D:\\Epam\\task04tryhelp\\src\\main\\java\\by\\training\\epam\\analyzer\\notes.xml";
    private static String breakfastFile = "D:\\Epam\\task04tryhelp\\src\\main\\java\\by\\training\\epam\\analyzer\\breakfastMenu.xml";

    public static void main(String[] args) {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        AnalyseService service = serviceFactory.getAnalyseService();

        Node node;

        System.out.println("Notes file: ");
        try {

            service.initialize(noteFile);
            while ((node = service.next()) != null) {
                System.out.println(node);
            }
            service.close();


            System.out.println(" ");
            System.out.println("Breakfast file: ");

            service.initialize(breakfastFile);
            while ((node = service.next()) != null) {
                System.out.println(node);
            }
            service.close();
        } catch (HandlingException e) {
            e.printStackTrace();
        }

    }
}
