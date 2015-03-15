File synchronizer
=================

Build:      mvn install

Integration  test
-----------------
From root dir:

> mvn -P it verify

Run client only:
----------
From root dir:

> mvn -P client verify

Run server only:
----------
From root dir:

> mvn -P server verify

Network transfer was emulated by copy to other directory.

> db_script.sql: script for table creating

> xml_storage: directory for synchronizing xml files

TODO
----
+ Implement network transfer
