package by.training.analyser.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.training.analyser.bean.Node;
import by.training.analyser.exception.HandlingException;
import by.training.analyser.service.AnalyseService;
import by.training.analyser.service.util.NodeParser;


public class AnalyseServiceImpl implements AnalyseService {

    private BufferedReader buffReader;

    @Override
    public void initialize(String fileName) throws HandlingException {
        try {
            buffReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new HandlingException("File: '" + fileName + "' doesn't exist", e);
        }

    }

    @Override
    public Node next() throws HandlingException {

        if (buffReader == null) {
            throw new HandlingException("BuffReader isn't initialized");
        }
        NodeParser nodeParser = new NodeParser();
        String nodeData = nodeParser.find(buffReader);
        Node node = null;
        if (nodeData != null) {
            node = NodeParser.addNodeType(nodeData);
        } else {
            close();
        }
        return node;
    }

    @Override
    public List<Node> getAll() throws HandlingException {

        if (buffReader == null) {
            throw new HandlingException("BuffReader isn't initialized");
        }

        List<Node> nodeList = new ArrayList<>();

        Node node;

        while ((node = next()) != null) {
            nodeList.add(node);
        }

        return nodeList;
    }

    @Override
    public void close() throws HandlingException {
        try {
            if (buffReader != null) {
                buffReader.close();
            }
        } catch (IOException e) {
            throw new HandlingException("Can't close buffReader", e);
        } finally {
            buffReader = null;
        }
    }

}
