import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

public class TreeExample implements Runnable {

    private DefaultMutableTreeNode root;//перебирає вузли дерева

    private DefaultTreeModel treeModel;//

    private JTree tree;//саме дерево

    @Override
    public void run() {
        JFrame frame = new JFrame("File Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File fileRoot = new File("C:/");
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
        tree.setShowsRootHandles(true);
        JScrollPane scrollPane = new JScrollPane(tree);

        frame.add(scrollPane);
        frame.setLocationByPlatform(true);
        frame.setSize(640, 480);
        frame.setVisible(true);

        CreateChildNodes ccn =
                new CreateChildNodes(fileRoot, root);
        new Thread(ccn).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new TreeExample());
    }

}