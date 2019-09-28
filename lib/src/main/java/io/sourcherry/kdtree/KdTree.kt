package io.sourcherry.kdtree

class Node<T>(
    var data: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)


class KdTree<I>(
    private vararg val dinemtions: Comparator<I>
) {

    var root: Node<I>? = null

    fun add(item: I) {
        if (root == null) root = Node(item)
        addTraversal(item)
    }

    fun findAll() {

    }

    private fun addTraversal(item: I) {
        check(dinemtions.isNotEmpty()) { "No dimension comparators provided" }
    }
}