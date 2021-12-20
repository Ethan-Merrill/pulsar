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
package org.apache.pulsar.functions.api;

import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.common.classification.InterfaceAudience;
import org.apache.pulsar.common.classification.InterfaceStability;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

/**
 * SharedContext provides contextual information for a given function/source/sink.
 * It allows to propagate information, such as pulsar environment, logs, metrics, states etc.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public interface SharedContext {
    /**
     * Get or create the state store with the provided store name in the tenant & namespace.
     *
     * @param name the state store name
     * @param <S> the type of interface of the store to return
     * @return the state store instance.
     *
     * @throws ClassCastException if the return type isn't a type
     * or interface of the actual returned store.
     */
    default <S extends StateStore> S getCreateStateStore(String tenant, String ns, String name) {
        throw new UnsupportedOperationException("Component cannot get state store");
    }

    /**
     * Update the state value for the key.
     *
     * @param key   name of the key
     * @param value state value of the key
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    void putState(String key, ByteBuffer value, String tenant, String ns, String name);

    /**
     * Update the state value for the key, but don't wait for the operation to be completed
     *
     * @param key   name of the key
     * @param value state value of the key
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    CompletableFuture<Void> putStateAsync(String key, ByteBuffer value, String tenant, String ns, String name);

    /**
     * Retrieve the state value for the key.
     *
     * @param key name of the key
     * @return the state value for the key.
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    ByteBuffer getState(String key, String tenant, String ns, String name);

    /**
     * Retrieve the state value for the key, but don't wait for the operation to be completed
     *
     * @param key name of the key
     * @return the state value for the key.
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    CompletableFuture<ByteBuffer> getStateAsync(String key, String tenant, String ns, String name);

    /**
     * Delete the state value for the key.
     *
     * @param key   name of the key
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    void deleteState(String key, String tenant, String ns, String name);

    /**
     * Delete the state value for the key, but don't wait for the operation to be completed
     *
     * @param key   name of the key
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    CompletableFuture<Void> deleteStateAsync(String key, String tenant, String ns, String name);

    /**
     * Increment the builtin distributed counter referred by key.
     *
     * @param key    The name of the key
     * @param amount The amount to be incremented
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    void incrCounter(String key, long amount, String tenant, String ns, String name);

    /**
     * Increment the builtin distributed counter referred by key
     * but dont wait for the completion of the increment operation
     *
     * @param key    The name of the key
     * @param amount The amount to be incremented
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    CompletableFuture<Void> incrCounterAsync(String key, long amount, String tenant, String ns, String name);

    /**
     * Retrieve the counter value for the key.
     *
     * @param key name of the key
     * @return the amount of the counter value for this key
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    long getCounter(String key, String tenant, String ns, String name);

    /**
     * Retrieve the counter value for the key, but don't wait
     * for the operation to be completed
     *
     * @param key name of the key
     * @return the amount of the counter value for this key
     * @param tenant the state tenant name
     * @param ns the state namespace name
     * @param name the state store name
     */
    CompletableFuture<Long> getCounterAsync(String key, String tenant, String ns, String name);
}
