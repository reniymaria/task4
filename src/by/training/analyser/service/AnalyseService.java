package by.training.analyser.service;

import java.util.List;

import by.training.analyser.bean.Node;
import by.training.analyser.exception.HandlingException;

public interface AnalyseService {

	void initialize(String fileName) throws HandlingException;

	List<Node> getAll() throws HandlingException ;

	Node next() throws HandlingException;
	
	void close()throws HandlingException ;

}
