package by.training.epam.analyzer.service.impl;

import by.training.epam.analyzer.bean.Node;
import by.training.epam.analyzer.bean.NodeType;
import by.training.epam.analyzer.exception.HandlingException;
import by.training.epam.analyzer.service.util.CharConstants;

import java.io.BufferedReader;
import java.io.IOException;


public class Analyser {

    private static final String MESSAGE_FOR_BUFREADER = "BuffReader isn't initialized";
    private static final String MESSAGE_FOR_FILE = "Can't read from file";
    private static String dataString;

    public String findNodes(BufferedReader buffReader) throws HandlingException {
        StringBuilder result = new StringBuilder();

        if (dataString == null) {
            dataString = getData(buffReader);
        }

        while (dataString != null) {
            char[] buffer = dataString.toCharArray();
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] == CharConstants.OPEN_BRACKET) {
                    result.append(buffer[i]);
                    i++;
                    while (dataString != null) {
                        if (i < buffer.length) {
                            result.append(buffer[i]);
                            if (buffer[i] == CharConstants.CLOSE_BRACKET) {
                                dataString = dataString.substring(i + 1, dataString.length());
                                return result.toString();
                            }
                            i++;
                        } else {
                            dataString = getData(buffReader);
                            buffer = dataString.toCharArray();
                            i = 0;
                        }
                    }
                } else if (Character.isAlphabetic(buffer[i])) {
                    result.append(buffer[i]);
                    i++;
                    while (dataString != null) {
                        if (i < buffer.length) {
                            if (buffer[i] == CharConstants.OPEN_BRACKET) {
                                dataString = dataString.substring(i, dataString.length());
                                return result.toString();
                            }
                            result.append(buffer[i]);
                            i++;
                        } else {
                            dataString = getData(buffReader);
                            buffer = dataString.toCharArray();
                            i = 0;
                        }
                    }
                }
            }
            dataString = getData(buffReader);
        }

        return null;
    }

    public static Node analyseNodeData(String data) {

        Node node = new Node();
        node.setName(data);
        char[] charArr = data.toCharArray();

        if (charArr[0] == CharConstants.OPEN_BRACKET) {

            if (charArr[1] == CharConstants.SLASH) {
                node.setType(NodeType.CLOSE_TAG);
            } else if (charArr[1] == CharConstants.QUESTION_MARK) {
                node.setType(NodeType.VERSION);
            } else if (charArr[charArr.length - 2] == CharConstants.SLASH) {

                node.setType(NodeType.EMPTY_ELEMENT_TAG);
            } else {

                node.setType(NodeType.OPEN_TAG);
            }

        } else {
            node.setType(NodeType.TEXT);
        }

        return node;
    }


    private String getData(BufferedReader buffReader) throws HandlingException {


        if (buffReader == null) {
            throw new HandlingException(MESSAGE_FOR_BUFREADER);
        }
        String line;

        try {

            line = buffReader.readLine();

        } catch (IOException e) {
            throw new HandlingException(MESSAGE_FOR_FILE, e);
        }


        return line;
    }


}
