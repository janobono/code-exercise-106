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
- ReportDataDto
    - has unused percentage field
    - Integer was used for salary
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

- `Number of rows can be up to 1000.` - wrong decision here I was thinking that it could be more. It can read big data
  file. In the process of reading results are counted. For report than just read is needed.
    - Flag removed - fixed.

- I didn't wrap the system errors in application errors. I wanted to use `ApplicationException` just for user-relevant
  cases.
    - Fixed.

- Structure
    - DTO - simple utility just two packages and three DTOs
        - Fixed

- ReportDataDto
    - has unused percentage field - removed - fixed
    - Integer was used for salary - [There is nothing](ASSIGNMENT.md) about data format. Based on example I decided to
      use integer.

- No validation
    - 'CsvLineParserTest' - you can see validation tests here
    - [There is nothing](ASSIGNMENT.md) about what to do in invalid data case. I decided to interrupt execution in that
      case and write error in to std.

- Util classes doesn't have private constructor to hide the implicit one.
    - I am using `@Component` in SpringBoot I forgot to hide constructor - fixed.

- ReportLineReaderTest - redundant variable removed - fixed.

- ReportRepositoryMathUtilTest - wrong dependency fixed and then test fixed too

- Documentation issues - simple util self documented code.
