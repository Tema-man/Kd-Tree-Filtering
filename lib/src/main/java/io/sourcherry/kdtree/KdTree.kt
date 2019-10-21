package io.sourcherry.kdtree

import java.util.*


class Node<T>(
    var data: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)

interface Bound<I> {
    val dimension: Comparator<I>
    fun contains(item: I): Boolean
    fun intersects(bound: Bound<I>): Boolean
}

/*
https://github.com/mgruben/Kd-Trees/blob/master/KdTree.java
 */
class KdTree<I>(
    private val dimensions: List<Comparator<I>>,
    items: Collection<I> = emptyList()
) {

    private val dimensMap: Map<Class<*>, Comparator<I>>
    private var size = 0L

    init {
        items.forEach { add(it) }
        dimensMap = dimensions.map { it::class.java to it }.toMap()
    }

    private var root: Node<I>? = null

    fun add(item: I) {
        check(dimensions.isNotEmpty()) { "No dimension comparators provided" }
        root = insert(root, item, 0)
    }


    /**
     * Return range of items that contains in specified
     *
     * TODO: Add bounds ordering
     *
     */
    fun range(bounds: List<Bound<I>>): Collection<I> {
        val items = Stack<I>()
        if (root == null) return items

        val nodes = Stack<Node<I>>()
        nodes.push(root)
        while (!nodes.isEmpty()) {

            // Examine the next Node
            val node = nodes.pop()

            // Add contained points to our points stack
            if (bounds.any { it.contains(node.data) }) items.push(node.data)

            //Add Nodes containing promising rectangles to our nodes stack.
            if (node.left != null && rect.intersects(node.lb.rect)) {
                nodes.push(node.left)
            }

            if (node.right != null && rect.intersects(node.rt.rect)) {
                nodes.push(node.right)
            }
        }
        return items
    }

    private fun insert(node: Node<I>?, item: I, dimension: Int): Node<I> {
        if (node == null) {
            size++
            return Node(item)
        } else {
            val cmp = dimensions[dimension].compare(node.data, item)

            if (cmp < 0) {
                node.left = insert(node.left, item, dimension.next())
            }

            if (cmp > 0) {
                node.right = insert(node.right, item, dimension.next())
            }

            if (cmp == 0) {
                if (node.data != item) {
                    node.right = insert(node.right, item, dimension.next())
                }
            }
            return node
        }
    }

    private fun Int.next(): Int = (this + 1) % dimensions.size
}
