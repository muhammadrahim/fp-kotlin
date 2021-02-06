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

        fun <A, B> map(tree: Tree<A>, f: (A) -> B): Tree<B> = when (tree) {
            is Leaf -> Leaf(f(tree.value))
            is Branch -> Branch(map(tree.left, f), map(tree.right, f))
        }

        fun <A, B> fold(tree: Tree<A>, f: (A) -> B, f2: (B, B) -> B): B =
                when (tree) {
                    is Leaf -> f(tree.value)
                    is Branch -> f2(fold(tree.left, f, f2), fold(tree.right, f, f2))
                }

        fun <A> size2(tree: Tree<A>): Int = fold(tree, { 1 }, { a, b -> a + b + 1 })

        fun maximum2(tree: Tree<Int>): Int = fold(tree, { it }, { a, b -> maxOf(a, b) })

        fun depth2(tree: Tree<Int>): Int = fold(tree, { 0 }, { a, b -> 1 + maxOf(a, b) })

        fun <A, B> map2(tree: Tree<A>, f: (A) -> B): Tree<B> =
                fold(tree, { Leaf(f(it)) }, { a: Tree<B>, b: Tree<B> -> Branch(a, b) })
    }
}

data class Leaf<A>(val value: A) : Tree<A>()

data class Branch<A>(
        val left: Tree<A>,
        val right: Tree<A>
) : Tree<A>()

