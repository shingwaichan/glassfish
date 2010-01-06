/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2006-2010 Sun Microsystems, Inc. All rights reserved.
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

package org.glassfish.deployment.common;

import com.sun.logging.LogDomains;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Singleton;

/**
 * This class keeps track of the set of jars that need to be downloaded as a
 * response to deploy --retrieve or get-client-stubs request for an appclient
 * module or EAR containing at least one appclient module.
 */
@Service
@Scoped(Singleton.class)
public class DownloadableArtifacts {

    private static Logger logger = LogDomains.getLogger(DownloadableArtifacts.class, LogDomains.ACC_LOGGER);

    private Map<String, Set<FullAndPartURIs>> artifactsMap = new HashMap<String, Set<FullAndPartURIs>>();

    public synchronized void addArtifact(String moduleName, URI full, URI part) {
        Set<FullAndPartURIs> uriPairs = artifactsMap.get(moduleName);
        if (uriPairs == null) {
            uriPairs = new HashSet<FullAndPartURIs>();
            artifactsMap.put(moduleName, uriPairs);
        }
        FullAndPartURIs fullAndPart = new FullAndPartURIs(full, part);
        uriPairs.add(fullAndPart);
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Added downloadable artifact: " + fullAndPart);
        }
    }

    public synchronized void addArtifact(String moduleName, URI full, String part) {
        addArtifact(moduleName, full, URI.create(part));
    }

    public synchronized void addArtifacts(String moduleName, Collection<FullAndPartURIs> urisCollection) {
        Set<FullAndPartURIs> uriPairs = artifactsMap.get(moduleName);
        if (uriPairs == null) {
            uriPairs = new HashSet<FullAndPartURIs>();
            artifactsMap.put(moduleName, uriPairs);
        }
        uriPairs.addAll(urisCollection);
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Added downloadable artifacts: " + urisCollection);
        }
    }

    public synchronized Set<FullAndPartURIs> getArtifacts(String moduleName) {
        /*
         * A module with submodules should match both its own artifacts and
         * its submodule artifacts.  This could be made faster with a two-level
         * map, but for now this will work.
         *
         * Match any entry for which the key matches the moduleName or for which
         * the key starts with the module name and the next character is a slash.
         */
        Set<FullAndPartURIs> uriPairs = new HashSet<FullAndPartURIs>();
        for (Map.Entry<String,Set<FullAndPartURIs>> entry : artifactsMap.entrySet() ) {
            final String key = entry.getKey();
            if (key.equals(moduleName) ||
                (key.startsWith(moduleName) && (key.charAt(moduleName.length())) == '/')) {
                uriPairs.addAll(entry.getValue());
            }
        }

        if (uriPairs.isEmpty()) {
            uriPairs = Collections.emptySet();
        }
        return uriPairs;
    }

    public synchronized void clearArtifacts(String moduleName) {
        Set<FullAndPartURIs> uriPairs = artifactsMap.remove(moduleName);
        if(uriPairs != null) {
            uriPairs.clear();
        }
    }

    public static class FullAndPartURIs {
        private URI full;
        private URI part;

        public FullAndPartURIs(URI full, URI part) {
            this.full = full;
            this.part = part;
        }

        public FullAndPartURIs(URI full, String part) {
            this.full = full;
            this.part = URI.create(part);
        }

        public URI getFull() {
            return full;
        }

        public URI getPart() {
            return part;
        }

        private void setFull(URI full) {
            this.full = full;
        }

        private void setPart(URI part) {
            this.part = part;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FullAndPartURIs other = (FullAndPartURIs) obj;
            if (this.full != other.full && (this.full == null || !this.full.equals(other.full))) {
                return false;
            }
            if (this.part != other.part && (this.part == null || !this.part.equals(other.part))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + (this.full != null ? this.full.hashCode() : 0);
            hash = 29 * hash + (this.part != null ? this.part.hashCode() : 0);
            return hash;
        }

        @Override
        public String toString() {
            return "full URI=" + full + "; part URI=" + part;
        }
    }
}
