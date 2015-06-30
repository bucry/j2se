package net.sf.nwn.loader;


import java.util.*;

import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.AxisAngle4f;


public abstract class Node
{
    protected Node supernode;
    private String name;
    protected LinkedHashMap children = new LinkedHashMap();

    public Node(Node parent)
    {
        supernode = parent;
    }

    public void setSupernode(Node parent)
    {
        supernode = parent;
    }

    /**
     * Gets the name.
     * @return Returns a String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name)
    {
        this.name = name.toLowerCase();
    }

    public String getType()
    {
        return "unknown";
    }

    public void addChild(Node node)
    {
        children.put(node.getName(), node);
    }

    public boolean containsChild(String name)
    {
        return findChild(name) != null;
    }

    public Node findChild(String name)
    {
        Node n = (Node) children.get(name);

        if (n != null)
            return n;

        Iterator it = children.values().iterator();

        while (it.hasNext())
        {
            Node child = (Node) it.next();

            n = child.findChild(name);
            if (n != null)
                return n;
        }
        return null;
    }

    public int getDepth()
    {
        int depth = 0;

        for (Node n = supernode; n != null; n = n.supernode)
            depth++;
        return depth;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer("");
        int depth = getDepth();

        while (depth-- > 0)
            sb.append("    ");
        sb.append(getName()).append('(').append(getType());
        sb.append(')');
        Iterator it = children.values().iterator();

        while (it.hasNext())
        {
            sb.append("\n").append(it.next());
        }
        return sb.toString();
    }

    public void dumpSingle(StringBuffer sb)
    {
        sb.append("node ").append(getType()).append(" ").append(getName()).append("\n");
        if (supernode == null)
        {
            sb.append("parent NULL\n");
        }
        else
        {
            sb.append("parent ").append(supernode.getName()).append("\n");
        }
    }

    public void dump(StringBuffer sb)
    {
        dumpSingle(sb);
        sb.append("endnode\n");
        Iterator it = children.values().iterator();

        while (it.hasNext())
        {
            Node n = (Node) it.next();

            n.dump(sb);
        }
    }

    protected void dump(StringBuffer sb, Tuple2f t)
    {
        sb.append(t.x).append(' ').append(t.y);
    }

    protected void dump(StringBuffer sb, Tuple3f t)
    {
        sb.append(t.x).append(' ').append(t.y).append(' ').append(t.z);
    }

    protected void dump(StringBuffer sb, AxisAngle4f t)
    {
        sb.append(t.x).append(' ').append(t.y).append(' ').append(t.z).append(' ').append(t.angle);
    }

}
