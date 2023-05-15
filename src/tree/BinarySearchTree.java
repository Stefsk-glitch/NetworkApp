package tree;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree
{
    private TreeNode root;

    public BinarySearchTree()
    {
        root = null;
    }

    public void put(String key, String value)
    {
        root = put(root, key, value);
    }

    public TreeNode put(TreeNode node, String key, String value)
    {
        if (node == null)
        {
            return new TreeNode(key, value);
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0)
        {
            node.setLeft(put(node.getLeft(), key, value));
        }
        else if (cmp > 0)
        {
            node.setRight(put(node.getRight(), key, value));
        }
        else
        {
            node.setValue(value);
        }

        return node;
    }

    public String get(String key)
    {
        TreeNode node = get(root, key);

        if (node == null)
        {
            return null;
        }

        return node.getValue();
    }

    private TreeNode get(TreeNode node, String key)
    {
        if (node == null)
        {
            return null;
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0)
        {
            return get(node.getLeft(), key);
        }
        else if (cmp > 0)
        {
            return get(node.getRight(), key);
        }
        else
        {
            return node;
        }
    }

    public boolean containsKey(String key)
    {
        return get(key) != null;
    }

    public List<String> getAllKeys()
    {
        List<String> keys = new ArrayList<>();
        getAllKeysHelper(root, keys);
        return keys;
    }

    private void getAllKeysHelper(TreeNode node, List<String> keys)
    {
        if (node == null)
        {
            return;
        }

        getAllKeysHelper(node.getLeft(), keys);
        keys.add(node.getKey());
        getAllKeysHelper(node.getRight(), keys);
    }
}

