import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class CreateChildNodes implements Runnable {

    private DefaultMutableTreeNode root;

    private File fileRoot;

    /**
     * повертаємо створені дочірні вузли
     * @param fileRoot
     * @param root
     */
    public CreateChildNodes(File fileRoot,
                            DefaultMutableTreeNode root) {
        this.fileRoot = fileRoot;
        this.root = root;
    }

    @Override
    /**
     *
     */
    public void run() {
        createChildren(fileRoot, root);
    }

    /**
     *створюємо дочірні вузли
     * @param fileRoot
     * @param node
     */
    private void createChildren(File fileRoot,
                                DefaultMutableTreeNode node) {
        File[] files = fileRoot.listFiles();
        if (files == null) return;

        for (File file : files) {
            DefaultMutableTreeNode childNode =
                    new DefaultMutableTreeNode(new FileNode(file));
            node.add(childNode);
            if (file.isDirectory()) {
                createChildren(file, childNode);
            }
        }
    }

}
