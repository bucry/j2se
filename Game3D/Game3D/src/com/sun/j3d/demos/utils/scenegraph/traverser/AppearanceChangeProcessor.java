/*
 * $Header: /cvsroot/nwn-j3d/nwn/src/com/sun/j3d/demos/utils/scenegraph/traverser/AppearanceChangeProcessor.java,v 1.1 2002/04/19 13:50:14 abies Exp $
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

import javax.media.j3d.Shape3D;
import javax.media.j3d.Appearance;

/** 
 * Abstract class for changing the parameters of Appearance Node Components.
 * Subclasses implement changeAppearance to make the actual updates
 *
 * @author  paulby
 * @version 
 */
public abstract class AppearanceChangeProcessor implements ProcessNodeInterface {

  /**
    * Called by TreeScan. node must be an instance of Shape3D
    */
  public void processNode(javax.media.j3d.Node node) {
    Appearance app = ((Shape3D)node).getAppearance();
    changeAppearance( (Shape3D)node, app );
  }
  
  public abstract void changeAppearance( javax.media.j3d.Shape3D shape,
                                          javax.media.j3d.Appearance app );
}
