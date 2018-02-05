# Docker stack

This docker stack is used for bringing up all dependant services needed for 

See the parent README.md for installing Docker

## Usage

### Bringing up the stack

To bring up the development stack's dependencies (elasticsearch, kibana, db, etc)

```console
./stack.sh up
```

By default this won't start the apps docker image, since its not useful for development (since the app is usually
run natively).

If you wish to bring up all services including the app (useful for testing the apps docker image and configuration) - run
```console
./stack.sh --all up
```

See the scripts help usage for more info
```commandline
./stack.sh --help
```

By default, the stack exposes the following ports:
* 9200: Elasticsearch HTTP
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
./stack.sh down
```

## Configuration

**NOTE**: Configuration is not dynamically reloaded, you will need to restart the stack after any change in the
configuration of a component.

### How can I tune the app's docker configuration?

The Apps docker configuration is stored in `search/application-docker.yml`.

### How can I tune the Kibana configuration?

The Kibana default configuration is stored in `kibana/config/kibana.yml`.

It is also possible to map the entire `config` directory instead of a single file.

### How can I tune the Elasticsearch configuration?

The Elasticsearch configuration is stored in `elasticsearch/config/elasticsearch.yml`.
