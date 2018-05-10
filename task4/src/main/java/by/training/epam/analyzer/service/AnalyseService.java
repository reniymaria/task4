package by.training.epam.analyzer.service;


import by.training.epam.analyzer.bean.Node;
import by.training.epam.analyzer.exception.HandlingException;


public interface AnalyseService {

	void initialize(String fileName) throws HandlingException;

	Node next() throws HandlingException;
	
	void close()throws HandlingException ;

}
