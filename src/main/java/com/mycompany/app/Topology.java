package com.mycompany.app;

import com.google.common.collect.Maps;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.hbase.bolt.mapper.SimpleHBaseMapper;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.Map;
//import org.apache.storm.contrib.hbase.utils.TupleTableConfig;


public class Topology {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("USAGE: storm jar </path/to/topology.jar> <com.mycompany.app.Topology> " + "</path/to/application.properties>");
			System.exit(1);
		}

		PropertyParser propertyParser = new PropertyParser();
		propertyParser.parsePropsFile(args[0]);


		new Topology().runTopology(propertyParser);
	}

	public void runTopology(PropertyParser propertyParser) throws Exception {


		Map hbaseConfMap = Maps.newHashMap();
		hbaseConfMap.put("hbase.zookeeper.quorum", "s251.hwxblr.com,s252.hwxblr.com,s253.hwxblr.com");
		hbaseConfMap.put("hbase.zookeeper.property.clientPort", "2181");
		hbaseConfMap.put("hbase.master.port", "16000");
		hbaseConfMap.put("zookeeper.znode.parent", "/hbase-secure");
		hbaseConfMap.put("storm.keytab.file","/etc/security/keytabs/storm.headless.keytab");
		hbaseConfMap.put("storm.kerberos.principal","storm-s25@HWXBLR.COM");
		String tablename = "test";
		SimpleHBaseMapper mapper = new SimpleHBaseMapper().withRowKeyField("row").withColumnFamily("cf").withColumnFields(new Fields("f1"));
		Config conf = new Config();
		conf.put("hbase.conf", hbaseConfMap);


		HBaseBolt hbolt = new HBaseBolt(tablename, mapper).withConfigKey("hbase.conf");

		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new TestSpout(), 1);
		builder.setBolt("HBaseBolt", hbolt, 1).fieldsGrouping("spout",new Fields("row"));

		StormSubmitter.submitTopology("sample-storm-hbase-toplogy", conf, builder.createTopology());
	}
}
