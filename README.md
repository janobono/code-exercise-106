# code-exercise-106

- [Assignment](./ASSIGNMENT.md)

## requirements

- [jdk 21](https://adoptium.net/)
- [maven](https://maven.apache.org/)

## build

```bash
mvn clean install
```

## run

```bash
java -jar ./target/code-exercise-106.jar [arg1] [arg2]
```

- **arg1** represents path to input data file
- **arg2** represents skip header value. Default value is true. It's optional.

## comments

- My solution counts with sorted input. It means that the manager is defined always before his subordinate.
- Line with salary less than 1 leads to `IllegalArgumentException`.
- Invalid line with length less than 4 leads to `IllegalArgumentException`.
