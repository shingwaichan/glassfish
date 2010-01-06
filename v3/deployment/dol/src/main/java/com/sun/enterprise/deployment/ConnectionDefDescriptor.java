/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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

package com.sun.enterprise.deployment;

import java.util.Set;

/**
 * Deployment Information for connection-definition
 *
 * <!ELEMENT connection-definition (
 * managedconnectionfactory-class, connectionfactory-intf,
 * connection-intf, config-property*, connectionfactory-impl, connection-impl
 * )>
 *
 * @author Sheetal Vartak
 */
public class ConnectionDefDescriptor extends Descriptor 
{
    
    private String managedConnectionFactoryImpl = "";
    private Set configProperties;
    private String connectionIntf = "";
    private String connectionImpl = "";
    private String connectionfactoryImpl = "";
    private String connectionfactoryIntf = "";

    public ConnectionDefDescriptor () 
    {
        configProperties    = new OrderedSet();
    }

    /** 
     * Gets the value of ManagedconnectionFactoryImpl
     */
    public String getManagedConnectionFactoryImpl() 
    {
        return managedConnectionFactoryImpl;
    }
    
    /** 
     * Sets the value of ManagedconnectionFactoryImpl
     */
    public void setManagedConnectionFactoryImpl(String managedConnectionFactoryImpl) 
    {
        this.managedConnectionFactoryImpl = managedConnectionFactoryImpl;
    }

    /** 
     * Set of ConnectorConfigProperty
     */
    public Set getConfigProperties() 
    {
        return configProperties;
    }
      
    /** 
     * Add a configProperty to the set
     */
    public void addConfigProperty(ConnectorConfigProperty configProperty)
    {
	configProperties.add(configProperty);
    }

    /** 
     * Add a configProperty to the set
     */ 
    public void removeConfigProperty(ConnectorConfigProperty configProperty)
    {
	configProperties.remove(configProperty);
    }
    
    /**
     * Get connection factory impl
     */
    public String getConnectionFactoryImpl()
    {
        return connectionfactoryImpl;
    }

    /** 
     * set connection factory impl 
     */
    public void setConnectionFactoryImpl(String cf) 
    {
	connectionfactoryImpl = cf;
    }

    /**
     * Get connection factory intf
     */
    public String getConnectionFactoryIntf()
    {
        return connectionfactoryIntf;
    }

    /** 
     * set connection factory intf
     */
    public void setConnectionFactoryIntf(String cf) 
    {
	connectionfactoryIntf = cf;
    }

    /**
     * Get connection intf
     */
    public String getConnectionIntf()
    {
        return connectionIntf;
    }

    /** 
     * set connection intf
     */
    public void setConnectionIntf(String con) 
    {
	connectionIntf = con;
    }

    /**
     * Get connection impl
     */
    public String getConnectionImpl()
    {
        return connectionImpl;
    }

    /** 
     * set connection intf
     */
    public void setConnectionImpl(String con) 
    {
	connectionImpl = con;
    }

 }
