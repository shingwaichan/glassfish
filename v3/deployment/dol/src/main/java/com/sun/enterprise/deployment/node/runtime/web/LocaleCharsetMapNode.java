/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.enterprise.deployment.node.runtime.web;

import com.sun.enterprise.deployment.node.XMLElement;
import com.sun.enterprise.deployment.runtime.RuntimeDescriptor;
import com.sun.enterprise.deployment.runtime.web.LocaleCharsetMap;
import com.sun.enterprise.deployment.xml.RuntimeTagNames;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
/**
* node for web property tag
*
* @author Jerome Dochez
*/
public class LocaleCharsetMapNode extends WebRuntimeNode {
    
    /**
     * receives notification of the value for a particular tag
     * 
     * @param element the xml element
     * @param value it's associated value
     */
    public void setElementValue(XMLElement element, String value) {
	RuntimeDescriptor descriptor = getRuntimeDescriptor();
	if (descriptor==null) {
	    throw new RuntimeException("Trying to set name or value on null property");
	}
	if (element.getQName().equals(RuntimeTagNames.LOCALE)) {
	    descriptor.setAttributeValue(LocaleCharsetMap.LOCALE, value);
	} else 
	if (element.getQName().equals(RuntimeTagNames.AGENT)) {
	    descriptor.setAttributeValue(LocaleCharsetMap.AGENT, value);
	}
	if (element.getQName().equals(RuntimeTagNames.CHARSET)) {
	    descriptor.setAttributeValue(LocaleCharsetMap.CHARSET, value);
	} 
    }    
    
    /**
     * write the descriptor class to a DOM tree and return it
     *
     * @param parent node for the DOM tree
     * @param node name for the descriptor
     * @param the descriptor to write
     * @return the DOM tree top node
     */    
    public Node writeDescriptor(Node parent, String nodeName, LocaleCharsetMap descriptor) {
	
	Element locale = (Element) super.writeDescriptor(parent, nodeName, descriptor);
	
	// description?
	appendTextChild(locale, RuntimeTagNames.DESCRIPTION, descriptor.getDescription());
	
	// locale, agent, charset attributes
	setAttribute(locale, RuntimeTagNames.LOCALE, (String) descriptor.getAttributeValue(LocaleCharsetMap.LOCALE));
	setAttribute(locale, RuntimeTagNames.AGENT, (String) descriptor.getAttributeValue(LocaleCharsetMap.AGENT));
	setAttribute(locale, RuntimeTagNames.CHARSET, (String) descriptor.getAttributeValue(LocaleCharsetMap.CHARSET));
	
	return locale;
    }
}
