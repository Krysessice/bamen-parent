package com.bamenyouxi.invite_code_mode.test;

/**
 * MainTest
 * Created by 13477 on 2017/8/16.
 */
final class MainTest {

	public static void main(String[] args) {
		Node head = new Node<>("我来组成头部");
		Node tail = new Node<>(123);
		head.next = tail;

		Node current;
		for (current = head; current != null; ) {
			System.out.println("value: " + current.item);
			current = current.next;
		}
	}

}

class Node<E> {
	E item;
	Node<E> next;

	Node(E element) {
		this.item = element;
		this.next = null;
	}
}
