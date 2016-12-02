package com.mycompany.app;

/*
 * Configuration Variables
 * Variables ending with _KEY will be read form the configuration file
 * others are constants
 */
public class ConfigVars {

    public static final String STORM_CONFIG_TOPOLOGY_NAME_KEY = "storm.config.topology.name";
    public static final String STORM_CONFIG_ENABLE_DEBUG_KEY = "storm.config.enable.debug";
    public static final String STORM_CONFIG_NUM_WORKERS_KEY = "storm.config.num.workers";
    public static final String STORM_CONFIG_NUM_ACKERS_KEY = "storm.config.num.ackers";
    public static final String STORM_CONFIG_TOPOLOGY_STATS_SAMPLE_RATE_KEY = "storm.config.topology.stats.sample.rate";

    public static final String HBASE_CONFIG_NAME = "HBASECONF";
    public static final String HBASE_CONFIG_R1_KEY = "hbase.config.r1";
    public static final String HBASE_CONFIG_R2_KEY = "hbase.config.r2";
    public static final String HBASE_CONFIG_R3_KEY = "hbase.config.r3";
    public static final String HBASE_CONFIG_KEYTAB_FILE_KEY = "hbase.config.keytab.file";
    public static final String HBASE_CONFIG_KERBEROS_PRINCIPAL_KEY = "hbase.config.kerberos.principal";

    }
