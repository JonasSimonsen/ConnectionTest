How we loaded CVS file into the Neo4J database.

Run command vagrant up

Navigate to: http://127.0.0.1:7474/browser/

You can also use: http://localhost:7474/browser/

Create index:
CREATE INDEX ON :Person(name);

Create and using constraints:
CREATE CONSTRAINT ON (p:Person) ASSERT p.id IS UNIQUE;

Load CSV:
USING PERIODIC COMMIT
LOAD CSV WITH HEADERS FROM "file:///social_network_nodes.csv" AS row
MERGE (:Person {id: toInt(row.node_id), name: row.name, job: row.job,
birthday: row.birthday});

USING PERIODIC COMMIT 5000
LOAD CSV WITH HEADERS FROM "file:///social_network_edges.csv" AS row
MATCH (f:Person {id: toInt(row.source_node_id)}), (t:Person {id:
toInt(row.target_node_id)})
CREATE (f)-[:ENDORSES]->(t);





How we created MySQL database and loaded CVS file into the MySQL database:

Run command: vagrant up

Run command: vagrant ssh

Run command:
mysql -u root -p
pwd is the password

Run command:
create database social_network;

Created two tables t_user & t_endorsement:
	CREATE TABLE t_user
	(
		node_id INT PRIMARY KEY,
		name NVARCHAR(128) NOT NULL,
		job NVARCHAR(128) NOT NULL,
		birthday NVARCHAR(10) NOT NULL
	);

CREATE UNIQUE INDEX t_user_node_id_uindex ON t_user (node_id);

CREATE TABLE social_network.t_endorsement
(
	source_node_id INT NOT NULL,
	target_node_id INT NOT NULL,
	PRIMARY KEY (source_node_id, target_node_id),
	FOREIGN KEY (source_node_id) REFERENCES t_user (node_id),
	FOREIGN KEY (target_node_id) REFERENCES t_user (node_id)	
);

Run command:
LOAD DATA LOCAL INFILE '/var/lib/neo4j/import/social_network_nodes.csv'
INTO TABLE social_network.t_user
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(node_id,name,job,birthday);

Run command:
LOAD DATA LOCAL INFILE '/var/lib/neo4j/import/social_network_edges.csv'
INTO TABLE social_network.t_endorsement
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(source_node_id, target_node_id);
