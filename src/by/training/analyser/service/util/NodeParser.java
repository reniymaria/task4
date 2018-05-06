package by.training.analyser.service.util;

import by.training.analyser.bean.Node;
import by.training.analyser.bean.NodeType;
import by.training.analyser.exception.HandlingException;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeParser {
    private static final String EXP_OPEN_TAG = "<[a-zA-Z]+\\s?";
    private static final String EXP_ATTRIBUTES = "\\b([a-zA-Z]+=[\"|']((\\w)+|\\s{1})[\"|'])";
    private String dataString;


    public String find(BufferedReader buffReader) throws HandlingException {
        StringBuilder result = new StringBuilder();
        dataString = new String();

        if (dataString.isEmpty()) {
            dataString = getData(buffReader);
        }

        while (dataString != null) {

            char[] buffer = dataString.toCharArray();

            for (int i = 0; i < buffer.length; i++) {

                if (buffer[i] == CharConstants.OPEN_BRACKET) {
                    if (buffer[i + 1] != CharConstants.QUESTION_MARK) {

                        result.append(buffer[i]);

                        i = i + 1;

                        while (dataString != null) {
                            if (i < buffer.length) {

                                result.append(buffer[i]);

                                if (buffer[i] == CharConstants.CLOSE_BRACKET) {
                                    int endPos = i + 1;
                                    dataString = dataString.substring(endPos, dataString.length());
                                    return result.toString();
                                }
                                i = i + 1;
                            } else {
                                dataString = getData(buffReader);
                                buffer = dataString.toCharArray();
                                i = 0;
                            }
                        }
                    } else {
                        break;
                    }
                } else {

                    if (Character.isAlphabetic(buffer[i])) {

                        result.append(buffer[i]);

                        i = i + 1;

                        while (dataString != null) {
                            if (i < buffer.length) {

                                if (buffer[i] == CharConstants.OPEN_BRACKET) {
                                    dataString = dataString.substring(i, dataString.length());
                                    return result.toString();
                                }
                                result.append(buffer[i]);
                                i = i + 1;
                            } else {
                                dataString = getData(buffReader);
                                buffer = dataString.toCharArray();
                                i = 0;
                            }
                        }
                    }
                }
            }
            dataString = getData(buffReader);
        }
        return null;
    }

    public static Node addNodeType(String data) {

        Node node;

        char[] charArr = data.toCharArray();

        if (charArr[0] == CharConstants.OPEN_BRACKET) {

            if (charArr[1] == CharConstants.SLASH) {
                node = createNode(data, NodeType.CLOSE_TAG);
            } else {

                if (charArr[charArr.length - 2] == CharConstants.SLASH) {
                    node = createNode(data, NodeType.EMPTY_ELEMENT_TAG);
                } else
                    node = createNode(data, NodeType.OPEN_TAG);
            }

        } else {
            node = createNode(data, NodeType.TEXT);
        }

        return node;
    }

    private static Node createNode(String data, NodeType type) {

        Node node = new Node();
        node.setType(type);

        if (type == NodeType.OPEN_TAG) {

            Pattern pattern = Pattern.compile(EXP_OPEN_TAG);
            Matcher matcher = pattern.matcher(data);

            matcher.find();
            String openTagname = matcher.group();
            openTagname = openTagname.trim();

            node.setName(openTagname + CharConstants.CLOSE_BRACKET);

            String content = "";
            pattern = Pattern.compile(EXP_ATTRIBUTES);
            matcher = pattern.matcher(data);

            while (matcher.find()) {
                content = content + matcher.group() + " ";
            }
            if (content.length() > 0) {
                content = content.trim();
                node.setContent(content);
            }
        } else {
            node.setName(data);
        }

        return node;
    }

    private static String getData(BufferedReader buffReader) throws HandlingException {
        if (buffReader == null) {
            throw new HandlingException("BuffReader isn't initialized");
        }
        String line;

        try {
            line = buffReader.readLine();

        } catch (IOException e) {
            throw new HandlingException("Can't read from file", e);
        }

        return line;
    }


}
