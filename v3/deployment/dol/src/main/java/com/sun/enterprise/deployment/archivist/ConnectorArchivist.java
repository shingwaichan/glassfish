/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
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

package com.sun.enterprise.deployment.archivist;

import com.sun.enterprise.deployment.ConnectorDescriptor;
import com.sun.enterprise.deployment.annotation.introspection.ResourceAdapterAnnotationScanner;
import com.sun.enterprise.deployment.util.XModuleType;
import com.sun.enterprise.deployment.util.AnnotationDetector;
import com.sun.enterprise.deployment.io.ConnectorDeploymentDescriptorFile;
import com.sun.enterprise.deployment.io.DeploymentDescriptorFile;
import com.sun.enterprise.deployment.io.runtime.ConnectorRuntimeDDFile;
import org.glassfish.api.deployment.archive.ReadableArchive;
import org.glassfish.deployment.common.DeploymentUtils;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.PerLookup;

import java.io.IOException;

/**
 * This class is responsible for handling J2EE Connector archive files.
 *
 * @author Sheetal Vartak
 * @version  
 */
@Service
@Scoped(PerLookup.class)
public class ConnectorArchivist extends Archivist<ConnectorDescriptor> {
    
    /** 
     * The DeploymentDescriptorFile handlers we are delegating for XML i/o
     */
    DeploymentDescriptorFile standardDD = new ConnectorDeploymentDescriptorFile(); 

    /**
     * @return the  module type handled by this archivist 
     * as defined in the application DTD
     *
     */
    @Override
    public XModuleType getModuleType() {
        return XModuleType.RAR;
    }        
          

    
    /**
     * @return the DeploymentDescriptorFile responsible for handling
     * standard deployment descriptor
     */
    @Override
    public DeploymentDescriptorFile getStandardDDFile() {
        return standardDD;
    }
    
    /**
     * @return if exists the DeploymentDescriptorFile responsible for
     * handling the configuration deployment descriptors
     */
    @Override
    public DeploymentDescriptorFile getConfigurationDDFile() {
        return new ConnectorRuntimeDDFile();
    }   
    

    /**
     * @return a default BundleDescriptor for this archivist
     */
    @Override
    public ConnectorDescriptor getDefaultBundleDescriptor() {
        return new ConnectorDescriptor();
    }

    /**
     * @return true if the archivist is handling the provided archive
     */
    @Override
    protected boolean postHandles(ReadableArchive abstractArchive)
            throws IOException {
        AnnotationDetector detector =
                    new AnnotationDetector(new ResourceAdapterAnnotationScanner());
        return detector.hasAnnotationInArchive(abstractArchive);

    }

    @Override    
    protected String getArchiveExtension() {
        return CONNECTOR_EXTENSION;
    }
}
