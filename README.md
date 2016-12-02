# sample-storm-hbase-app

build and deploy
```
build using 
mvn clean package

prepare hbase using hbase shell
create 'test', 'cf'

run using
storm jar sample-strom-hbase-1.0-SNAPSHOT-jar-with-dependencies.jar com.mycompany.app.Topology application.properties
```

