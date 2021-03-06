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
package com.couchbase.client.core.metrics;

import com.couchbase.client.core.env.Diagnostics;
import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metrics.RuntimeMetricsEvent;
import rx.Scheduler;

import java.util.Map;
import java.util.TreeMap;

/**
 * A {@link MetricsCollector} which collects and emits system information like gc, memory or thread usage.
 *
 * @author Michael Nitschinger
 * @since 1.2.0
 */
public class RuntimeMetricsCollector extends AbstractMetricsCollector {

    public RuntimeMetricsCollector(final EventBus eventBus, Scheduler scheduler, MetricsCollectorConfig config) {
        super(eventBus, scheduler, config);
    }

    @Override
    protected CouchbaseEvent generateCouchbaseEvent() {
        Map<String, Object> metrics = new TreeMap<String, Object>();

        Diagnostics.gcInfo(metrics);
        Diagnostics.memInfo(metrics);
        Diagnostics.threadInfo(metrics);

        return new RuntimeMetricsEvent(metrics);
    }

}
