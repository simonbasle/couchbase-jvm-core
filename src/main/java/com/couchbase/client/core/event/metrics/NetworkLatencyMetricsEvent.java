/**
 * Copyright (c) 2015 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */
package com.couchbase.client.core.event.metrics;

import com.couchbase.client.core.metrics.NetworkLatencyMetricsIdentifier;
import com.couchbase.client.core.utils.Events;

import java.util.HashMap;
import java.util.Map;

/**
 * This event represents network latencies captured in the core layer.
 *
 * @author Michael Nitschinger
 * @since 1.2.0
 */
public class NetworkLatencyMetricsEvent extends LatencyMetricsEvent<NetworkLatencyMetricsIdentifier> {

    public NetworkLatencyMetricsEvent(Map<NetworkLatencyMetricsIdentifier, LatencyMetric> latencies) {
        super(latencies);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> result = Events.identityMap(this);

        for (Map.Entry<NetworkLatencyMetricsIdentifier, LatencyMetric> metric : latencies().entrySet()) {
            NetworkLatencyMetricsIdentifier ident = metric.getKey();
            Map<String, Object> host = getOrCreate(ident.host(), result);
            Map<String, Object> service = getOrCreate(ident.service(), host);
            Map<String, Object> request = getOrCreate(ident.request(), service);
            Map<String, Object> status = getOrCreate(ident.status(), request);
            status.put("metrics", metric.getValue().export());
        }
        return result;
    }

    @SuppressWarnings({"unchecked"})
    private Map<String, Object> getOrCreate(String key, Map<String, Object> source) {
        Map<String, Object> found = (Map<String, Object>) source.get(key);
        if (found == null) {
            found = new HashMap<String, Object>();
            source.put(key, found);
        }
        return found;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkLatencyMetricsEvent");
        sb.append(toMap().toString());
        return sb.toString();
    }

}
