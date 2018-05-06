package by.training.analyser.start;
import java.util.List;

import by.training.analyser.bean.Node;
import by.training.analyser.exception.HandlingException;
import by.training.analyser.service.AnalyseService;
import by.training.analyser.service.factory.ServiceFactory;

public class Main {

    //Should be changed the rout!!!!
	private static String fileNameNotes = "D:\\Epam\\Task4\\src\\by\\training\\analyser\\notes.xml";
	private static String fileNamebreakfastMenu = "D:\\Epam\\Task4\\src\\by\\training\\analyser\\breakfastMenu.xml";

	public static void main(String[] args) {

		ServiceFactory sf = ServiceFactory.getInstance();
		AnalyseService service = sf.getAnalyseService();


		System.out.println("Notes File: ");
		try {
			service.initialize(fileNameNotes);
            List<Node> listNotes = service.getAll();
            for (Node node : listNotes) {
                System.out.println(node);
            }
			service.close();

			System.out.println("\n");
			System.out.println("BreakfastMenu File: ");

			service.initialize(fileNamebreakfastMenu);
			List<Node> listBreakfast = service.getAll();

			for (Node node : listBreakfast) {
				System.out.println(node);
			}
			service.close();

		} catch (HandlingException e) {
			e.printStackTrace();
		}

	}
}
