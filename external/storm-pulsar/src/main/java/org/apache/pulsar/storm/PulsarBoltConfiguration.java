/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.pulsar.storm;

import java.util.Objects;

/**
 * Class used to specify Pulsar bolt configuration.
 */
public class PulsarBoltConfiguration extends PulsarStormConfiguration {

    private static final long serialVersionUID = 1L;

    private TupleToMessageMapper tupleToMessageMapper = null;

    /**
     * Get the tuple to message mapper.
     * @return the mapper to convert storm tuples to a pulsar message.
     */
    public TupleToMessageMapper getTupleToMessageMapper() {
        return tupleToMessageMapper;
    }

    /**
     * Sets the mapper to convert storm tuples to a pulsar message.
     * <p>
     * Note: If the mapper returns null, the message is not sent by the producer and is acked immediately on the
     * collector
     * </p>
     *
     * @param mapper - tuple to message mapper
     */
    public void setTupleToMessageMapper(TupleToMessageMapper mapper) {
        this.tupleToMessageMapper = Objects.requireNonNull(mapper);
    }

}
