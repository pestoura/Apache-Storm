/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.flux.multilang;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Sanity checks to make sure we can at least invoke the shells used.
 */
public class MultilangEnvironmentTest {
    private static final Logger LOG = LoggerFactory.getLogger(MultilangEnvironmentTest.class);

    @Test
    public void testInvokePython() throws Exception {
        String[] command = new String[]{"python3", "--version"};
        int exitVal = invokeCommand(command);
        assertEquals(0, exitVal, "Exit value for python3 is 0.");
    }

    @Test
    public void testInvokeNode() throws Exception {
        String[] command = new String[]{"node", "--version"};
        int exitVal = invokeCommand(command);
        assertEquals(0, exitVal, "Exit value for node should be 0.");
    }

    private static class StreamRedirect implements Runnable {
        private final InputStream in;
        private final OutputStream out;

        public StreamRedirect(InputStream in, OutputStream out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public void run() {
            try {
                int i;
                while ((i = this.in.read()) != -1) {
                    out.write(i);
                }
                this.in.close();
                this.out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int invokeCommand(String[] args) throws Exception {
        LOG.debug("Invoking command: {}", (Object) args);

        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(true);
        final Process proc = pb.start();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thread t = new Thread(new StreamRedirect(proc.getInputStream(), out));
        t.start();
        int exitVal = proc.waitFor();
        LOG.debug("Command result: {}", out);
        return exitVal;
    }
}
