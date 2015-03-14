File synchronizer
=================

Build:      mvn install

Run client:
----------
From root dir:

> mvn -P client verify

Run server:
----------
From root dir:

> mvn -P server verify

Network transfer was emulated by copy to other directory.

> db_script.sql: script for table creating

> xml_storage: directory for synchronizing xml files

TODO
----
+ Implement network
+ Refactoring, based on file flags (TODO in comments)

Integration  test
-----------------
From root dir:

> mvn -P it verify

Run in background client and server main and integration tests: MainServerSynchronizerIT- TODO
Client and Server run via run-background-plugin (only Windows implementation, Unix- TODO)