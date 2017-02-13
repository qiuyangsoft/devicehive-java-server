package com.devicehive.dao.graph;

/*
 * #%L
 * DeviceHive Dao RDBMS Implementation
 * %%
 * Copyright (C) 2016 - 2017 DataArt
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.devicehive.dao.ConfigurationDao;
import com.devicehive.dao.graph.model.ConfigurationVertex;
import com.devicehive.vo.ConfigurationVO;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConfigurationDaoGraphImpl extends GraphGenericDao implements ConfigurationDao {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationDaoGraphImpl.class);

    @Override
    public Optional<ConfigurationVO> getByName(String name) {
        logger.info("Getting configuration");
        GraphTraversal<Vertex, Vertex> gT = g.V().has(ConfigurationVertex.LABEL, ConfigurationVertex.Properties.NAME, name);
        if (gT.hasNext()) {
            ConfigurationVO configurationVO = ConfigurationVertex.toVO(gT.next());
            return Optional.of(configurationVO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int delete(String name) {
        logger.info("Deleting configuration");
        GraphTraversal<Vertex, Vertex> gT = g.V().has(ConfigurationVertex.LABEL, ConfigurationVertex.Properties.NAME, name);
        int count = gT.asAdmin()
                .clone()
                .toList()
                .size();

        gT.drop().iterate();
        logger.info(g.V().count().next().toString());
        return count;
    }

    @Override
    public void persist(ConfigurationVO configuration) {
        logger.info("Adding configuration");
        GraphTraversal<Vertex, Vertex> gT = ConfigurationVertex.toVertex(configuration, g);
        gT.next();
        logger.info(g.V().count().next().toString());
    }

    @Override
    public ConfigurationVO merge(ConfigurationVO existing) {
        logger.info("Updating configuration");
        GraphTraversal<Vertex, Vertex> gT = g.V()
                .hasLabel(ConfigurationVertex.LABEL)
                .has(ConfigurationVertex.Properties.NAME, existing.getName());
        gT.property(ConfigurationVertex.Properties.VALUE, existing.getValue());
        gT.property(ConfigurationVertex.Properties.ENTITY_VERSION, existing.getEntityVersion());
        gT.next();
        return existing;
    }
}
