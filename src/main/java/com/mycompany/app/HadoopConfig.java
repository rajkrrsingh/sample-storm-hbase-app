package com.mycompany.app;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.storm.Config;
import org.apache.storm.hbase.security.HBaseSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HadoopConfig extends Config {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(HadoopConfig.class);

    public HadoopConfig(PropertyParser propertyParser) {
        super();

	this.setStatsSampleRate(propertyParser.getPropertyAsDouble(ConfigVars.STORM_CONFIG_TOPOLOGY_STATS_SAMPLE_RATE_KEY));
	this.setDebug(propertyParser.getPropertyAsBoolean(ConfigVars.STORM_CONFIG_ENABLE_DEBUG_KEY));
	this.setNumWorkers(propertyParser.getPropertyAsInt(ConfigVars.STORM_CONFIG_NUM_WORKERS_KEY));
	this.setNumAckers(propertyParser.getPropertyAsInt(ConfigVars.STORM_CONFIG_NUM_ACKERS_KEY));


	this.put(HBaseSecurityUtil.STORM_KEYTAB_FILE_KEY, propertyParser.getProperty(ConfigVars.HBASE_CONFIG_KEYTAB_FILE_KEY));
	this.put(HBaseSecurityUtil.STORM_USER_NAME_KEY, propertyParser.getProperty(ConfigVars.HBASE_CONFIG_KERBEROS_PRINCIPAL_KEY));

	// HBase
	Map<String, Object> hBaseMap = new HashMap<String, Object>();
	Configuration hBaseClusterConfig = new Configuration(false);
	hBaseClusterConfig.addResource(new Path(propertyParser.getProperty(ConfigVars.HBASE_CONFIG_R1_KEY)));
	hBaseClusterConfig.addResource(new Path(propertyParser.getProperty(ConfigVars.HBASE_CONFIG_R2_KEY)));
	hBaseClusterConfig.addResource(new Path(propertyParser.getProperty(ConfigVars.HBASE_CONFIG_R3_KEY)));

	// Set cluster defaults
	for (java.util.Map.Entry<String, String> config : hBaseClusterConfig) {
	    hBaseMap.put(config.getKey(), config.getValue());
	}

	hBaseMap.put(HBaseSecurityUtil.STORM_KEYTAB_FILE_KEY, propertyParser.getProperty(ConfigVars.HBASE_CONFIG_KEYTAB_FILE_KEY));
	hBaseMap.put(HBaseSecurityUtil.STORM_USER_NAME_KEY, propertyParser.getProperty(ConfigVars.HBASE_CONFIG_KERBEROS_PRINCIPAL_KEY));
	
	this.put(ConfigVars.HBASE_CONFIG_NAME, hBaseMap);
    }

    @SuppressWarnings("unchecked")
    public String getZKHosts() {
	Map<String, Object> hBaseMap = new HashMap<String, Object>();
	hBaseMap = (Map<String, Object>) this.get(ConfigVars.HBASE_CONFIG_NAME);

	String hosts = (String) hBaseMap.get("hbase.zookeeper.quorum");
	String port = (String) hBaseMap.get("hbase.zookeeper.property.clientPort");


	String zkHosts = null;
	if (hosts.contains(",")) {
	    zkHosts = hosts.replace(",", ":" + port + ",");
	    zkHosts = zkHosts + ":" + port;
	} else
	    zkHosts = hosts + ":" + port;

	LOG.debug("zkhosts: " + zkHosts);

	return zkHosts;
    }
}
