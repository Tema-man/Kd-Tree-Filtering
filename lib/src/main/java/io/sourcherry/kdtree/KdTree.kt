package io.sourcherry.kdtree

 class Node<T>(
    var data: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)

class Bound<B : Comparable<B>>(
    val dimension: Int,
    val range: ClosedRange<B>
)


/*
https://github.com/mgruben/Kd-Trees/blob/master/KdTree.java
 */
class KdTree<I>(
    private val dimensions: List<Comparator<I>>
) {

    var root: Node<I>? = null

    fun add(item: I) {
        check(dimensions.isNotEmpty()) { "No dimension comparators provided" }
        root = insert(root, item, 0)
    }

    fun findAll(bounds: List<Bound<*>>): Collection<I> {


        return emptyList()
    }

    private fun insert(node: Node<I>?, item: I, dimension: Int): Node<I> {
        if (node == null) {
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