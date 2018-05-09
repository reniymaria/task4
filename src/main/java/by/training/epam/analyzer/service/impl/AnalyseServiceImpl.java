package by.training.epam.analyzer.service.impl;

import by.training.epam.analyzer.bean.Node;
import by.training.epam.analyzer.exception.HandlingException;
import by.training.epam.analyzer.service.AnalyseService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class AnalyseServiceImpl implements AnalyseService {

	private static final String MESSAGE_FOR_BUFREADER = "BuffReader isn't initialized";
	private static final String CLOSE_BUFF_READER = "Can't close buffReader";
	private static final String FILE_PATH = "File not found. Check the Path: ";

	private BufferedReader buffReader;

	public void initialize(String fileName) throws HandlingException {

		try {

			buffReader = new BufferedReader(new FileReader(fileName));

		} catch (FileNotFoundException e) {
			throw new HandlingException(FILE_PATH + fileName, e);
		}

	}

	public Node next() throws HandlingException {

		Analyser analyser = new Analyser();
		String nodeData = analyser.findNodes(buffReader);

		Node node = null;

		if (nodeData != null) {
			node = Analyser.analyseNodeData(nodeData);
		} else {
			try {
				buffReader.close();
			} catch (IOException e) {
				throw new HandlingException(MESSAGE_FOR_BUFREADER, e);
			}
		}

		return node;
	}


	public void close() throws HandlingException {

		try {
			if (buffReader != null) {
				buffReader.close();
			}
		} catch (IOException e) {
			throw new HandlingException(CLOSE_BUFF_READER, e);
		} finally {
			buffReader = null;
		}

	}

}
