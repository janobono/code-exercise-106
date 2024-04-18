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

## review result

- Overall solution looks overcomplicated - candidate writes results to temp files, then reads from them to prepare
  response. Also there is unnecessary flag to keep these response files and not delete them.
- Candidate created one custom exception for processing input arguments, but in another places throws RuntimeException
  is used.
- Structure
    - DTO files are in same package as services. I would say putting them into some sub-package would increase
      readability of structure
- No validation
    - in case of invalid data
    - no processing (skip) empty line from input CSV
- Util classes doesn't have private constructor to hide the implicit one.
- ReportLineReaderTest has test readLine_NoDataFile_ExceptionIsThrown with unnecessary assertThrows assignment to
  variable.
- ReportRepositoryMathUtilTest has a lot similar asserts, with different parameters, so test class could be improved by
  using @ParametrizedTest
- Documentation issues:
    - No documentation comments for classes and for methods

## comments after review

- `Number of rows can be up to 1000.` - wrong decision here I was thinking that it could be more. Partial result data
  are serialized so the next reading is just summary.
  - Flag removed - fixed.
