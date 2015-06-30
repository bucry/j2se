/*
 * $Header: /cvsroot/nwn-j3d/nwn/src/com/sun/j3d/demos/utils/scenegraph/traverser/TreeScan.java,v 1.1 2002/04/19 13:50:14 abies Exp $
 *
 * Copyright (c) 2000-2002 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *    -Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *
 *    -Redistribution in binary form must reproduce the above copyright notice, 
 *     this list of conditions and the following disclaimer in the  
 *     documentation and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed,licensed or intended for use in 
 * the design, construction, operation or maintenance of any nuclear facility.
 */

package com.sun.j3d.demos.utils.scenegraph.traverser;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.BitSet;

import javax.media.j3d.Group;
import javax.media.j3d.SharedGroup;
import javax.media.j3d.Node;
import javax.media.j3d.Link;
import javax.media.j3d.Switch;

public class TreeScan extends Object {
  
  private static HashSet visitedSharedGroups = null;

                                  
  /** Traverse the SceneGraph starting at node treeRoot. Every time a node of
   * class nodeClass is found call processNode method in processor.
   * @param treeRoot The root of the SceneGraph to search
   * @param nodeClass The class of the node(s) to search for
   * @param processor The class containing the processNode method which will be
   * called every time the correct nodeClass is found in the Scene Graph.
   * @param onlyEnabledSwitchChildren when true only recurse into Switch
   * children which are enabled
   * @param sharedGroupsOnce when true only process SharedGroups once,
   * regardless how many Links refer to them
   * @throws CapabilityNotSetException If the node is live or compiled and the scene graph
   * contains groups without ALLOW_CHILDREN_READ capability
   */
  public static void findNode(javax.media.j3d.Node treeRoot,
          Class nodeClass,
          ProcessNodeInterface processor,
          boolean onlyEnabledSwitchChildren,
          boolean sharedGroupsOnce) 
          throws javax.media.j3d.CapabilityNotSetException 
  {

    Class[] nodeClasses = new Class[]{ nodeClass };
    
    findNode( treeRoot, nodeClasses, processor, 
                    onlyEnabledSwitchChildren,
                    sharedGroupsOnce );
    
  }
  
  /** Traverse the SceneGraph starting at node treeRoot. Every time a node of
   * class nodeClass is found call processNode method in processor.
   * @param treeRoot The root of the SceneGraph to search
   * @param nodeClasses The list of classes of the node(s) to search for
   * @param processor The class containing the processNode method which will be
   * called every time the correct nodeClass is found in the Scene Graph.
   * @param onlyEnabledSwitchChildren when true only recurse into Switch
   * children which are enabled
   * @param sharedGroupsOnce when true only process SharedGroups once,
   * regardless how many Links refer to them
   * @throws CapabilityNotSetException If the node is live or compiled and the scene graph
   * contains groups without ALLOW_CHILDREN_READ capability
   */
  public static void findNode( javax.media.j3d.Node treeRoot, 
                        Class[] nodeClasses,
                        ProcessNodeInterface processor,
                        boolean onlyEnabledSwitchChildren,
                        boolean sharedGroupsOnce ) throws 
                                  javax.media.j3d.CapabilityNotSetException {
    if (sharedGroupsOnce)
      if (visitedSharedGroups==null)                              
        visitedSharedGroups = new HashSet();
      
    actualFindNode( treeRoot, nodeClasses, processor, 
                    onlyEnabledSwitchChildren,
                    sharedGroupsOnce );
    
    if (sharedGroupsOnce)
      visitedSharedGroups.clear();
  }
  
  /**
    * Conveniance method to return a Class given the full Class name
    * without throwing ClassNotFoundException
    * 
    * If the class is not available an error message is displayed and a 
    * runtime exception thrown
    */
  public static Class getClass( String str ) {
    try {
      return Class.forName( str );
    } catch( ClassNotFoundException e ) {
      e.printStackTrace();
      throw new RuntimeException( "BAD CLASS "+str );
    }
  }
                                  
  private static void actualFindNode( javax.media.j3d.Node treeRoot, 
                        Class[] nodeClasses,
                        ProcessNodeInterface processor,
                        boolean onlyEnabledSwitchChildren,
                        boolean sharedGroupsOnce ) throws 
                                  javax.media.j3d.CapabilityNotSetException {
                                    
    //System.out.println(treeRoot + " " + treeRoot.getUserData());
    //if (onlyEnabledSwitchChildren)
    //  throw new RuntimeException( "OnlyEnabledSwitchChildren not implemented");    
                                
    if (treeRoot == null)
      return;
    
    if (treeRoot instanceof SharedGroup && sharedGroupsOnce) {
      if (visitedSharedGroups.contains( treeRoot ))
        return;
      else
        visitedSharedGroups.add( treeRoot );
    }
    
    //System.out.print( treeRoot.getClass().getName()+"  ");
    //System.out.print( nodeClasses[0].getName()+"  ");
    //System.out.println( nodeClasses[0].isAssignableFrom( treeRoot.getClass() ));
    for(int i=0; i<nodeClasses.length; i++)
      if (nodeClasses[i].isAssignableFrom( treeRoot.getClass() ))
        processor.processNode( treeRoot );
    
    if (onlyEnabledSwitchChildren && treeRoot instanceof Switch ) {
        int whichChild = ((Switch)treeRoot).getWhichChild();
        
        if (whichChild==Switch.CHILD_ALL) {
              Enumeration e = ((Group)treeRoot).getAllChildren();
              while( e.hasMoreElements() )
                actualFindNode( (Node)e.nextElement(), nodeClasses, processor, 
                              onlyEnabledSwitchChildren, sharedGroupsOnce  );
        } else if (whichChild==Switch.CHILD_MASK) {
            BitSet set = ((Switch)treeRoot).getChildMask();
            for(int s=0; s<set.length(); s++) {
                if (set.get(s))
                    actualFindNode( ((Switch)treeRoot).getChild(s), nodeClasses,
                                    processor, onlyEnabledSwitchChildren, sharedGroupsOnce );
            }
        } else if (whichChild==Switch.CHILD_NONE) {
            // DO nothing
        } else
            actualFindNode( ((Switch)treeRoot).currentChild(), nodeClasses,
                            processor, onlyEnabledSwitchChildren, sharedGroupsOnce );
    } else if (treeRoot instanceof Group) {
      Enumeration e = ((Group)treeRoot).getAllChildren();
      while( e.hasMoreElements() )
        actualFindNode( (Node)e.nextElement(), nodeClasses, processor, 
                      onlyEnabledSwitchChildren, sharedGroupsOnce  );
    } else if (treeRoot instanceof Link) {
        actualFindNode( ((Link)treeRoot).getSharedGroup(), nodeClasses, processor, 
                      onlyEnabledSwitchChildren, sharedGroupsOnce );
    }
  }
}
