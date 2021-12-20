#!/usr/bin/env python
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#

# -*- encoding: utf-8 -*-

import state_context

class StateManager():
    states = {}
    state_storage_serviceurl = ""

    def __init__(self, state_storage_serviceurl):
        self.state_storage_serviceurl = state_storage_serviceurl

    def get_state(self, tenant, ns, name):
        table_ns = "%s_%s" % (str(tenant), str(ns))
        table_ns = table_ns.replace("-", "_")
        table_name = str(name)
        state_id = table_ns + table_name

        state = self.states.get(state_id) 
        if state is None:
            state = state_context.create_state_context(self.state_storage_serviceurl, table_ns, table_name)
            self.states[state_id] = state

        return state