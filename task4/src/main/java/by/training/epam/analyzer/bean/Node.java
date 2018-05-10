package by.training.epam.analyzer.bean;

import java.util.Objects;

public class Node {

	String name;

	NodeType type;

	public Node() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public Node withName(String name) {
		this.name = name;
		return this;
	}

	public Node withType(NodeType type) {
		this.type = type;
		return this;
	}


	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Node node = (Node) obj;

		if (this.name == null) {
			if (node.name != null) {
				return false;
			}
		} else {
			if (!this.name.equals(node.name)) {
				return false;
			}
		}

		if (!this.type.equals(node.type)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, type);
	}

	public String toString() {

		return "Node: " + name + ": type = " + type + "";
	}

}
