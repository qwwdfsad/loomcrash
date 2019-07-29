### How to run

```
export JAVA_HOME=$path_to_loom
mvn clean package
java -jar target/benchmarks.jar -p  "primes=100" -wi 10000 loomSieve
``` 

### Loom version

Fibers branch, `changeset:   55992:423f4ecbf972` head