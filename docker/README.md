# Docker stack

Based off [https://github.com/deviantony/docker-elk](https://github.com/deviantony/docker-elk)

Stack contains

* [elasticsearch](https://github.com/elastic/elasticsearch-docker)
* [kibana](https://github.com/elastic/kibana-docker)
* [postgres](https://hub.docker.com/_/postgres/)
* [Adminer](https://www.adminer.org/)

See the parent README.md for installing Docker

## Usage

### Bringing up the stack

Start the stack using `docker-compose`:

```console
docker-compose up
```

You can also choose to run it in background (detached mode):

```console
docker-compose up -d
```

By default, the stack exposes the following ports:
* 9200: Elasticsearch HTTP
* 9300: Elasticsearch TCP transport
* 5601: Kibana
* 9090: Adminer

Inspect status of cluster

```commandline
curl http://localhost:9200/_cat/health
```

Go to `http://localhost:9090/` to access the web console for postgres
1. System: Postgres
2. Server: db
3. username: postgres
4. password: example
5. database: blank  

Give Kibana a few seconds to initialize, then access the Kibana web UI by hitting
[http://localhost:5601](http://localhost:5601) with a web browser.

### Bring Down the stack

Stop and remove containers, networks, images, and volumes

```console
docker-compose down
```

## Configuration

**NOTE**: Configuration is not dynamically reloaded, you will need to restart the stack after any change in the
configuration of a component.

### How can I tune the Kibana configuration?

The Kibana default configuration is stored in `kibana/config/kibana.yml`.

It is also possible to map the entire `config` directory instead of a single file.

### How can I tune the Elasticsearch configuration?

The Elasticsearch configuration is stored in `elasticsearch/config/elasticsearch.yml`.

## Initial setup

### Default Kibana index pattern creation

When Kibana launches for the first time, it is not configured with any index pattern.

#### Via the Kibana web UI

**NOTE**: You need to inject data into Logstash before being able to configure a Logstash index pattern via the Kibana web
UI. Then all you have to do is hit the *Create* button.

Refer to [Connect Kibana with
Elasticsearch](https://www.elastic.co/guide/en/kibana/current/connect-to-elasticsearch.html) for detailed instructions
about the index pattern configuration.

#### On the command line

Run this command to create a Logstash index pattern:

```console
$ curl -XPUT -D- 'http://localhost:9200/.kibana/doc/index-pattern:docker-elk' \
    -H 'Content-Type: application/json' \
    -d '{"type": "index-pattern", "index-pattern": {"title": "logstash-*", "timeFieldName": "@timestamp"}}'
```

This will automatically be marked as the default index pattern as soon as the Kibana UI is opened for the first time.
