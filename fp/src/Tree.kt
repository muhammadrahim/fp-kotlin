sealed class Tree<out A> {
    companion object {
        fun <A> size(tree: Tree<A>): Int = when (tree) {
            is Leaf -> 1
            is Branch -> 1 + size(tree.left) + size(tree.right)
        }

        fun maximum(tree: Tree<Int>): Int = when (tree) {
            is Leaf -> tree.value
            is Branch -> maxOf(maximum(tree.left), maximum(tree.right))
        }

        fun depth(tree: Tree<Int>): Int = when (tree) {
            is Leaf -> 0
            is Branch -> 1 + maxOf(depth(tree.left), depth(tree.right))
        }

        fun <A,B> map(tree: Tree<A>, f: (A) -> B): Tree<B> {
            return when (tree) {
                is Leaf -> Leaf(f(tree.value))
                is Branch -> Branch(map(tree.left, f), map(tree.right, f))
            }
        }
    }
}

data class Leaf<A>(val value: A): Tree<A>()

data class Branch<A>(
        val left: Tree<A>,
        val right: Tree<A>
) : Tree<A>()

