/**
 * A sudoku class that creates a board of any given sudoku size
 *
 * @author Micky Santiago-Zayas
 * @version August 9, 2022
 */

public class Sudoku {
    Node root;
    int size = 1;

    /***
     * Basic Sudoku constructor makes only a 3 by 3 cell grid
     */
    public Sudoku() {
        Node root = new Node();
        this.size = 0;
        this.root = root;
        new_grid(root.content);
    }

    /***
     * Sudoku constructor
     * @param size
     */
    public Sudoku(int size) {
        // TODO: Change sudoku type from given size
        Node root = new Node();
        this.size = size;
        this.root = root;
        // Size 0
        new_grid(root.content);

        // makes main sudoku grid with nine smaller grids
        if (size > 0) {
            // makes a size 1 sudoku grid
            new_grid(root);

            /*
             * Makes space for the grid cells for the numbers
             * for all the other grids besides the center
             */
            // make cells for the top grid
            new_grid(root.getTop());

            // make cells for the bottom grid
            new_grid(root.getBottom());

            // make cells for the left grid
            new_grid(root.getLeft());

            // make cells for the right grid
            new_grid(root.getRight());

            // make cells for the top right grid
            new_grid(root.getRight().getTop());

            // make cells for the bottom right grid
            new_grid(root.getRight().getBottom());

            // make cells for the top left grid
            new_grid(root.getLeft().getTop());

            // make cells for the bottom left grid
            new_grid(root.getLeft().getBottom());

            /*
             * Need to connect all the adjacent cells to adjacent grids
             */

            // Connect top and center grids
            add_connection_down((Node) root.getTop().getContent(), (Node) root.getContent());

            // Connect bottom and center grids
            add_connection_down((Node) root.getContent(), (Node) root.getBottom().getContent());

            // Connect left and center grids
            add_connection_side((Node) root.getLeft().getContent(), (Node) root.getContent());

            // Connect right and center grids
            add_connection_side((Node) root.getContent(), (Node) root.getRight().getContent());

            // Connect corner grids
            // Top right corner
            add_connection_side((Node) root.getTop().getContent(), (Node) root.getTop().getRight().getContent());
            add_connection_down((Node) root.getTop().getRight().getContent(), (Node) root.getRight().getContent());

            // Bottom right corner
            add_connection_side((Node) root.getBottom().getContent(), (Node) root.getBottom().getRight().getContent());
            add_connection_down((Node) root.getRight().getContent(), (Node) root.getBottom().getRight().getContent());

            // Bottom left corner
            add_connection_side((Node) root.getBottom().getLeft().getContent(), (Node) root.getBottom().getContent());
            add_connection_down((Node) root.getLeft().getContent(), (Node) root.getBottom().getLeft().getContent());

            // Top left corner
            add_connection_side((Node) root.getTop().getLeft().getContent(), (Node) root.getTop().getContent());
            add_connection_down((Node) root.getTop().getLeft().getContent(), (Node) root.getLeft().getContent());

            if (size == 2) {
                /*
                 * Makes the new root to be the left top corner to make
                 * the grid to have two sudoku grids connected by the left top corner
                 */
                this.root = root.getTop().getLeft();

                // Makes a whole 3 by 3 grid to simply connect to the old root
                Sudoku topLeft = new Sudoku(1);

                // Connect the old root to the new grid
                topLeft.root.getBottom().setRight(this.root);
                topLeft.root.getRight().setBottom(this.root);

                // Connect the new grid to the old root
                this.root.setLeft(topLeft.root.getBottom());
                this.root.setTop(topLeft.root.getRight());

                // Connect the new adjacent cells of the old root from the new grid
                add_connection_side((Node) topLeft.root.getBottom().getContent(), (Node) this.root.getContent());
                add_connection_down((Node) topLeft.root.getRight().getContent(), (Node) this.root.getContent());
            }
        }
    }

    /***
     * Generates a new grid with 9 spaces for all the digits
     * @param grid
     */
    private void new_grid(Object grid) {
        grid = new Node();

        // add top cell
        ((Node) grid).setTop(new Node());
        ((Node) grid).getTop().setBottom((Node) grid);

        // add bottom cell
        ((Node) grid).setBottom(new Node());
        ((Node) grid).getBottom().setTop((Node) grid);

        // add left cell
        ((Node) grid).setLeft(new Node());
        ((Node) grid).getLeft().setRight((Node) grid);

        // add right cell
        ((Node) grid).setRight(new Node());
        ((Node) grid).getRight().setLeft((Node) grid);

        // add all the corners
        add_corners((Node) grid);

        return;
    }

    /***
     * This function adds the four corners to a center node
     * @param center
     */
    private void add_corners(Node center) {
        Node top_right = new Node();
        Node top_left = new Node();
        Node bot_left = new Node();
        Node bot_right = new Node();

        // connect the grid center to the top right corner
        center.getTop().setRight(top_right);
        center.getRight().setTop(top_right);
        // connect the top right node to the grid center
        top_right.setLeft(center.getTop());
        top_right.setBottom(center.getRight());

        // connect the grid center to the top left corner
        center.getTop().setLeft(top_left);
        center.getLeft().setTop(top_left);
        // connect the top left node to the grid center
        top_left.setRight(center.getTop());
        top_left.setBottom(center.getLeft());

        // connect the grid center to the bottom left corner
        center.getBottom().setLeft(bot_left);
        center.getLeft().setBottom(bot_left);
        // connect the bottom left node to the grid center
        bot_left.setRight(center.getBottom());
        bot_left.setTop(center.getLeft());

        // connect the grid center to the bottom right corner
        center.getBottom().setRight(bot_right);
        center.getRight().setBottom(bot_right);
        // connect the bottom right node to the grid center
        bot_right.setLeft(center.getBottom());
        bot_right.setTop(center.getRight());
        return;
    }

    /***
     * This function connects the continuous left to right cells
     * @param left needs to be the root node of the cell grids for the left grid
     * @param right needs to be the root node of the cell grids for the right grid
     */
    private void add_connection_side(Node left, Node right) {
        // connects the right grid's left cell to the left grid's right cell
        left.getRight().setRight(right.getLeft());
        // connects the left grid's right cell to the right grid's left cell
        right.getLeft().setLeft(left.getRight());

        // similar actions to the respective corners
        // top corners
        left.getRight().getTop().setRight(right.getLeft().getTop());
        right.getLeft().getTop().setLeft(left.getRight().getTop());

        // bottom corners
        left.getRight().getBottom().setRight(right.getLeft().getBottom());
        right.getLeft().getBottom().setLeft(left.getRight().getBottom());
        return;
    }

    /***
     * This function connects the continuous top to bottom cells
     * @param top needs to be the root node of the cell grids for the top grid
     * @param bottom needs to be the root node of the cell grids for the bottom grid
     */
    private void add_connection_down(Node top, Node bottom) {
        // connects the bottom grid's top cell to the top grid's bottom cell
        bottom.getTop().setTop(top.getBottom());
        // connects the top grid's bottom cell to the bottom grid's top cell
        top.getBottom().setBottom(bottom.getTop());

        // similar actions to the respective corners
        // right corners
        bottom.getRight().getTop().setTop(top.getRight().getBottom());
        top.getRight().getBottom().setBottom(bottom.getRight().getTop());

        // left corners
        bottom.getLeft().getTop().setTop(top.getLeft().getBottom());
        top.getLeft().getBottom().setBottom(bottom.getLeft().getTop());
        return;
    }

    private Node set_left_most() {
        Node placeholder = root;
        while (placeholder.getTop() != null || placeholder.getLeft() != null) {
            if (placeholder.getTop() != null) {
                placeholder = placeholder.getTop();
            }
            if (placeholder.getLeft() != null) {
                placeholder = placeholder.getLeft();
            }
        }
        return placeholder;
    }

    public boolean insert(int row, int col, int value) {
        // TODO
        int grid_row_num = row / 3;
        int cel_row_num = row % 3;

        int grid_col_num = col / 3;
        int cel_col_num = col % 3;

        Node ph = set_left_most();

        for (int i = 0; i < grid_col_num)

        return true;
    }

    public void print(String output_file) {
        // TODO
        return;
    }

    // Returns true if it is unique in the position
    public boolean unique_test(int row, int pos, int value) {
        // TODO
        return true;
    }
}
